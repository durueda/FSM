# FSM Designer 

## SE 116 Software Engineering Project  
Deterministic Finite State Machine (FSM) Implementation in Java  

This project was developed for the SE 116 Software Engineering course. It is a command-line based application that allows users to design, modify, and simulate a Deterministic Finite State Machine (FSM).
The program runs in the terminal and enables the user to define symbols, states, transitions, and test whether a given input string is accepted by the FSM.

## Project Purpose

The goal of this project is to demonstrate how deterministic finite state machines work in practice.

Users can:

- Define symbols  
- Define states  
- Set an initial state  
- Define final (accepting) states  
- Create transitions between states  
- Execute the FSM with an input string  
- See the state trace step by step  
- Save and load FSM configurations  

The FSM processes the input string from left to right. For each symbol, it updates the current state according to the defined transition.  

When the input is fully consumed:
- If the final state is an accepting state → output is **YES**
- Otherwise → output is **NO**

The program also prints the full sequence of visited states (state trace).

## Features

- SYMBOLS command  
- STATES command  
- INITIAL-STATE command  
- FINAL-STATES command  
- TRANSITIONS command  
- EXECUTE command  
- PRINT command  
- COMPILE command  
- LOAD command  
- CLEAR command  
- LOG command  
- EXIT command  

All commands must end with a semicolon (`;`).

---

## Build and Run

```bash
# Compile
javac *.java

# Create executable JAR
jar cfe fsm.jar Main *.class

# Run the program
java -jar fsm.jar
### When the program starts, it displays:
FSM DESIGNER <versionNo> <date-time>
?
```
The ? symbol indicates that the program is waiting for a command.

## Run with a Command File
You can also start the program with a text file containing FSM commands:
```bash
java -jar fsm.jar commands.txt
```
The commands inside the file will be executed automatically.

### Example Usage
```text
SYMBOLS 0 1 2 3;
STATES Q0 Q1 Q2;
INITIAL-STATE Q0;
FINAL-STATES Q2;
TRANSITIONS 0 Q0 Q0, 1 Q0 Q1, 2 Q1 Q2;
EXECUTE 123;
```
### Example output:
```text
Q0 Q1 Q0 Q0 NO
```
## Development Environment

- Java  
- Git & GitHub  
- IntelliJ IDEA
