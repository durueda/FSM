import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        //Opening Message
        System.out.println("FSM DESIGNER 1.0" + LocalDateTime.now());

        //Creating Objects
        FSM fsm = new FSM();
        Logger logger = new Logger();
        CommandHandler commands = new CommandHandler(fsm,logger);

        //For files:
        if(args.length == 1) {
            String fileName = args[0];
            try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
                String line;
                int lineNumber = 0;
                StringBuilder commandBuffer = new StringBuilder();
                boolean isCollectingCommand = false;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    if (!isCollectingCommand) {
                        if (line.endsWith(";")) {
                            //For one line commands
                            System.out.println("> " + line);
                            String reply = commands.processCommand(line);
                            logger.log(line, reply);
                            if (reply != null && !reply.trim().isEmpty()) {
                                System.out.println(reply);
                            }
                        } else {
                            //Multi-Line
                            isCollectingCommand = true;
                            commandBuffer.append(line);
                        }
                    } else {
                        commandBuffer.append(" ").append(line);
                        if (line.endsWith(";")) {
                            String fullCommand = commandBuffer.toString();
                            System.out.println("> " + fullCommand);
                            String reply = commands.processCommand(fullCommand);
                            logger.log(fullCommand, reply);
                            if(reply != null && !reply.trim().isEmpty()) {
                                System.out.println(reply);
                            }
                            commandBuffer.setLength(0);
                            isCollectingCommand = false;
                        }
                    }
                    if (line.trim().equalsIgnoreCase("EXIT;")) {
                        return;
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
        //Command Line Interaction
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print("? ");
            String input = sc.nextLine();
            String reply = commands.processCommand(input);

            //logger
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
