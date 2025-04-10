import gui.AppWindow;
import shell.CommandExecutor;

public class Main {

    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        new AppWindow(commandExecutor);
    }

}
