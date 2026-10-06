package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentTest {

    @Test
    void createsStudentWithValidValues() {
        Student student = new Student("Alice", "123456789", "alice@example.com", 3.5, 90);

        assertEquals("Alice", student.getName());
        assertEquals("123456789", student.getRedId());
        assertEquals(3.5, student.getGpa());
        assertEquals(90, student.getUnits());
    }

    @Test
    void rejectsInvalidGpa() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student("Alice", "123", "alice@example.com", 4.1, 90));
        assertThrows(IllegalArgumentException.class,
                () -> new Student("Alice", "123", "alice@example.com", Double.NaN, 90));
    }

    @Test
    void rejectsInvalidUnits() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student("Alice", "123", "alice@example.com", 3.5, -1));
        assertThrows(IllegalArgumentException.class,
                () -> new Student("Alice", "123", "alice@example.com", 3.5, 151));
    }

    @Test
    void rejectsNullFields() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(null, "123", "alice@example.com", 3.5, 90));
    }

    @Test
    void formatsStudentInformation() {
        Student student = new Student("Alice", "123", "alice@example.com", 3.5, 90);

        assertEquals(
                "Name: Alice, Red ID: 123, Email: alice@example.com, Priority: 64.05",
                student.getInfo(64.05)
        );
    }
}