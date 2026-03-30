open System;
// some aliases:
let omap = Option.map
let obind = Option.bind
let oiter = Option.iter
let is_some = Option.isSome
let is_none = Option.isNone
let get_or = Option.defaultValue
type Vec<'T> = ResizeArray<'T>;;

type Stream<'T> = unit -> Option<'T>

(*
  A stream is a possibly infinite sequence of values.
  A stream of values of type 'T is represented by a function of type (unit -> Option<'T>).
  Each time the function is called, it either returns a next value (Some value) or None if there are no more values.
  For example, the function (fun () -> None) represents the empty stream.
  The following infinte stream generates all even integers starting from zero:
*)
let mutable n = 0
let evenints() = fun () -> (n <- n+2; Some(n-2))

(*
  Most streams are mutable closures such as evenints.
  These closures can be modified by a series of operations.

  We eventually want to be able to write code like this:

  evenints()
  |> limit(1000)                  // limit to the first 100 even integers
  |> take_while(fun x -> x<1000)  // take until x>=1000
  |> filter (fun x -> x%4==0)     // take only those x such that x%4==0
  |> map (fun x -> x*x)           // square each x
  |> reduce (+) 0                 // sum up entire stream

  The first modifier limits the number of elements that a stream can generate:
*)

let limit n stream =
  let mutable counter = 0
  fun () ->
    if counter >= n then None
    else
      counter <- counter + 1
      stream()

//  The next modifier applies a function to each generated value

let map mapfun stream =
  fun () -> stream() |> omap mapfun

(*
  For example, evenints |> map (fun x -> x*x) generates the sequence 0 4 16 36 ...

  Each call to stream() returns a (Some x) or None.
  If the function to be mapped also returns an option, we apply the following variant to avoid nested options:
*)
let map_oflatten mapfun stream =  fun () -> stream() |> obind mapfun

