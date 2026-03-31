(* CSC 123/252 Assignment: Completing an Interpreter for a Programming Language

   This assignment can be completed at several levels.  The most basic
   level only asks you to implement booleans and if-else expressions.
   The next level requires you to implement variables, assignment and
   while-loops.  The most advanced level asks you to implement mutable
   closures and recursive functions.

   The programming language `Lambda7b` (version 7EA) is an
   interpreted, call-by-value, non-statically typed language that
   supports first class mutable closures, as in the ability to pass
   and return lambda closures as values.

   Lambda7b has two primitive types, integers and strings, though
   strings are only used for output: there are no operations allowed
   on strings and thus there can only be string literals (constants).
   Booleans are represented by integer 0 for false and any non-zero
   integer for true.  If-else is in the functional style: if (a) b
   else c, which evaluates to either b or c.  There is no stand-alone
   if statement, only if-else. A series of expressions can be separated
   using ; and grouped together using parentheses (). The value of
   such a series is the value of the last expresssion. There is a
   while loop and destructive assignment is allowed.  Functions are
   written using the lambda keyword, such as `lambda x:x+1`.  Programs
   are evaluated as scripts (no main).

   The following is a sample lambda7b program:

   # function to compute base**n by binary factorization on n:
   define power = lambda base:lambda n:(
     define ax = 1;
     define factor = base;
     while (0<n) (
       if ((n%2) == 1) (ax = ax * factor) else 0;
       factor = factor * factor;
       n = n/2;
     );
     ax;
   );
   print "Enter base: ";
   define base = get;
   print "Enter exponent: ";
   define exponent = get;
   print power(base)(exponent);
   print "\n";
   # only single-line comments are allowed

Notice the "else 0" at the end of the if-else: it's just a filler
because no stand-alone if statement is allowed.  Also, instead of "n>0"
one writes "0<n" because lambda7b only supports "==" and "<" as
comparison operations.  Input/output are done with "print" and "get"
If you find the syntax strange don't worry: you won't have to write
code with it.  Instead, you'll be finishing an implementation of the
language and only work with ABSTRACT syntax.

All lambdas take one argument: multiple arguments must be Curried, as in
the call to `power(base)(exponent)`.  In other words, the power function
returns a closure with `base` as a free variable.

Mutable closures (objects) are also possible:

   define makeaccumulator = lambda x:lambda y:( x = x+y; x; );
   define a1 = makeaccumulator(0);

Lambdas (closures) can be passed as arguments to other lambdas:

   define fsquared = lambda f:lambda n: f(f(n));
   fsquared(lambda x:x*3)(2);
   
evaluates to 18 (3*3*2)

Lambdas declared using `define` can be recursive:

   define log = lambda n: if (n==1) 0 else 1+log(n/2);

The scope of each `define` is limited to the body of the lambda or ()-bound
series in which it occurs.  In addition to define, there is also `let`,
which has a more limited scope:
  define x = 1;
  x+(let x = 2 in (x+x));
will evaluate to 5: the outer x is 1 and the inner x's are 2.

Finally, as with all modern languages Lambda7b is statically scoped and
the fate of your planet will depend on this being implemented correctly
(see the sample program area51.7b).


///////////// Implementing Lambda7b in F# ///////////////

In the following program skeleton you will find an incomplete interpreter
for this programming language.  It contains:

    1. Definition of abstract syntax (AST) in the types `expr` and `bindings`
    2. A parser that can read from files or from stdin
    3. An incomplete interpreter: not all cases are implemented by the eval function.  THIS IS WHAT YOU HAVE TO COMPLETE, as much as possible.

Study the AST structure (type expr) carefully to understand how program
constructs are represented as expr trees.  In particular, understand that

   a. Instead of Plus, Times, Minus, etc, all binary operators are unified under Binop of string*expr*expr.  This means that 2+3 is Binop("+",Num 2, Num 3).  Likewise, -4 is Uniop("-",Num 4).  The print operation is also a unary operator: Uniop("print",Num 4)

   b. A Sequence is a list of expressions.  In concrete syntax, these expressions are separated by ; and sequences can be bounded by ()'s. A sequence of expressions is evaluated in turn, and the the value of a sequence is the value of the last expression. A while loop should also keep evaluating and return the last expression evaluated. If a while loop does not run at all, the return value should be Void.

   c. The expr type is itself a kind of monad, and there is no need to use the option type for error handling.  In particular, one variant of expr is 'RuntimeError'.  This represents a runtime error in the target language (lambda7b), but not in the meta language F#!
   Beneath the expr type you'll find monadic combinators such as numap, nubind, numap2, istrue, and iserr. These correspond to map/bind on the option type. You can also just use match to look into the contents of expressions. See the samples already done in eval.

   d. The eval function evaluates ASTs to normal forms as defined by the 'normal_form' predicate. In particular, a Lambda is not a normal form: a Lambda evaluates to a Closure, which captures both the lambda term and the current stack (which contains bindings for the free vars of the lambda).  It is a closure, not a lambda, that is applied as a function to arguments. When applying a closure-lambda, the stack (bindings) inside the closure should REPLACE the current runtime stack. This is a crucial step for implementing static scoping, and is easy to get wrong.

   e. Understand the difference between 'let' and 'define'.  A let is a self-contained expression: in 'let x=1 in x+x' the variable x is not visible outside of the 'in' expression.  However, a 'define' is intended to be used in a sequence of expressions:
      define x = 1;
      x = x+1;
    Thus a define must augment the current bindings (stack) with a new pair, ("x",ref Num(1))::bindings.  The 'ref' allows for destructive assignment (using :=).  In other words, they allow the closure to be mutable. However, 'define' is not always making a global definition: the definition is still local within the sequence block.  This is why the eval function is defined simultaneously with eval_seq.  A 'define' affects the stack in the evaluation of the rest of the sequence.

   ***** COMPILING AND RUNNING THIS PROGRAM *****:

    1. Inside the downloaded folder 'lambda7b' which contains this file,
       create a new project: dotnet new console --language F#
    2. Edit the lambda7b.fsproj file and change the <ItemGroup> to
    
      <ItemGroup>
        <Compile Include="lambda7basn.fs" />
        <Reference Include="simpleLexer">
          <HintPath>.\cslexer.dll</HintPath>
        </Reference>
      </ItemGroup>

    3. dotnet build - compiles project
    4. dotnet run test0.7b  

  If the executable is not given a program to run, the interpreter will
  read from stdin, but then the 'get' command won't work.
  
  Please note that the lexical scanner part of the program was
  written in C# and may throw exceptions. Also, the lexical analyzer
  and parser are a little picky: if you want to write your own lambda7b
  programs, you will have to insert some extra ()'s and spaces. For
  example, don't write "if 1&&0 2 else 3": write "if (1 && 0) 2 else 3".
  Since the parser is rudimentary don't expect very good error messages.
  The most common syntactic mistake I made was to forget the ; at the end of
  a line.
  
  *YOUR ASSIGNMENT.*  Your grade for the assignment will depend on how
  much of the interpreter you can complete, and how many of the test 
  programs you can run.  You will be graded on the following scale:

Level 1 (minimal pass): BASIC GEEK.
A basic geek must be able to evaluate booleans, if-else expressions, and implement multiplication, division and remainder safely, and division by zero should eval to a RuntimeError, but not crash the interpreter. 
Booleans must be short circuited.
Also implement the case for Binop("<",a,b), as well as for operators "*", "/" and "%", which are missing from the basic interpreter.
If a and b are not both numbers, a RuntimeError should be returned indicating that invalid operands were given to the operator.
To complete level 1 you must be able to run the program *testbool.7b*

Level 2: MEGAGEEK.
An accomplished megageek must first complete the requirements of being a basic geek, then implement the definition of variables, destructive assignment (ASSIGN) and while loops.
To complete level 2, you must be able to run the program *testloop.7b*

Level 3: GIGAGEEK.
An honored gigageek is a megageek who can also interpret basic functions (lambdas) and function calls.
Function calls should be CALL-BY-VALUE: the argument must be evaluated to a normal form before being passed to the function.
Hint: augment the stack with a new pair binding the formal argument var to a reference to the actual argument.
Run the program *testfun.7b*

Level 4: TERAGEEK.
Being another order of magnitude higher than a gigageek, a revered terageek must be prepared to defend the planet from extra-terrestrials.
You must pass the all-important "AREA 51" test by running the program *area51.7b* and keep the planet safe.
A terageek must also be able to interpret recursive functions, and functions within functions.
Run the following programs: *area51.7b*, *testrec.7b*, *testinner.7b*
Completing level 4 will earn full credit for the assignment.

LEVEL 5: PETAGEEK.
Completing this level would mean matching all the features of the professor's demo program.
A powerful petageek can pass lambdas as arguments and return lambda-closures.
This means defining and calling curried functions and returning mutable closures that capture local state, simulating objects.  
Run all of the following programs:
*testfun2.7b*, *testhigherorder.7b*, *testclosure.7b*, *testcons.7b*

LEVEL 6: EXAGEEK.
The given interpreter is CALL-BY-VALUE.
A magical exageek can also implement a call-by-name version.  
That is, f(e) should not first evaluate e to a normal form (it may not have one).
Since e can contain free variables, make sure that it's evaluated in the correct context inside the function.
Only a few petageeks dare to become exageeks as they could put the planet in danger again...  
Run *testcbn.7b*

----
The next level is not recommended as it requires changing the parser:

LEVEL 7: The ONE ZETAGEEK.
There can only be ONE.
Legends fortell the coming of a super geek with powers to change the world.
Implement arrays for lambda7b.
This means changing more than just the eval and related functions.
The true zetageek will implement a concrete syntax for arrays and modify the expr type and parser to recognize array syntax.

  # defines an array of 4 numbers:
  define A = [1,3,5,7];
  # defines an array of 100 numbers, all initialized to 0
  define B = [0;100]; 
  A[0] = 5;

Arrays are of fixed lengths in memory, and array access, A[i], must take O(1) time.
Arrays must be mutable (but not resizable) and there must be a way to quickly find the length of an array.
Are you the One?
*)

