let S x y z = (x z (y z));;

printfn "%d %d" 1 (g(2,4));;

let square s = fun x -> s(s x);;

let x = 1;;
let f() = x;;
let test() =
  let x = 2
  printfn "%d" (f());;

let rec foreach l action =
  match l with
  | [] -> ()
  | lfirst :: lrest ->
    action lfirst
    foreach lrest action;;

let rec multi a b =
  match (a,b) with
  | (Integer x, Integer y) -> Integer(x*y)
  | (Integer x, Rational(a,b)) -> Rational(x*a,b)
  | (Integer x, Real a) -> Real(float(x)*a)
  | (Integer x, Complex(a,i)) -> Complex(float(x)*a,float(x)*i)
  | (Rational(a,b), Rational(c,d)) -> Rational(a*c,b*d)
  | (Rational(a,b), Real c) -> Real(c*float(a)/float(b))
  | (Rational(a,b), Complex(r,i)) -> Complex(float(a)*r/float(b),float(a)*i/float(b))
  | (Real a, Real b) -> Real(a*b)
  | (Real a, Complex(r,i)) -> Complex(a*r,a*i)
  | (Complex(a,b), Complex(c,d)) -> Complex(a*c-b*d,a*d+b*c)
  | (x,y) -> multi y x;;

let rec stringify x =
  match x with
  | Integer x -> sprintf "%d" x
  | Rational(a,b) -> sprintf "%d%s%d" a "/" b
  | Real x -> sprintf "%f" x
  | Complex(a,i) -> sprintf "%f%s%f%s" a " + " i "i"

let doubleup ll =
  let rec inner lrest curr =
    match lrest with
    | Nil -> curr
    | Cons(a,b) -> inner b (Cons(a,Cons(a,curr)))
  let rec rev input curr =
    match input with
    | Nil -> curr
    | Cons(a,b) -> rev b (Cons(a,curr))
  rev (inner ll Nil) Nil;;

let howmany func ll =
  let rec inner cond curr count =
    match curr with
    | Nil -> count
    | Cons(a,b) ->
      if (cond a) then inner cond b (count+1)
      else inner cond b count
  inner func ll 0;;

let filter func ll =
  let rec inner cond curr filt =
    match curr with
    | Nil -> filt
    | Cons(a,b) ->
      if (cond a) then inner cond b (Cons(a,filt))
      else inner cond b filt
  inner func ll Nil;;

let subset ll1 ll2 =
  let rec inner1 llout llin ss =
    let rec inner2 check lls inll =
      match lls with
      | Nil -> inll
      | Cons(a,b) ->
        if (a=check) then inner2 check b true
        else inner2 check b inll
    match llout with
    | Nil -> ss
    | Cons(a,b) -> inner1 b llin (inner2 a ll2 ss)
  inner1 ll1 ll2 false;;

let tolist ll =
  let rec inner l lcurr =
    match l with
    | Nil -> lcurr
    | Cons(a,b) -> inner b (a::lcurr)
  List.rev (inner ll []);;

let f x =
  let mutable x = x
  while x>0 do
    printfn "%d" x
    x <- x-1;;

let ptrorarr (arr:'a[]) =
  let first = arr.[0]
  let inner (a:'b[]) =
    if (a.[0]<>0) then a.[0] <- 0
    else a.[0] <- 1
  inner arr
  if (first<>0) then
    if (arr.[0]=0) then
      "pointer"
    else
      "copy"
  else
    if (arr.[0]=1) then
      "pointer"
    else
      "copy"
