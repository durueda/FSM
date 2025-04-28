import java.util.List;
import java.util.Arrays;
public class CommandHandler {
    private FSM fsm;
    private Logger logger;

    public CommandHandler(FSM fsm, Logger logger) {
        this.fsm = fsm;
        this.logger = logger;
    }

    public String processCommand(String commandLine) {
        int commentIndex = commandLine.indexOf(';');
        //We check if there is a ';' in the command line coming from the user.
        if (commentIndex != -1) {
            commandLine = commandLine.substring(0, commentIndex + 1);
        }/* If ';' is found, we only take the part up to that point.
         In this way, the text after the comment (; post) is canceled.*/

        String[] parts = commandLine.trim().split("\\s+", 2);
        //command line is divided into 2 parts command,arguments(symbols and 1,2,3)
        String args = parts.length > 1 ? parts[1].trim() : "";
        //If something is written after the command, we take it as an argument.
        String command = parts[0].toUpperCase();//Since all commands are case-insensitive

        if (args.endsWith(";")) {
            args = args.substring(0, args.length() - 1).trim();
        }//We end the command line with ; but we need to remove ; part when we move on to the process.

        switch (command) {
            case "SYMBOLS":
                if (args.isEmpty()) {
                    return "Current symbols: " + fsm.getSymbols();
                } else {
                    List<String> symbols = Arrays.asList(args.split("\\s+"));
                    fsm.setSymbols(symbols);
                    return "Symbols updated.";
                }
            case "STATES":
                if (args.isEmpty()) {
                    return "Current states: " + fsm.getStates();
                } else {
                    List<String> states = Arrays.asList(args.split("\\s+"));
                    fsm.setStates(states);
                    return "States updated.";
                }
            case "INITIAL-STATE":
                if (args.isEmpty()) {
                    return "Error: No initial state provided.";
                } else {
                    fsm.setInitialState(args);
                    return "Initial state set to " + args.toUpperCase();
                }
            case "FINAL-STATES":
                if (args.isEmpty()) {
                    return "Error: No final states provided.";
                } else {
                    List<String> finals = Arrays.asList(args.split("\\s+"));
                    fsm.setFinalStates(finals);
                    return "Final states updated.";
                }


        }
    }
}