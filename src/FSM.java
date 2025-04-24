import java.util.*;

public class FSM {
    private Set<Character> symbols;
    private Set<String> states;
    private String initialState;
    private Set<String> finalStates;
    private Map<String, Map<Character, String>> transitions;


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

    /*public void addTransition(char symbol, String currentState,String nextState) {
        HashMap<Character, String> transition;
        transition.putIfAbsent(currentState,new HashMap<>());
        transition.get(currentState).put(symbol,nextState);
    }*/

    public String execute(String input){
        StringBuilder result = new StringBuilder();
        String currentState = initialState;
        result.append(currentState);

        for (char charList : input.toCharArray()) {
            if (!symbols.contains(charList)) {
                return result + "\nError: Invalid Symbol '"+charList+"'\nNO";
            }
        }
        return currentState;
    }

}

