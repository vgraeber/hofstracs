let maketoggle init =
  let mutable x = false
  fun y ->
    x <- not x
    x;;

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
