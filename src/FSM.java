import java.util.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
public class FSM {
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

    public String execute(String input){
        StringBuilder result = new StringBuilder();

        if(initialState != null) {
            return "Error: Initial state is not set.";
        }

        String currentState = initialState;
        result.append(currentState);

        for (char charList : input.toCharArray()) {
            char normalized = Character.toLowerCase(charList);
            if (!symbols.contains(normalized)) {
                return result + "\nError: Invalid Symbol '"+charList+"'\nNO";
            }
            //Valid symbols, checking for transitions
            if(!transitions.containsKey(currentState) || !transitions.get(currentState).containsKey(normalized)) {
                return result + "\nNO";
            }
            //Valid transition
            currentState = transitions.get(currentState).get(normalized);
            //New state added
            result.append(" ").append(currentState);
        }
        if(finalStates.contains(currentState)) {
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

            System.out.println("FSM successfully compiled to file: " + fileName);
        } catch (IOException ex) {
            System.out.println("Error compiling FSM to file: " + ex.getMessage());
        }
    }


}

