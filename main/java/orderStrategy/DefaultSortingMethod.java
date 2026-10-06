package orderStrategy;

import model.Student;

public class DefaultSortingMethod implements PriorityStrategy{

    @Override
    public double calculatePriority(Student student){
        return (student.getGpa() * 0.3) + (student.getUnits()*0.7);
    }
}