open System
open System.Collections.Generic;
open Option

////////////Abstract Syntax for Language "Lambda7b", version 7e8
type expr =
  | Num of int     // integer constants
  | Str of string  // string literals "abc", includes ""'s inside string
  | Var of string  // alphanumeric identifiers like x, y1, etc
  | Uniop of string * expr   // unary operations such as Uniop "-" (Num 3)
  | Binop of string * expr * expr   // e.g. Binop("*",A,B), Binop("+",A,B)
  | Ifelse of expr * expr * expr
  | While of expr * expr
  | Lambda of string * expr
  | Apply of expr * expr
  | Closure of bindings * expr   // does not correspond to concrete syntax
  | Let of string * expr * expr  // variable bindings
  | Define of string * expr
  | Assign of lvalue:expr * rvalue:expr  // destructive assignment
  | Sequence of expr list   // sequence of expressions separated by ;
  | Void                    // unit, no value (print returns Void)
  | RuntimeError of string  // error with error message
  | Sym of string  // token "*": not an expression, exists pre-parsing only
  | EOF          // EOF - pre-parsing only.

and
  bindings = list<string*expr ref>;; // bindings are lists of pairs

let rec lookup (B:bindings) (x:string) = // lookup stack for binding for x
  match B with
  | (y,e)::bs when x=y -> Some(e)
  | _::bs -> lookup bs x
  | [] -> None
