type Number =
  | Integer of int
  | Rational of int*int
  | Real of float
  | Complex of float*float;;

let inverse num =
  match num with
  | Integer(x) when (x<>0) -> Some(Rational(1,x))
  | Rational(a,b) when (a<>0) -> Some(Rational(b,a))
  | Real(r) when (r<>0.0) -> Some(Real(1.0/r))
  | Complex(a,b) when ((a<>0.0)||(b<>0.0)) -> Some(Complex(a/(a*a+b*b),-b/(a*a+b*b)))
  | _ -> None

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

let div a b =
  Option.map (fun x -> multi a x) (inverse b);;
