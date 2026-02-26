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