let lookupval B x = (lookup B x) |> map (fun r -> !r) // dereferences
// to add to environment, do non-destructive cons: (x,ref Num(0))::bindings

//// The AST incorporates its own monad for error handling.
let numap f = function // equiv to let numap f arg = match arg with ...
  | Num(n) -> Num(f(n))
  | RuntimeError(e) -> RuntimeError(e)
  | _ -> RuntimeError("cannot map function to non-numerical value");;
let numap2 f a b =
  match (a,b) with
  | (Num(x),Num(y)) -> Num(f(x,y))
  | _ -> RuntimeError("function requires two numerical normal forms");;
let numbind2 f a b =
  match (a,b) with
  | (Num(x),Num(y)) -> f(x,y)
  | _ -> RuntimeError("function requires two numerical normal forms");;
// sample use:
let safediv(x,y) = if y<>0 then Num(x/y) else RuntimeError("div by zero")
// numbind2 safediv (Num 6) (Num 2) will return Num(3)

let numtest p a = // test predicate on num
  match a with
  | Num(x) -> p(x)
  | _ -> false;;
let istrue = function  // for convenience
  | Num(x) when x<>0 -> true
  | _ -> false
let iserr = function
  | RuntimeError(_) -> true
  | _ -> false

///// eval maps AST to another AST in normal form   *********
let normal_form = function
  | Num(_) | Str(_) | Void | Closure(_,_) | RuntimeError(_) -> true
  | _ -> false

