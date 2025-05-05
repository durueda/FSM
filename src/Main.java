import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        //Opening Message
        System.out.println("FSM DESIGNER 1.0 "+ LocalDateTime.now());

        //Creating Objects
        FSM fsm = new FSM();
        Logger logger = new Logger();
        CommandHandler commands = new CommandHandler(fsm,logger);

        //For command files:
        if(args.length == 1) {
            String fileName = args[0];
            try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
                String line;
                StringBuilder commandBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    commandBuilder.append(line.trim()).append(" ");
                    if (line.trim().endsWith(";")) {
                        String fullCommand = commandBuilder.toString().trim();
                        System.out.println("> " + fullCommand);
                        String reply = commands.processCommand(fullCommand);
                        logger.log(fullCommand, reply);

                        if (reply != null && !reply.trim().isEmpty()) {
                            System.out.println(reply);
                        }

                        if (fullCommand.equalsIgnoreCase("EXIT;")) {
                            return;
                        }

                        commandBuilder.setLength(0); // temizle
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
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
