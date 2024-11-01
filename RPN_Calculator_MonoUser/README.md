# Calculatrice RPN Java Mono-user

## Description

Project that emulate a RPN calculator. It can perform arithmetic operations and handle 2D Vector operations. You can use it in local or remote mode with some
functionnality to save actions (logging) and replay them.

## Functionalities

- Arithmetic operations Addition (+), Substraction (-), Multiplication (*), Division (/)
- Operation on 2D and 1D vectors
- Logging of actions
- Replay of actions
- Other operations : head, pop
- Remote (remote) mode with Telnet on port 12345 and local mode (local) switchable
- Help command

## Pre-requisites

- JDK 17.0.11 or higher

## How to build it

```bash
javac -d out src/*.java
```

It's going to compile all the files in the src directory and put the .class files in the out directory.

## How to use it

```bash
java -cp out Calculatrice 

```

### Options

* -h : Display help
* -user=local : Start the calculator in local mode
* -user=remote : Start the calculator in remote mode
* -log=none : No logging
* -log=rec : Record actions
* -log=replay : Replay actions

### Example of use
    
```bash
 java -cp out Calculatrice -user=local -log=replay
```

Launch the calculator in local mode with replay of actions.

```bash
java -cp out Calculatrice -user=remote -log=rec
```

Launch the calculator in remote mode with recording of actions. To connect to the calculator you can use a Telnet client on port 12345.

Connect to remote :

```bash
telnet localhost 12345
```

PS : During remote or local mode you can use the command "-remote" to switch to remote mode and "-local" to switch to local mode when you are in remote mode.