// evaluation defined simultaneously by eval and eval_seq:
let rec eval_seq (stack:bindings) (seq:expr list) =
  match seq with
  | Define(x,e)::es ->  // define x = 2+3; x = 3; print x; 
    let temp = ref Void
    let nstack = (x,temp)::stack
    let ve = (eval nstack e)
    if (iserr ve) then ve
    else
      temp := ve
      eval_seq nstack es
  | e::e2::es ->
    eval stack e |> ignore
    eval_seq stack (e2::es)
  | [e] -> eval stack e
  | [] -> Void;
    
and eval (stack:bindings) expression =
  match expression with
  | n when normal_form(n) -> n // normal forms eval to themselves
  | Uniop("-",e) -> (eval stack e) |> numap (fun x -> -1*x)
  | Uniop("!",e) ->
    if istrue(eval stack e) then Num(0)
    else Num(1)
  | Binop("*",a,b) ->
    match ((eval stack a), (eval stack b)) with
    | (Num(x),Num(y)) -> Num(x*y)
    | _ -> RuntimeError("* requires two numbers")
  | Binop("/",a,b) ->
    match ((eval stack a), (eval stack b)) with
    | (Num(x),Num(y)) when y<>0 -> Num(x/y)
    | (Num(x),Num(y)) -> RuntimeError("/ requires a non-zero denominator")
    | _ -> RuntimeError("/ requires two numbers")
  | Binop("%",a,b) ->
    match ((eval stack a), (eval stack b)) with
    | (Num(x),Num(y)) -> Num(x%y)
    | _ -> RuntimeError("% requires two numbers")
  | Binop("+",a,b) ->
    match ((eval stack a), (eval stack b)) with
    | (Num(x),Num(y)) -> Num(x+y)
    | _ -> RuntimeError("+ requires two numbers")
  | Binop("-",a,b) -> numap2 (fun (x,y) -> x-y) (eval stack a) (eval stack b)
  | Binop("<",a,b) ->
    match ((eval stack a), (eval stack b)) with
    | (Num(x),Num(y)) ->
      if (x<y) then Num(1)
      else Num(0)
    | _ -> RuntimeError("< requires two numbers")
  | Binop("&&",a,b) ->
    if not (istrue(eval stack a)) then Num(0)
    elif not (istrue(eval stack b)) then Num(0)
    else Num(1)
  | Binop("||",a,b) ->
    if ((not (istrue(eval stack a))) && (not (istrue(eval stack b)))) then Num(0)
    else Num(1)
  | Binop("==",a,b) ->
    if ((eval stack a)<>(eval stack b)) then Num(0)
    else Num(1)
  | Ifelse(c,a,b) ->
    if istrue(eval stack c) then (eval stack a)
    else (eval stack b)
  | While(a,b) ->
    let mutable last = Void
    while istrue(eval stack a) do  last = (eval stack b)
    last
  | Define(s,e) ->
    let temp = ref Void
    let nstack = (s,temp)::stack
    let ve = (eval nstack e)
    temp := ve
    ve
  | Assign(l,r) ->
    match l with
    | Var(x) ->
      (lookup stack x)
      |> map(fun y -> y := (eval stack r))
      (eval stack r)
    | _ -> RuntimeError("var not on stack, cannot assign")
  | Let(s,e1,e2) ->
    eval ((s,ref (eval stack e1))::stack) e2
  | Lambda(s,e) ->
    Closure(stack,Lambda(s,e))
  | Apply(f,x) ->
    match (eval stack f) with
    | Closure(s,Lambda(l,e)) ->
      (eval ((l,ref (eval stack x))::s) e)
    | _ -> RuntimeError("Invalid apply")
  | Sequence es ->  // sequences are constructed in reverse by parser
    eval_seq stack (List.rev es)
  | Sym("get") ->
    let get = Console.ReadLine();
    let x = ref 0;
    if Int32.TryParse(get,x) then Num(!x) else Str(get)
  | Uniop("print",Str(s)) ->
    let fixeds = s.Substring(1,s.Length-2).Replace("\\n","\n");
    printf "%s" fixeds;
    Void // print always returns Void
  | Uniop("print",Num(n)) -> printf "%d" n; Void;
  | Uniop("print",n) when normal_form(n) -> printf "%A" n; Void;  
  | Uniop("print",e) -> eval stack (Uniop("print",(eval stack e)));
  | Var x ->
    match (lookup stack x) with
    | Some(v) -> eval stack !v
    | None -> RuntimeError(sprintf "%s not found in scope" x)
  | e -> RuntimeError(sprintf "no rule to evaluate %A" e);;
  

