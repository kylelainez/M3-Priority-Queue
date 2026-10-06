package collection;

import model.Student;
import orderStrategy.DefaultSortingMethod;
import orderStrategy.PriorityStrategy;

import java.util.*;

public class PriorityQueue extends AbstractQueue<Student> {
    private final List<Student> queue;
    private final PriorityStrategy method;

    // Run this to use default strategy
    public PriorityQueue(){
        this(new DefaultSortingMethod());
    }

    public PriorityQueue(PriorityStrategy method){
        if (method == null){
            throw new IllegalArgumentException("Strategy method cannot be null");
        }
        this.queue = new ArrayList<>();
        this.method = method;
    }

    public boolean remove(Student student){
        int index = queue.indexOf(student);
        if (index == -1) return false;
        int lastIndex = queue.size() - 1;
        if (index == lastIndex) {
            queue.remove(lastIndex);
        } else {
            queue.set(index, queue.get(lastIndex));
            queue.remove(lastIndex);
            shiftDown(index);
            shiftUp(index);
        }
        return true;
    }

    PriorityQueue createCopy(){
        PriorityQueue copy = new PriorityQueue(method);
        copy.queue.addAll(queue);
        return copy;
    }

    @Override
    public Iterator<Student> iterator() {
        return new PriorityQueueIterator(this, createCopy());
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean offer(Student student) {
        if (student == null) {
            throw new NullPointerException("Student cannot be null");
        }

        queue.add(student);
        shiftUp(queue.size() - 1);
        return true;
    }

    @Override
    public Student poll() {
        if (queue.isEmpty()) {
            return null;
        }
        Student removed = queue.getFirst();
        queue.set(0, queue.getLast());
        queue.removeLast();
        shiftDown(0);
        return removed;
    }

    @Override
    public Student peek() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.getFirst();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[queue.size()];
        for(int i=0; i<queue.size(); i++){
            array[i] = queue.get(i);
        }
        return array;
    }

    @Override
    public String toString() {
        PriorityQueue copy = createCopy();
        StringBuilder result = new StringBuilder("[");

        while (!copy.isEmpty()) {
            Student student = copy.poll();
            result.append(student.getInfo(method.calculatePriority(student)));

            if (!copy.isEmpty()) {
                result.append(", ");
            }
        }

        return result.append("]").toString();
    }

    // Helper Methods

    // Swaps the student from the bottom of the tree to top if it has higher priority than its parent
    private void shiftUp(int index) {
        int parent = (index - 1) / 2;
        // index > parent
        while (index > 0 && method.compare(queue.get(index), queue.get(parent)) > 0) {
            Student temp = queue.get(index);
            queue.set(index, queue.get(parent));
            queue.set(parent, temp);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    // Swaps the student from with one of its children if it has lower priority than either of them
    private void shiftDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int parent = index;
        int largest = parent;

        // leftChild > largest
        if (leftChild < queue.size() && method.compare(queue.get(leftChild), queue.get(largest)) > 0) {
            largest = leftChild;
        }

        if (rightChild < queue.size() && method.compare(queue.get(rightChild), queue.get(largest)) > 0) {
            largest = rightChild;
        }

        if (largest != parent) {
            Student temp = queue.get(parent);
            queue.set(parent, queue.get(largest));
            queue.set(largest, temp);
            shiftDown(largest);
        }
    }
}