(*
  The next modifier applies a predicate ('T -> bool) to each value, keeping only those for which the predicate returns true
*)

let filter predicate stream =
  let rec inner next =
    match next with
    | Some(x) when (predicate x) -> next
    | Some(x) -> inner (stream())
    | None -> None
  fun () -> inner (stream())

// version using while loop
let filter_alt predicate stream =
  fun () ->
    let mutable next = stream()
    let notp = fun x -> not(predicate x)
    while (next |> omap notp |> get_or false) do
      next <- stream()
    next

(*
  notp is the negation of the predicate.
  The while loop keeps running if next contains a value for which notp holds.
  Since next is an Option, we must again use Option.map to apply notp to the inner value.
  (next |> omap notp) gives us an Option<bool>.
  We then extract the boolean value with get_or (Option.defaultValue), using false as default in case next is None.

  For example, if next is Some(4) and notp is fun x -> x>1 then (next |> omap notp |> get_or false) produces the sequence of values

    Some(4) --> Some(true) --> true.

  However, if next is None, then the values generated are

    None --> None --> false

  The next modifier is similar to limit, but takes a predicate, and stops the stream when the predicate returns false:
*)

let take_while predicate stream =
  fun () -> stream() |> obind (fun x -> if predicate(x) then Some(x) else None)

(*
  Notice that this time we applied Option.bind (obind) instead of omap on the value produced because the function itself produces an Option.
  obind is applied to avoid producing a nested option (Some(Some(x)).

  A stream of streams (unit -> Option<unit -> Option<'T>>) should be flattened.
  This is a rather convoluted procedure involving options and streams.
  Don't confuse this procedure with map_oflatten above.
  This time we're flattening a stream of streams:
*)

let flatten nested_stream =
  let mutable current_stream = nested_stream() // Option<stream>
  let rec inner next =
    match (next,current_stream) with
    | (Some x,_) -> next
    | (None,Some(stream)) ->
      current_stream <- nested_stream()
      inner (current_stream |> obind (fun s -> s()))
    | (None,None) -> None
  fun () -> inner (current_stream |> obind (fun s -> s()))

// version using while loop
let flatten_alt nested_stream =
  let mutable current_stream = nested_stream() // Option<stream>
  fun () ->
    let mutable next = current_stream |> obind (fun strm -> strm())
    while (is_none next) && (is_some current_stream) do
      current_stream <- nested_stream()
      next <- current_stream |> obind (fun strm -> strm())
    next

(*
  Like Option, Stream is also a kind of "monad" because it has operations such as map, flatten and bind.
  bind is flatten composed with map:
*)

let bind binder stream = stream |> map binder |> flatten

(*
  bind will be useful when we create streams from options
*)

// conver option to stream
let to_stream opt =
  match opt with
  | None -> fun() -> None
  | Some x ->
    let mutable singleton = true
    fun () ->
      if singleton then singleton <- false; Some(x)
      else None

// Two streams can be joined to form a single stream:
let append stream1 stream2 =
  fun () ->
    let next = stream1()
    match next with
    | Some(x) -> next
    | None -> stream2()

let (+++) a b = append a b      // defines an infix operator
// +++ has the same precedence and associativity as +


(* -------------------------------------------------------------------
  The next series of functions take streams and consume them by doing something with the values generated.
*)

let foreach action stream =  // apply action to each element of stream
  let rec inner next =
    match next with
    | Some x ->
      action x
      inner (stream())
    | None -> ()
  inner (stream())

// version using while loop
let for_each action stream =
  let mutable next = stream()
  while (is_some next) do
    next |> oiter action // perform action on the option object
    next <- stream()     // get next value
// foreach

(*
  Notice that we defined evenints as a function so that each call to evenints() re-creates the stream.
  Each call to foreach consumes an instance of the stream (the stream would return None afterwards).

  We can define the for_all and there_exists quantifiers to streams.
*)

let forall predicate stream =
  let rec inner next =
    match next with
    | None -> true
    | Some x when (predicate x) -> inner (stream())
    | Some x -> false
  inner (stream())

let for_all predicate stream =
  let mutable answer = true
  let mutable next = stream()
  while answer && (is_some next) do
    if not(next |> omap predicate |> get_or true) then answer <- false
    else next <- stream()
  answer

let there_exists predicate stream =
  not(for_all (fun x -> not (predicate x)) stream)

(*
  there_exists just returns a boolean, but we probably want to find the actual value that exists.
*)
let find_first predicate stream =
  let rec inner next =
    match next with
    | Some(x) when (predicate x) -> next
    | Some(x) -> inner (stream())
    | None -> next
  inner (stream())

// The following simply returns the nth value generated by the stream:
let nth n stream =
  let mutable cx = 0
  let mutable next = stream()
  while cx<n && (is_some next) do
    next <- stream()
    cx <- cx+1
  next

(*
  The reduce operation takes an operator and an identity and successively applies the operator to the values of the stream in left-associative manner, starting with the id
*)

let reduce op id stream =
  let rec inner ax next =
    match next with
    | None -> ax
    | Some x -> inner (op ax x) (stream())
  inner id (stream())

// version using while loop
let reduce_alt op id stream =
  let mutable ax = Some(id)
  let mutable next = stream()
  while (is_some next) do
    ax <- next |> obind (fun b -> ax |> omap (fun a -> op a b))
    next <- stream()
  get_or id ax

(*
  Operations to create streams.

  Instead of defining a mutable closure for each stream, there are generalized ways to create them.
*)

let finite_stream<'T> (m:'T list) =
  let mutable index = 0
  fun () ->
    if index >= m.Length then None
    else
      index <- index + 1
      Some(m[index-1])

let empty_stream() = fun () -> None

let input_stream() = fun () ->
  let input = Console.ReadLine()
  if input="" then None else Some(input)

(*
  Infinite sequences are commonly defined inductively, from a base case and a function to generate the n+1st value from the nth value.
  Technically this is called "coinduction".
  It's the inverse of induction or recursion, which goes backwards.
  Coinduction goes forward:
*)

let coinduction base_case gen_next =
  let mutable current = base_case
  fun () ->
    let previous = current;
    current <- gen_next current  // generates the next value
    Some(previous)

//// Fibonacci sequence, like you've never seen it before:
coinduction (0,1) (fun (a,b) -> (b,a+b))
|> map (fun (x,y) -> y)
|> limit 20

//// The coinduction generates an infinite sequence of pairs (a,b)
//// starting from (0,1).  We apply map to take just the second value
//// of the pair to produce the sequence 1 1 2 3 5 8 13 21 ...

//// The next example defines n!.  The codinduction defines pairs (n,nf)
//// with the meaning "nf is n!".
////

let factorial(n) =
  coinduction (0,1) (fun (n,nf) -> (n+1, (n+1)*nf))
  |> map (fun (x,y) -> y)
  |> nth n |> Option.get

// We applied Option.get.  The justification is that n! exists.
// But we may want coinduction to be limited, for example, when overflow occurs,
// the coinduction stream should stop

let limited_coinduction base_case has_next gen_next =
  let mutable current = base_case
  fun () ->
    if not(has_next current) then None
    else
      let previous = current
      current <- gen_next current
      Some(previous)

let n_factorial(n) =
  limited_coinduction (0,1) (fun (_,n) -> n>0) (fun (n,nf) -> (n+1,(n+1)*nf))
  |> map (fun (_,y) -> y)
  |> nth n

(*
  The next series of definitions will culminate in defining the infinte stream of all prime numbers.
  First, we modify coinduction to use a mutable vector of values.
  Each time the next value in the sequence is generated, it is added to the vector.
*)

let vector_stream<'T> (m:Vec<'T>) =  // stream a vector as opposed to list
  let mutable i = 0
  fun () ->
    i <- i+1
    if i <= m.Count then Some(m[i-1]) else None

let vector_coinduction<'T> (seeds:Vec<'T>) gen_next =
  let mutable index = 0
  fun () ->
    if index < seeds.Count then
      index <- index + 1
      Some(seeds[index-1])
    else
      index <- index + 1
      let next = gen_next seeds
      seeds.Add(next)
      Some(next)

