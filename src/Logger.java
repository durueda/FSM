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

    public void startLogging(String fileName) {
        stopLogging();
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            isLogging = true;
        } catch (IOException e) {
            System.err.println("Logger Error: Unable to open file for logging -> " + fileName);
        }
    }

    public void stopLogging() {
        if (writer != null) {
            writer.close();
            writer = null;
        }
        isLogging = false;
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

    public boolean isLogging() {
        return isLogging;
    }
}
