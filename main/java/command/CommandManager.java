package command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandManager {

    private final Deque<Command> history;

    public CommandManager() {
        history = new ArrayDeque<>();
    }

    public void save(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        history.push(command);
    }

    public boolean undo() {
        if (history.isEmpty()) {
            return false;
        }

        Command command = history.pop();
        command.undo();

        return true;
    }

    public boolean canUndo() {
        return !history.isEmpty();
    }
}
