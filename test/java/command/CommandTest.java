package command;

import collection.PriorityQueue;
import model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandTest {

    private final Student student =
            new Student("Alice", "123", "alice@example.com", 3.5, 90);

    @Test
    void offerCommandCanBeUndone() {
        PriorityQueue queue = new PriorityQueue();
        OfferCommand command = new OfferCommand(queue, student);

        command.execute();
        assertEquals(1, queue.size());

        command.undo();
        assertTrue(queue.isEmpty());
    }

    @Test
    void pollCommandCanBeUndone() {
        PriorityQueue queue = new PriorityQueue();
        queue.offer(student);
        PollCommand command = new PollCommand(queue);

        command.execute();
        assertTrue(queue.isEmpty());

        command.undo();
        assertEquals(student, queue.peek());
    }

    @Test
    void removeCommandCanBeUndone() {
        PriorityQueue queue = new PriorityQueue();
        queue.offer(student);
        RemoveCommand command = new RemoveCommand(queue, student);

        command.execute();
        assertTrue(queue.isEmpty());

        command.undo();
        assertEquals(student, queue.peek());
    }

    @Test
    void commandManagerUndoesCommandsInReverseOrder() {
        PriorityQueue queue = new PriorityQueue();
        Student secondStudent =
                new Student("Bob", "456", "bob@example.com", 3.0, 50);
        OfferCommand first = new OfferCommand(queue, student);
        OfferCommand second = new OfferCommand(queue, secondStudent);
        CommandManager manager = new CommandManager();

        first.execute();
        manager.save(first);
        second.execute();
        manager.save(second);

        assertTrue(manager.canUndo());
        assertTrue(manager.undo());
        assertEquals(1, queue.size());
        assertEquals(student, queue.peek());
        assertTrue(manager.undo());
        assertTrue(queue.isEmpty());
        assertFalse(manager.canUndo());
        assertFalse(manager.undo());
    }
}