//////////////////////////////////////////// PARSING (leave alone)
let precedence = function
  | Sym("+") -> 100
  | Sym("-") -> 100
  | Sym("u-") -> 210
  | Sym("*") -> 200
  | Sym("/") -> 200
  | Sym("%") -> 200
  | Sym("||") -> 80  //400  fixes problem (80 is correct value)
  | Sym("&&") -> 80  //400
  | Sym("==") -> 80  //400  
  | Sym("!") -> 500
  | Sym("(") -> 1000
  | Sym("in") -> 70  // forces shift on let x=1 in x+x
  | Sym("=") -> 50
  | Sym(")") -> 20
  | EOF -> 0   // don't ever shift EOF
  | _ -> 20;;

let binops = ["+";"-";"*";"/";"%";"||";"&&";"==";"<"];
let uniops = ["-";"!";"print"];
let inlist(x,L) = List.exists (fun y -> y=x) L

let proper_expr = function  // identify pre-parsing symbols
  | EOF -> false
  | Sym("get") -> true
  | Sym(_) -> false
  | _ -> true;

// assume all operators are left-associative, for now
let leftassoc x = true

// check for precedence,associativity and proper expressions
let check(a,b,es) =
  match (a,b) with
  | (a,b) when a=b -> leftassoc(a) && List.forall(fun e -> proper_expr(e)) es
  | (a,b) ->
    let (pa,pb) = (precedence(a),precedence(b));
    (pa >= pb) && List.forall(fun e -> proper_expr(e)) es

let applicable = function // things that can be applied as functions
  | Var(_) | Lambda(_,_) | Apply(_,_) -> true
  | _ -> false

let error_report (stack:expr list) (input:expr list) =
  let mutable report = sprintf "Parse error on reading token %A. Top of parse stack: " input.[0]
  let mutable i = 0
  while i<4 && i<stack.Length do
    report <- report + (sprintf "%A, " stack.[i])
    i <- i+1
  report

