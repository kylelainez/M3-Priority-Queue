package collection;

import model.Student;
import orderStrategy.PriorityStrategy;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTest {

    private final Student lowPriority =
            new Student("Low", "1", "low@example.com", 2.0, 20);
    private final Student mediumPriority =
            new Student("Medium", "2", "medium@example.com", 3.0, 60);
    private final Student highPriority =
            new Student("High", "3", "high@example.com", 4.0, 100);

    @Test
    void offersAndPollsStudentsInDefaultPriorityOrder() {
        PriorityQueue queue = new PriorityQueue();
        queue.offer(mediumPriority);
        queue.offer(lowPriority);
        queue.offer(highPriority);

        assertEquals(highPriority, queue.peek());
        assertEquals(highPriority, queue.poll());
        assertEquals(mediumPriority, queue.poll());
        assertEquals(lowPriority, queue.poll());
        assertTrue(queue.isEmpty());
        assertNull(queue.poll());
    }

    @Test
    void usesInjectedStrategyForOrdering() {
        PriorityStrategy gpaFirst = student -> student.getGpa();
        PriorityQueue queue = new PriorityQueue(gpaFirst);

        queue.offer(lowPriority);
        queue.offer(highPriority);
        queue.offer(mediumPriority);

        assertNull(queue.poll());
        assertEquals(highPriority, queue.poll());
        assertEquals(mediumPriority, queue.poll());
        assertEquals(lowPriority, queue.poll());
    }

    @Test
    void iteratorTraversesByPriorityWithoutChangingQueue() {
        PriorityQueue queue = new PriorityQueue();
        queue.offer(mediumPriority);
        queue.offer(lowPriority);
        queue.offer(highPriority);

        Iterator<Student> iterator = queue.iterator();

        assertEquals(highPriority, iterator.next());
        assertEquals(mediumPriority, iterator.next());
        assertEquals(lowPriority, iterator.next());
        assertFalse(iterator.hasNext());
        assertEquals(highPriority, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void iteratorRemoveRemovesLastReturnedStudentFromOriginalQueue() {
        PriorityQueue queue = new PriorityQueue();
        queue.offer(lowPriority);
        queue.offer(highPriority);

        Iterator<Student> iterator = queue.iterator();
        assertEquals(highPriority, iterator.next());
        iterator.remove();

        assertEquals(1, queue.size());
        assertEquals(lowPriority, queue.peek());
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void iteratorThrowsWhenExhausted() {
        PriorityQueue queue = new PriorityQueue();
        queue.offer(lowPriority);
        Iterator<Student> iterator = queue.iterator();

        iterator.next();

        assertThrows(java.util.NoSuchElementException.class, iterator::next);
    }

    @Test
    void toStringListsStudentsByPriorityWithoutChangingQueue() {
        PriorityQueue queue = new PriorityQueue();
        queue.offer(lowPriority);
        queue.offer(highPriority);

        String output = queue.toString();

        assertTrue(output.indexOf("Name: High") < output.indexOf("Name: Low"));
        assertTrue(output.contains("Priority: 71.20"));
        assertTrue(output.contains("Priority: 14.60"));
        assertEquals(highPriority, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void rejectsInvalidQueueInputs() {
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueue(null));
        assertThrows(NullPointerException.class, () -> new PriorityQueue().offer(null));
    }
}
