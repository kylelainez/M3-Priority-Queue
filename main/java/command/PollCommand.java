package command;

import collection.PriorityQueue;
import model.Student;

public class PollCommand implements Command{
    private final PriorityQueue queue;
    private Student student;
    private boolean executed;

    public PollCommand(PriorityQueue queue){
        if (queue == null) {
            throw new IllegalArgumentException("Queue cannot be null");
        }
        this.queue = queue;
    }

    @Override
    public void execute(){
        student = queue.poll();
        executed = student != null;
    }

    @Override
    public void undo(){
        if (executed) {
            queue.offer(student);
            executed = false;
        }
    }
}
