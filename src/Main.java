import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("FSM DESIGNER <dad96a2> " + LocalDateTime.now());

        FSM fsm = new FSM();
        Logger logger = new Logger();
        CommandHandler commands = new CommandHandler(fsm,logger);

        if(args.length == 1) {
            String fileName = args[0];
            try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
                String line;
                int lineNumber = 0;
                StringBuilder commandBuffer = new StringBuilder();
                boolean isCollectingCommand = false;
                List<String> multiLineCommands = Arrays.asList("SYMBOLS", "STATES", "INITIAL-STATE", "FINAL-STATES", "TRANSITIONS");

                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    // Multi-line continuation
                    if (isCollectingCommand) {
                        commandBuffer.append(" ").append(line);
                        if (line.contains(";")) {
                            String fullCommand = commandBuffer.toString();
                            int semiIndex = fullCommand.indexOf(';');
                            String executable = fullCommand.substring(0, semiIndex + 1);
                            System.out.println("> " + executable);
                            String reply = commands.processCommand(executable);
                            logger.log(executable, reply);
                            if(reply != null && !reply.trim().isEmpty()) {
                                System.out.println(reply);
                            }
                            commandBuffer.setLength(0);
                            isCollectingCommand = false;
                        }
                        continue;
                    }

                    // One-line command with semicolon
                    if (line.contains(";")) {
                        int semiIndex = line.indexOf(';');
                        String commandPart = line.substring(0, semiIndex + 1).trim();
                        System.out.println("> " + commandPart);
                        String reply = commands.processCommand(commandPart);
                        logger.log(commandPart, reply);
                        if (reply != null && !reply.trim().isEmpty()) {
                            System.out.println(reply);
                        }
                        if (commandPart.trim().equalsIgnoreCase("EXIT;")) {
                            return;
                        }
                        continue;
                    }

                    // Line without semicolon — either invalid or start of multi-line
                    String[] parts = line.split("\\s+", 2);
                    String firstWord = parts[0].toUpperCase();

                    if (multiLineCommands.contains(firstWord)) {
                        // Begin multi-line collection
                        isCollectingCommand = true;
                        commandBuffer.append(line);
                    } else {
                        // Invalid command or missing semicolon
                        System.out.println("> " + line);
                        String reply = commands.processCommand(line);
                        logger.log(line, reply);
                        if(reply != null && !reply.trim().isEmpty()) {
                            System.out.println(reply);
                        }
                    }
                }

                if (isCollectingCommand) {
                    System.out.println("Error: Unterminated command at line " + lineNumber);
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
            return;
        }

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print("? ");
            String input = sc.nextLine();
            String reply = commands.processCommand(input);

            logger.log(input,reply);

            if(reply != null && !reply.trim().isEmpty()){
                System.out.println(reply);
            }
            if(input.trim().equalsIgnoreCase("EXIT;")){
                break;
            }
        }
    }
}
