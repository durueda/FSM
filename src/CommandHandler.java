import java.io.*;
import java.util.*;

public class CommandHandler {
        private FSM fsm;
        private Logger logger;

        public CommandHandler(FSM fsm, Logger logger) {
            this.fsm = fsm;
            this.logger = logger;
        }
    public String processCommand(String commandLine) {
        int commentIndex = commandLine.indexOf(';');
        if (commentIndex != -1) {
            commandLine = commandLine.substring(0, commentIndex + 1);
        }
        String[] parts = commandLine.trim().split("\\s+", 2);
        String command = parts[0].toUpperCase();
        String args = parts.length > 1 ? parts[1].trim() : "";

        if (args.endsWith(";")) {
            args = args.substring(0, args.length() - 1).trim();
        }
    }

