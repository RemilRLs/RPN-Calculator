## Description
RPN Multi-User Java Calculator is a Reverse Polish Notation (RPN) calculator application designed to work in multi-user mode. It allows multiple users to connect to the server simultaneously and perform basic arithmetic operations as well as 2D vector operations. The server can manage a shared stack or individual stacks per user, depending on the configuration chosen.


## Functionalities

- Arithmetic operations Addition (+), Substraction (-), Multiplication (*), Division (/)
- Operation on 2D and 1D vectors
- Other operations : head, pop and pile (to display the stack)
- Help command
- Stack Mode: Shared (share) or Individual (multiple)


## Pre-requisites

- JDK 17.0.11 or higher

## How to build it

```bash
javac -d out src/*.java
```
It's going to compile all the files in the src directory and put the .class files in the out directory.

## How to use it

```bash
java -cp out ServerCalcRPL 
```

By default it is in shared mode.

## Execute

```bash
java -cp out ServerCalcRPL -stack=[shared|multiple]
```

* -stack=shared : Start the calculator in shared stack mode (so everyone shares the same stack)
* -stack=multiple : Start the calculator in multiple stack mode (so everyone has its own stack)

### Example

```bash
java -cp out ServerCalcRPL -stack=shared
```

Here eveyone shares the same stack.

Connect to remote :

```bash
telnet localhost 12345
```
