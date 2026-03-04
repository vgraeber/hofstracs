(*
  CSC 123 Abstract Machine Implementation Assignment (version 7EA)

  Abstract Machine AM16 is a simple computer with 64K RAM.
  Word size is 32 bits (4 bytes), so there are only 16K memory addresses.
  The machine's CPU contains four general purpose registers, ax, bx, cx and dx, special purpose register sp and bp (top of stack and stack base), and program counter register pc.
  The CPU's arithmetic logic unit (ALU) is limited to integer operations add, sub, imul and idiv.

  AM16 Instruction Set

    add src dst     # add ax bx, add 4 bx
    sub src dst     # only src can be immediate
    imul src dst
    idiv src dst
    push src        # push ax, push 5
    pop dst         # pop ax
    mov src dst
    jmp inst        # unconditional jump to instruction number
    jnz inst        # jump if cx register is not zero
    jz inst         # jump if cx=0
    jn inst         # jump if cx<0
    call inst       # push sp, then jump
    ret             # pc = sp
    nop             # does nothing, but it has a role

  The semantics of an instruction such as (sub ax bx) is bx -= ax: the second operand is also the destination operand.
  There is an exception with the integer division instruction: (idiv ax cx) will leave the quotient in cx and remainder in dx.
  If the destination register is dx, the quotient is discarded.

  Instruction operands can be immediate (3), register (ax), or memory ([3] or [ax]).
  However, AM16 is a RISC architecture.
  This means that memory operands are only allowed in the mov instruction for loading and saving to memory:

    mov [ax] bx   # load from memory address in ax into bx
    mov ax [bx]   # store ax into memory at address bx
    mov [ax] [bx] # NOT ALLOWED IN RISC MACHINES

  Memory operands are also allowed to be immediate (move [16] ax).

  The jump instructions set the pc register to the instruction number.
  When implementing these instructions, be aware that pc is automatically incremented by 1 in the executeProg function found below.

  The call instruction should push the current value of the pc register on the stack, then jump to the indicated instruction number.

  The ret instruction pops the stack into the pc register.

  For example, the following program calculates 6!

    mov 6 cx
    mov 1 ax
    imul cx ax
    sub 1 cx
    jnz 2
    push ax     # push result on stack

  AM16 programs should always leave the final result on top of the stack.

  This program implements A FRAGMENT OF AM16 in F#, including assembler.
  Your assignment is to complete the implementation.
*)

open System;
open System.Collections.Generic;
open Microsoft.FSharp.Math;
open System.Text.RegularExpressions

type Vec<'T> = ResizeArray<'T>;;

type operand = Imm of int | Reg of string | Mem of operand;;

type instruction =
  | ALU of (string*operand*operand)   // ALU("add",Imm 3, Reg "ax") = add 3 ax
  | PUSH of operand
  | POP of operand
  | MOV of operand*operand
  | JMP of int
  | JNZ of int
  | JN of int
  | JZ of int
  | CALL of int
  | RET
  | NOP;;

///////////// Machine Simulation ///////////////

let RAM:int[] = Array.zeroCreate (16*1024) // 64K memory
let mutable sp = 0
let mutable pc = 0
let mutable fault = false
let REGS = Dictionary<string,int>();
REGS.["ax"] <- 0
REGS.["bx"] <- 0
REGS.["cx"] <- 0
REGS.["dx"] <- 0
REGS.["bp"] <- 0

let rec load_operand = function
  | Imm x -> x
  | Reg("sp") -> sp
  | Reg(r) -> REGS.[r]
  | Mem(opr) -> RAM.[ load_operand opr ]

let coredump() =
  fault <- true
  printfn "MACHINE FAULT, CORE DUMPED!"
  printfn "ax=%d, bx=%d, cx=%d, dx=%d" (REGS.["ax"]) (REGS.["bx"]) (REGS.["cx"]) (REGS.["dx"])
  printfn "sp=%d, top portion of stack:" sp
  let mutable i = sp-1
  while (i>=0 && i > sp-8) do
    printfn "%d" (RAM.[i])
    i <- i-1;;

let rec clean cln l r =
  match cln with
  | Imm x -> l + (string x) + r
  | Reg(a) -> l + a + r
  | Mem(a) -> (clean a "[" "]")

let prettyinst inst =
  match inst with
  | ALU("add",a,Reg(b)) -> "add " + (clean a "" "") + " " + b
  | ALU("sub",a,Reg(b)) -> "sub " + (clean a "" "") + " " + b
  | ALU("imul",a,Reg(b)) -> "imul " + (clean a "" "") + " " + b
  | ALU("idiv",a,Reg(b)) -> "idiv " + (clean a "" "") + " " + b
  | PUSH(x) -> "push " + (clean x "" "")
  | POP(r) -> "pop " + (clean r "" "")
  | MOV(a,b) -> "mov " + (clean a "" "") + " " + (clean b "" "")
  | JMP(l) -> "jmp " + (string l)
  | JNZ(l) -> "jnz " + (string l)
  | JZ(l) -> "jz " + (string l)
  | JN(l) -> "jn " + (string l)
  | CALL(l) -> "call " + (string l)
  | RET -> "ret"
  | NOP -> "nop"
  | x -> "Illegal instruction: " + (string x);;

