import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Logger {
    private String logFileName;

    public Logger(String logFileName) {
        this.logFileName = logFileName;
    }

    public void log(String message) {
        try (PrintWriter out = new PrintWriter(new FileWriter(logFileName, true))) {
            LocalDateTime now = LocalDateTime.now();
            out.println("[" + now + "] " + message);
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}