///// main bottom-up, operator precedence parser:
let rec parse (pstack,input:expr list) =
  match (pstack,input) with
  | ([ast], [EOF]) -> ast  // ACCEPT
  | (Sym(")")::e::Sym("(")::f::pst, la::inp) when (applicable f) && (proper_expr e) ->
    parse (Apply(f,e)::pst, la::inp)
  | (Sym(")")::e::Sym("(")::pst, la::inp) when check(Sym("("),la,[e]) -> parse (e::pst, la::inp)
  | (fcase::Sym("else")::tcase::cond::Sym("if")::pst, la::inp) when check(Sym("if"),la,[cond;tcase;fcase]) ->
    parse (Ifelse(cond,tcase,fcase)::pst, la::inp)
  | (e2::Sym(s)::e1::pst, la::inp) when inlist(s,binops) && check(Sym(s),la,[e1;e2]) ->
    parse (Binop(s,e1,e2)::pst, la::inp)
  | (body::cond::Sym("while")::pst, la::inp) when check(Sym("while"),la,[body;cond]) ->
    parse (While(cond,body)::pst, la::inp)
  | (e::Sym("-")::pst, la::inp) when check(Sym("u-"),la,[e]) ->
    parse (Uniop("-",e)::pst, la::inp) // unary - has higher prec than *
  | (e::Sym(s)::pst, la::inp) when inlist(s,uniops) && check(Sym(s),la,[e]) ->
    parse (Uniop(s,e)::pst, la::inp)
  | (e::Sym("=")::Var(x)::Sym("define")::pst, la::inp) when check(Sym("="),la,[e]) ->
    parse (Define(x,e)::pst, la::inp)
  | (body::Sym("in")::init::Sym("=")::Var(x)::Sym("let")::pst, la::inp) when check(Sym("let"),la,[init;body]) ->
    parse (Let(x,init,body)::pst, la::inp)
  | (e::Sym("=")::Var(x)::pst, la::inp) when check(Sym("="),la,[e]) ->
    parse (Assign(lvalue=Var(x),rvalue=e)::pst, la::inp)
  | (body::Sym(":")::Var(x)::Sym("lambda")::pst, la::inp) when check(Sym("lambda"),la,[body]) ->
    parse(Lambda(x,body)::pst, la::inp)
  | (Sym(";")::e::(Sequence es)::pst, la::inp) when (proper_expr e) ->
    parse ((Sequence (e::es))::pst, la::inp)
  | (Sym(";")::e::pst, la::inp) when (proper_expr e) ->
    parse ((Sequence [e])::pst, la::inp)
  | (pst, la::inp) when la<>EOF -> parse (la::pst, inp)  // shift
  | (p,i) -> RuntimeError(error_report p i);;
//    | (p,i) -> RuntimeError(sprintf "Parse Error: parse stack %A" p);;

/////// lexical tokenizer

// function to convert C# lexToken structures to F# type expr
let convert_token (token:lexToken) =
  match token.token_type with
  | "Integer" -> Num(token.token_value :?> int) // :?> downcasts from obj
  | "Alphanumeric" -> Var(token.token_value :?> string)
  |  "Symbol" | "Keyword" -> Sym(token.token_value :?> string)
  | "StringLiteral" -> Str(token.token_value :?> string)
  | _ -> EOF;;  // if can't tokenize, force end of stream

// reverses list m while applying function f to each value
let rec revmap f M stack =
  match (M,stack) with
  | ([],s) -> s
  | (a::b,s) -> revmap f b ((f a)::s);;

let rec gettokens (lexer:simpleLexer) ax =
  let next_token = lexer.next()
  if next_token=null then ax else gettokens lexer (next_token::ax);;

// collect all tokens into a list, which will enable pattern matching
let tokenize(lexer:simpleLexer) = 
  let tokens = gettokens lexer []
  revmap convert_token tokens [EOF];;


//////////////////////////// "main"
let on_behalf f =
  try (Some (f())) with | _ -> None
  
let main(print_final_result:bool) =
  let argv = Environment.GetCommandLineArgs(); //command-line args
  let lexeropt = on_behalf (fun () -> simpleLexer(argv[1],""))
  for k in ["print";"get";"define";"let";"in";"lambda"] do
    lexeropt |> iter (fun lx -> lx.addKeyword(k))
  // keywords are recognized as symbols instead of variables (Sym, not Var)
  let result =
    lexeropt
    |> map (fun lx -> tokenize lx)
    |> map (fun tokens -> parse([],tokens))
    |> map (fun ast -> eval [] ast)
  match (result,print_final_result) with
  | (Some (RuntimeError e), _ ) ->
    printfn "RuntimeError: %s" e
  | (Some r, true) ->
    printfn "Final Value: %A" r
  | (None,_) -> printfn "no input found"
  | _ -> ()

main(false);  // run main
