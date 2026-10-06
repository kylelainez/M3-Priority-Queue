package command;

import collection.PriorityQueue;
import model.Student;

public class OfferCommand implements Command{
    private final PriorityQueue queue;
    private final Student student;
    private boolean executed;

    public OfferCommand(PriorityQueue queue, Student student){
        if (queue == null) {
            throw new IllegalArgumentException("Queue cannot be null");
        }
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        this.queue = queue;
        this.student = student;
    }

    @Override
    public void execute(){
        queue.offer(student);
        executed = true;
    }

    @Override
    public void undo(){
        if (executed) {
            queue.remove(student);
            executed = false;
        }
    }
}
