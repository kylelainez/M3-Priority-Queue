package orderStrategy;

import model.Student;

public interface PriorityStrategy {
    double calculatePriority(Student student);

    default int compare(Student s1, Student s2) {
        return Double.compare(calculatePriority(s1), calculatePriority(s2));
    }
}
