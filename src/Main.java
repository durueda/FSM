import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        //Opening Message
        System.out.println("FSM DESIGNER 1.0"+LocalDateTime.now());

        //Creating Objects
        FSM fsm = new FSM();
        Logger logger = new Logger();
        CommandHandler commands = new CommandHandler(fsm,logger);

        //For command files:
        if(args.length == 1) {
            String fileName = args[0];
            try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
                String line;
                while((line = reader.readLine()) != null){
                    if(line.trim().isEmpty()) continue;

                    System.out.println("> " + line);
                    String reply = commands.processCommand(line);

                    //Logger
                    logger.log(line,reply);
                    if(reply != null && !reply.trim().isEmpty()){
                        System.out.println(reply);
                    }

                    if(line.trim().equalsIgnoreCase("EXIT;")){
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
        //Command Line Interaction
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("? ");
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
