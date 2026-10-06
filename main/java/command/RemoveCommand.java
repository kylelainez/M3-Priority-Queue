package command;

import collection.PriorityQueue;
import model.Student;

public class RemoveCommand implements Command{
    private final PriorityQueue queue;
    private final Student student;
    private boolean removed;

    public RemoveCommand(PriorityQueue queue, Student student){
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
        removed = queue.remove(student);
        if(removed){
            System.out.println("Student Removed Successfully");
        }else{
            System.out.println("Error: Student not found");
        }
    }

    @Override
    public void undo(){
        if (removed) {
            queue.offer(student);
            removed = false;
        }
    }
}
