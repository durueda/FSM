import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private PrintWriter writer;
    private boolean isLogging;

    public Logger() {
        this.writer = null;
        this.isLogging = false;
    }
    public String startLogging(String fileName) {
        stopLogging(); // Close if already open
        try {
            if (fileName == null || fileName.trim().isEmpty() || !fileName.endsWith(".log") || fileName.length() < 5) {
                return "Error: File cannot be created.";
            }

            writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            isLogging = true;
            return "Logging started to " + fileName;
        } catch (IOException e) {
            return "Error: File cannot be created.";
        }
    }
    public String stopLogging() {
        if (!isLogging || writer == null) {
            return "Error: Logging was not enabled.";
        }

        writer.close();
        writer = null;
        isLogging = false;
        return "Stopped logging.";
    }

    public void log(String commandLine, String output) {
        if (!isLogging || writer == null) {
            return;
        }

        writer.println("> " + commandLine);
        if (output != null && !output.trim().isEmpty()) {
            writer.println(output.trim());
        }
        writer.flush();
    }
}
