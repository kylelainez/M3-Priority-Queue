package model;

public class Student {
    private final String name;
    private final String redId;
    private final String email;
    private final double gpa; // 0.0 - 4.0
    private final int units; // 0 - 150

    public Student(String name, String redId, String email, double gpa, int units) {
        // Validate GPA and units boundaries
        if (!Double.isFinite(gpa) || gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }

        if (units < 0 || units > 150) {
            throw new IllegalArgumentException("Units must be between 0 and 150");
        }

        if (name == null || redId == null || email == null) {
            throw new IllegalArgumentException("Student fields cannot be null");
        }

        this.name = name;
        this.redId = redId;
        this.email = email;
        this.gpa = gpa;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public String getRedId() {
        return redId;
    }

    public double getGpa() {
        return gpa;
    }

    public int getUnits() {
        return units;
    }

    // Returns the information of the student
    public String getInfo(double priority) {
        return String.format("Name: %s, Red ID: %s, Email: %s, Priority: %.2f", name, redId, email, priority);
    }
}