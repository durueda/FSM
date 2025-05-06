import java.io.*;
import java.util.*;

public class FSM implements Serializable{
    private Set<Character> symbols;
    private Set<String> states;
    private String initialState;
    private Set<String> finalStates;
    private Map<String, Map<Character, String>> transitions;

    public Set<Character> getSymbols() {
        return new HashSet<>(symbols);
    }
    public Set<String> getStates() {
        return new HashSet<>(states);
    }

    public FSM() {
        this.symbols = new HashSet<>();
        this.states = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();
        this.initialState = null;
    }
    public void setSymbols(List<String> inputSymbols) {
        for (String sym : inputSymbols) {
            if (sym.length() != 1 || !Character.isLetterOrDigit(sym.charAt(0))) {
                System.out.println("Warning: invalid symbol " + sym);
                continue;
            }

            char symbol = Character.toLowerCase(sym.charAt(0));
            if (!symbols.add(symbol)) {
                System.out.println("Warning: " + symbol + " was already declared as a symbol");
            }
        }
    }
    public void setStates(List<String> inputStates) {
        for (String st : inputStates) {
            if (!st.matches("[a-zA-Z0-9]+")) {
                System.out.println("Warning: invalid state " + st);
                continue;
            }

            String state = st.toUpperCase();
            if (!states.add(state)) {
                System.out.println("Warning: " + state + " was already declared as a state");
            }

            if (initialState == null) {
                initialState = state;
            }
        }
    }
    /*The purpose of the setSymbols and setStates methods is to validate user input (detect invalid symbols/states),
    update collections in FSM, manage warnings in FR5 and FR6.*/

    public void setInitialState(String state) {
        if (!state.matches("[a-zA-Z0-9]+")) {
            System.out.println("Warning: Invalid state name " + state);
            return;
        }

        String normalized = state.toUpperCase();

        if (!states.contains(normalized)) {
            System.out.println("Warning: " + normalized + " was not previously declared as a state");
            states.add(normalized);
        }

        if (initialState != null) {
            if (initialState.equals(normalized)) {
                System.out.println("Warning: Initial state is already set to " + initialState + ".");
                return;
            } else {
                System.out.println("Warning: Initial state was previously set to " + initialState + ".");
            }
        }

        initialState = normalized;
    }
    //We check if it has been defined before with states.contains(...). If it is not defined, we issue a warning according to FR7 and add it anyway.

    public void setFinalStates(List<String> inputStates) {
        for (String st : inputStates) {
            if (!st.matches("[a-zA-Z0-9]+")) {
                System.out.println("Warning: Invalid state name " + st);
                continue;
            }

            String state = st.toUpperCase();
            if (!states.contains(state)) {
                System.out.println("Warning: " + state + " was not previously declared as a state");
                states.add(state);
            }

            if (!finalStates.add(state)) {
                System.out.println("Warning: " + state + " was already declared as a final state");
            }
        }
    }
    //Strings that are not valid according to FR8 are skipped with a warning.

    public void addTransitions(List<String[]> transitionList) {
        for (String[] parts : transitionList) {
            if (parts.length != 3) {
                System.out.println("Error: transition must have 3 elements");
                continue;
            }

            String symStr = parts[0];
            String current = parts[1].toUpperCase();
            String next = parts[2].toUpperCase();
            //symbol control
            if (symStr.length() != 1 || !Character.isLetterOrDigit(symStr.charAt(0))) {
                System.out.println("Error: Invalid symbol " + symStr);
                continue;
            }

            char symbol = Character.toLowerCase(symStr.charAt(0));
            if (!symbols.contains(symbol)) {
                System.out.println("Error: invalid symbol " + symbol);
                continue;
            }
            //state control
            if (!states.contains(current)) {
                System.out.println("Error: invalid state " + current);
                continue;
            }

            if (!states.contains(next)) {
                System.out.println("Error: invalid state " + next);
                continue;
            }
            //add transitions
            transitions.putIfAbsent(current, new HashMap<>());

            if (transitions.get(current).containsKey(symbol)) {
                System.out.println("Warning: transition already exists for <" + symbol + "," + current + ">, overriding.");
            }

            transitions.get(current).put(symbol, next);
        }
    }
    //Each transition will be in the format: [symbol, currentState, nextState].
    //A code that complies with FR9

    public String print() {
        StringBuilder printString = new StringBuilder();
        printString.append("SYMBOLS");
        for (char symbol : symbols) {
            printString.append(" ").append(symbol);
        }
        printString.append(";\n").append("STATES");
        for (String state : states) {
            printString.append(" ").append(state);
        }
        printString.append(";\n");

        if (initialState != null && !initialState.isEmpty()) {
            printString.append("INITIAL-STATE ").append(initialState).append(";\n");
        }

        if (!finalStates.isEmpty()) {
            printString.append("FINAL-STATES");
            for (String state : finalStates) {
                printString.append(" ").append(state);
            }
            printString.append(";\n");
        }

        printString.append("TRANSITIONS");
        boolean first = true;
        for (String fromState : transitions.keySet()) {
            for (char symbol : transitions.get(fromState).keySet()) {
                String toState = transitions.get(fromState).get(symbol);
                if (!first) {
                    printString.append(",");
                }
                printString.append(" ").append(symbol).append(" ").append(fromState).append(" ").append(toState);
                first = false;
            }
        }
        printString.append(";\n");
        return printString.toString();
    }
    //A code that complies with FR10
    //print() can write to both file and screen
    //If filename == null or empty, it writes to System.out (screen), otherwise it writes to file.

    public void clear() {
        symbols.clear();
        states.clear();
        finalStates.clear();
        transitions.clear();
        initialState = null;

    }
    //A code that complies with FR12

    public String execute(String input) {
        StringBuilder result = new StringBuilder();

        if (initialState == null) {
            return "Error: Initial state is not set.";
        }

        String currentState = initialState;
        result.append(currentState);

        for (char charList : input.toCharArray()) {
            char normalized = Character.toLowerCase(charList);
            if (!symbols.contains(normalized)) {
                result.append("\nError: Invalid symbol '" + charList + "' not declared in SYMBOLS.\nNO");
                return result.toString();
            }
            Map<Character, String> transitionMap = transitions.get(currentState);
            if (transitionMap == null || !transitionMap.containsKey(normalized)) {
                result.append("\nError: No transition for symbol '" + normalized + "' from state '" + currentState + "'\nNO");
                return result.toString();
            }

            currentState = transitionMap.get(normalized);
            result.append(" ").append(currentState);
        }

        if (finalStates.contains(currentState)) {
            result.append("\nYES");
        } else {
            result.append("\nNO");
        }

        return result.toString();
    }
    public void compileToFile(String fileName) {
        try{
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream output = new ObjectOutputStream(fileOut);

            //We write the FSM object to the file.
            output.writeObject(this);
            output.close();
            fileOut.close();
        } catch (IOException ex) {
            System.out.println("Error compiling FSM to file: " + ex.getMessage());
        }
    }
    public void loadFromFile(String fileName) {
        try{
            //Reading the file
            FileInputStream fileIn = new FileInputStream(fileName);

            //Binary Reading
            ObjectInputStream in = new ObjectInputStream(fileIn);
            FSM loadedFSM = (FSM) in.readObject();

            //Updating FSM datas
            this.symbols = loadedFSM.symbols;
            this.states = loadedFSM.states;
            this.initialState = loadedFSM.initialState;
            this.finalStates = loadedFSM.finalStates;
            this.transitions = loadedFSM.transitions;

            in.close();

        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error loading FSM from file: " + e.getMessage());
        }
    }


}

