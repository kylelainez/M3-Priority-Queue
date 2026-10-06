package collection;

import model.Student;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PriorityQueueIterator implements Iterator<Student> {
    private final PriorityQueue priorityQueue;
    private final PriorityQueue traversalQueue;
    private Student lastReturned;
    private boolean canRemove;

    public PriorityQueueIterator(PriorityQueue priorityQueue, PriorityQueue traversalQueue) {
        if (priorityQueue == null) {
            throw new IllegalArgumentException("Priority queue cannot be null");
        }
        if (traversalQueue == null) {
            throw new IllegalArgumentException("Traversal queue cannot be null");
        }

        this.priorityQueue = priorityQueue;
        this.traversalQueue = traversalQueue;
    }

    @Override
    public boolean hasNext() {
        return !traversalQueue.isEmpty();
    }

    @Override
    public Student next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        lastReturned = traversalQueue.poll();
        canRemove = true;
        return lastReturned;
    }

    @Override
    public void remove() {
        if (!canRemove) {
            throw new IllegalStateException("next() must be called before remove()");
        }
        priorityQueue.remove(lastReturned);
        canRemove = false;
    }
}