let execute = function
  | ALU("add",a,Reg(b)) -> REGS.[b] <- REGS.[b] + (load_operand a)
  | ALU("sub",a,Reg(b)) -> REGS.[b] <- REGS.[b] - (load_operand a)
  | ALU("imul",a,Reg(b)) -> REGS.[b] <- REGS.[b] * (load_operand a)
  | ALU("idiv",a,Reg(b)) ->
    REGS.["dx"] <- REGS.[b] % (load_operand a)
    if (b<>"dx") then
      REGS.[b] <- REGS.[b] / (load_operand a)
  | PUSH(x) when sp < RAM.Length ->
    RAM.[sp] <- (load_operand x)
    sp <- sp+1
  | POP(Reg(r)) when sp > 0 ->
    sp <- sp-1
    REGS.[r] <- RAM.[sp]
  | MOV(a,b) -> 
    let movstr = (string (prettyinst (MOV(a,b))))
    let bmem = (movstr.[movstr.Length-1]=']')
    if (not bmem) then
      REGS.[movstr.[(movstr.Length-2)..]] <- (load_operand a)
    elif (bmem&&(movstr.[4]<>'[')) then
      let newb = (clean b "" "")
      RAM.[(load_operand (Reg(newb.[1..(newb.Length-2)])))] <- (load_operand a)
    else
      printfn "Illegal instruction: %s" movstr
      coredump()
  | JMP(l) -> pc <- (l-1)
  | JNZ(l) -> 
    if (REGS.["cx"]<>0) then
      pc <- (l-1)
  | JZ(l) -> 
    if (REGS.["cx"]=0) then
      pc <- (l-1)
  | JN(l) -> 
    if (REGS.["cx"]<0) then
      pc <- (l-1)
  | CALL(l) -> 
    RAM.[sp] <- pc
    sp <- sp+1
    pc <- (l-1)
  | RET ->
    sp <- sp-1
    pc <- RAM.[sp]
  | NOP -> ()
  | x -> printfn "Illegal instruction: %s" (string x); coredump();;

let execute_program trace (program:Vec<instruction>) =
  pc <- 0
  sp <- 0
  while (not fault) && pc<program.Count do
    let inst = program.[pc]
    execute inst
    pc <- pc + 1
    if trace then
      printf "%s,    \t" (prettyinst inst)
      printf "ax=%d, bx=%d, cx=%d, dx=%d, sp=%d, pc=%d" (REGS.["ax"]) (REGS.["bx"]) (REGS.["cx"]) (REGS.["dx"]) sp pc
      if sp>0 then printfn " tos=%d" (RAM.[sp-1]) else printfn "";;

///////// ASSEMBLER

let rec translate_operand (x:string) =
  try Imm(int x)
  with | except ->
    let len = x.Length
    if x.[0] = '[' && x.[len-1] = ']'
    then
      Mem(translate_operand (x.Substring(1,len-2)))
    else
      Reg(x);;

// will be using split to split string into array of string tokens

let translate_instruction = function
  | [| "push"; x |] -> Some(PUSH(translate_operand x))
  | [| "pop"; x |] -> Some(POP(translate_operand x))
  | [| "mov"; x; y |] -> Some(MOV(translate_operand x,translate_operand y))
  | [| "jmp"; x |] -> Some(JMP(int x))
  | [| "jn"; x |] -> Some(JN(int x))
  | [| "jnz"; x |] -> Some(JNZ(int x))
  | [| "jz"; x |] -> Some(JZ(int x))
  | [| "call"; x |] -> Some(CALL(int x))
  | [| "ret" |] -> Some(RET)
  | [| "nop" |] -> Some(NOP)
  | [| op; x; y |] -> Some(ALU(op,translate_operand x, translate_operand y))
  | _ -> None

let assemble (inst:string) = translate_instruction (inst.Split())

let readprogram() =
  let mutable line = ""
  let vc = Vec<instruction>()
  while line <> null do
    line <- Console.ReadLine()
    if line<>null && line.Length>0 && line.[0]<>'#' then
       (assemble line) |> Option.iter(fun x -> vc.Add(x))
  vc

execute_program true (readprogram())

(*
  YOUR ASSIGNMENT

1.  Write a 'prettyinst' function that translates an instruction in abstract syntax to a string in concrete syntax: for example, Push(Reg("ax")) should translate to "push ax".
    Uncomment the call the prettyinst in the executeProg function.

2.  Complete the definition of the `execute` function to cover all cases.
    All instructions with valid operands must be accounted for.

3.  Test your program with the various .m16 programs included.
*)
