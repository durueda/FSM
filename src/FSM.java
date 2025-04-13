import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

}

