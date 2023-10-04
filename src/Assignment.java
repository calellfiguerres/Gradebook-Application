/**
 * Represents an Assignment
 */
public class Assignment {
    // Stores the name of the assignment and its max grade (the "out of" points)
    private final String assignmentName;
    private int maxGrade;

    // Stores the grades for each student (who did the assignment)
    private final LinearHashTable<String, Grade> studentGrades = new LinearHashTable<>(new SimpleStringHasher());

    /**
     * Creates an Assignment with the given name and max grade
     *
     * @param assignmentName The name of the assignment
     * @param maxGrade The max grade of the assignment
     */
    public Assignment(String assignmentName, int maxGrade) {
        this.assignmentName = assignmentName;
        this.maxGrade = maxGrade;
    }

    /**
     * Creates an Assignment with the given name and a max grade of 100
     *
     * @param assignmentName The name of the assignment
     */
    public Assignment(String assignmentName) {
        this(assignmentName, 100);
    }

    /**
     * Returns the assignment name
     *
     * @return The name of the assignment
     */
    public String getAssignmentName() {
        return this.assignmentName;
    }

    /**
     * Sets a new max grade for the assignment
     *
     * @param maxGrade The new max grade for the assignment
     */
    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    /**
     * Gets the max grade for the assignment
     *
     * @return The max grade for the assignment
     */
    public int getMaxGrade() {
        return this.maxGrade;
    }

    /**
     * Adds a student's grade to the assignment
     *
     * @param grade The grade to add
     */
    public void addGrade(Grade grade) {
        this.studentGrades.addElement(grade.getStudentId(), grade);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentName='" + assignmentName + '\'' +
                ", maxGrade=" + maxGrade +
                '}';
    }
}