// in this version gen_next returns an Option
let limited_vec_coinduction<'T> (seeds:Vec<'T>) gen_next =
  let mutable index = 0
  fun () ->
    if index < seeds.Count then
      index <- index + 1
      Some(seeds[index-1])
    else
      index <- index + 1 
      let next = gen_next seeds
      next |> oiter (fun x -> seeds.Add(x))
      next

let seeded_coinduction<'T> (m:'T list) gen =  // use list to initialize vector
  let vec = Vec<'T>()
  for x in m do vec.Add(x)
  vector_coinduction vec gen

let limited_seeded_coinduction<'T> (m:'T list) gen =
  let vec = Vec<'T>()
  for x in m do vec.Add(x)
  limited_vec_coinduction vec gen

//// function to generate the next prime number given vector of known primes:
let next_prime (known_primes:Vec<int>) =
  if known_primes.Count < 2 then
    known_primes.Add(known_primes.Count + 2);
    Some(known_primes.Count + 1)  //returns 2 or 3
  else
    let last_prime = known_primes.[known_primes.Count - 1]
    let candidates = limited_coinduction (last_prime+2) (fun c->c>0) (fun c -> c+2)
    candidates
    |> find_first(fun candidate ->
      vector_stream known_primes
      |> take_while(fun p -> float(p)<=1.0+Math.Sqrt(float(candidate)))
      |> for_all(fun p -> candidate % p <> 0))

let primes() = limited_seeded_coinduction [2;3;5;7;11] next_prime

let until predicate stream =
  let mutable first = false
  fun () -> stream() |> obind (fun x ->
    if (not (predicate(x)) && not first) then Some(x) 
    elif not first then
      first <- true
      Some(x)
    else None)

let skip n stream =
  let mutable i = 1
  let mutable next = stream()
  while (i<n) do
    next <- stream()
    i <- i+1
  fun () -> stream() |> obind (fun x -> Some(x))

let find_last predicate stream =
  let rec inner next last =
    match next with
    | Some(x) when (predicate x) -> inner (stream()) next
    | Some(x) -> inner (stream()) last
    | None -> last
  inner (stream()) None

let get_last stream =
  find_last (fun p -> p=p) stream

let powersoftwo =
  coinduction (0,1) (fun (a,b) -> ((a+1, b*2)))
  |> map (fun (x,y) -> y)

let primefactors n =
  primes()
  |> take_while (fun x -> x<n)
  |> filter (fun x -> n%x=0)
  |> foreach (printf "%O ")

let binaryfactors n =
  coinduction (1,n) (fun (f,m) -> (f*2, m/2))
  |> take_while (fun (a,b) -> b>0)
  |> filter (fun (a,b) -> b%2=1)
  |> map (fun (a,b) -> a)
  |> foreach (printf "%O ")

let power m n =
  coinduction (m,n) (fun (a,b) -> ((a*a, b/2)))
  |> until (fun (a,b) -> b<=1)
  |> get_last
  |> Option.map (fun (x,y) -> x)
  |> get_or 1

