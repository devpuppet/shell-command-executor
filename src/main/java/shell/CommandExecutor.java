package shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class CommandExecutor {

    public String runShellCommand(String command) {
        String result;
        try {
            result = getResult(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private String getResult(String command) throws Exception {
        Callable<String> callable = () -> {
            try {
                Process process = buildProcess(command);
                return getResult(process);
            } catch (Exception e) {
                return String.format("Exception thrown: %s", e);
            }
        };
        return callable.call();
    }

    private Process buildProcess(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("bash", "-c", command);
        }

        return builder.start();
    }

    private String getResult(Process process) throws IOException, InterruptedException {
        String result = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String line;
        while((line = reader.readLine()) != null) {
            result = result.concat(line + "\n");
        }

        while((line = errorReader.readLine()) != null) {
            result = result.concat("[ERROR] " + line + "\n");
        }

        process.waitFor();

        return result;
    }
}