(*
  YOUR ASSIGNMENT:

0.  The first exercise just asks you to practice creating a mutable closure.
    A "toggle" is a function that alternates between returning true and false.
    Write a function maketoggle() that has the following behavior:

      let toggle1 = maketoggle()
      let toggle2 = maketoggle()
      printfn "%A" (toggle1())   // prints true
      printfn "%A" (toggle1())   // prints false
      printfn "%A" (toggle1())   // prints true
      printfn "%A" (toggle2())   // prints true

    Follow the example of "make_accumulator" in
    https://cs.hofstra.edu/~cscccl/csc123/closures.fsx

  Add the following functions for streams

let maketoggle init =
  let mutable x = false
  fun y ->
    x <- not x
    x;;

1.  until predicate stream   (let until predicate stream = ... )

    This operation is similar to take_while but will INCLUDE the first value for which the predicate returns true.
    For example,

    primes() |> until (fun p -> p > 10) should stop at 11, the first prime greater than 10.
    Notice that primes() |> take_while (fun p -> p<=10) will stop at 7

    So until is not just the inverse of take_while.

let until predicate stream =
  let mutable first = false
  fun () -> stream() |> obind (fun x -> 
    if (not (predicate(x)) && not first) then
      Some(x)
    elif not first then
      first <- true
      Some(x)
    else
      None)

2.  skip n stream

    This operation should take a stream and skip the first n values, returning the modified stream.

let skip n stream =
  let mutable i = 1
  let mutable next = stream()
  while (i<n) do
    next <- stream()
    i <- i+1
  fun () -> stream() |> obind (fun x -> Some(x))

3.  find_last predicate stream

    This operation works like find_first but returns the last value for which the predicate holds.
    This function should return Some(value) or None.

let find_last predicate stream =
  let rec inner next last =
    match next with
    | Some(x) when (predicate x) -> inner (stream()) next
    | Some(x) -> inner (stream()) last
    | None -> last
  inner (stream()) None

4.  get_last stream

    This operation should return the last value generated by a stream.
    This should be easy given find_last.
    Think of what predicate to pass to find_last.
    Note that since the entire stream may be empty, this function must still return an option.

let get_last stream =
  find_last (fun p -> p=p) stream

5.  Use coinduction to define all powers of two: 1 2 4 8 16 32 64 128 ...

let powersoftwo =
  coinduction (0,1) (fun (a,b) -> ((a+1, b*2)))
  |> map (fun (x,y) -> y)

6.  Using the stream of primes, write a procedure that prints all prime factors of a number n.
    (a prime factor of n is a prime p such that n%p==0).
    Use a combination of take_while, filter, etc...

    For example: primefactors 10001 should print 73 and 137

let primefactors n =
  primes()
  |> take_while (fun x -> x<n)
  |> filter (fun x -> n%x=0)
  |> foreach (printf "%O ")

7.  The binary factors of a number, such as 45, can be computed with the following sequence of pairs

    (1,45) -> (2,22) -> (4,11) -> (8,5) -> (16,2) -> (32,1)

    The binary factors are extracted from those pairs (factor,m) where m is odd.
    Thus the binary factors of 45 are 1+4+8+32.
    Given n, we can define the sequence with

      coinduction (1,n) (fun (f,m) -> (f*2, m/2))

    Use this sequence to write a function that prints all the binary factors of n.
    You will need a combination of take_while, filter and map.

let binaryfactors n =
  coinduction (1,n) (fun (f,m) -> (f*2, m/2))
  |> take_while (fun (a,b) -> b>0)
  |> filter (fun (a,b) -> b%2=1)
  |> map (fun (a,b) -> a)
  |> foreach (printf "%O ")

8.  (challenge) The following function computes m to the nth power in log(n) steps by binary factorization on n:

    let power m n multiplier identity =
      let rec inner ax factor n =
        if (n>0) then
          let newfactor = multiplier factor factor
          if n%2=1 then inner (multiplier ax factor) newfactor (n/2)
          else inner ax newfactor (n/2)
        else
          ax
      inner identity m n

    power 2 8 (*) 1 will return 256.

    Rewrite the function using coinduction (you will need until and get_last).
    Hint: first write a (inner) function next that takes the tuple (ax,factor,n) to the "next" (ax,factor,n) based on n%2.

let power m n =
  coinduction (m,n) (fun (a,b) -> ((a*a, b/2)))
  |> until (fun (a,b) -> b<=1)
  |> get_last
  |> Option.map (fun (x,y) -> x)
  |> get_or 1

    The nth Fibonacci number can be computed in log(n) steps using matrix multiplication.

    // 2x2 matrix multiplication
    type M2x2 = (int64*int64*int64*int64)

    let mmult(A:M2x2) (B:M2x2) = // 2x2 matrix multiplication
      let ([a, b]   [q, r]
           [c, d],  [s, t]) = (A,B)
      (a*q+b*s, a*r+b*t,
       c*q+d*s, c*r+d*t)

    // using int64 types - L suffix required
    let IDM = [1L, 0L]
              [0L, 1L]    // identity matrix
    let FIBM =  [1L, 1L]
                [1L, 0L]   // Fibonacci matrix

    // FIBM**n power gives
    // [fib(n+1), fib(n)   ]
    // [fib(n),   fib(n-1))]

    // nth Fibonacci number in log n steps, (without overflow)
    let Fib n  =
      match (power FIBM (n-1) mmult IDM) with
      | Some(a,_,_,_) when a>0 -> Some(a)
      | _ -> None
*)
