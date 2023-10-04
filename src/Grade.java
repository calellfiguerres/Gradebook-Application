import java.util.Random;

/**
 * Represents a Grade between a Student and an Assignment
 */
public class Grade {
    // Random to generate ID numbers as needed
    private static final Random random = new Random();

    // Stores the assignment for the grade
    private final Assignment assignment;

    // Stores the student whom the grade is assigned to
    private final Student student;

    // Stores the student's score
    private int score;

    /**
     * Creates a grade for a given assignment and student, and gives the student
     * that score
     *
     * @param assignment The assignment to be graded
     * @param student The student to be graded
     * @param score The score the student got on the assignment
     */
    public Grade(Assignment assignment, Student student, int score) {
        this.assignment = assignment;
        this.student = student;
        this.score = score;

        // Adds references to this grade on the student (students can query their own grades) and the assignment
        // (assignments can query all of their grades)
        student.addGrade(this);
        assignment.addGrade(this);
    }

    /**
     * Creates a grade for a student and assigns the default grade of 0
     *
     * @param assignment The assignment to be graded
     * @param student The student to be graded
     */
    public Grade(Assignment assignment, Student student) {
        this(assignment, student, 0);
    }

    /**
     * Gets the assignment for which the grade is scoring
     *
     * @return The assignment the grade is scoring
     */
    public Assignment getAssignment() {
        return this.assignment;
    }

    /**
     * Quick handler to get the name of the assignment for which the grade is scoring
     *
     * @return The name of the assignment
     */
    public String getAssignmentName() {
        return this.assignment.getAssignmentName();
    }

    /**
     * Gets the student for which the grade is scoring
     *
     * @return The student the grade is scoring
     */
    public Student getStudent() {
        return this.student;
    }

    /**
     * Gets the ID of the student for which the grade is scoring
     *
     * @return The ID of the student
     */
    public String getStudentId() {
        return this.student.getStudentId();
    }

    /**
     * Sets a new score for the assignment
     *
     * @param score The new score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the current score for the assignment
     *
     * @return The assignment's score
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "assignment=" + assignment +
                ", student=" + student +
                '}';
    }

    /**
     * Creates a grade for a student on an assignment
     *
     * @param assignment The assignment that is being graded
     * @param student The student who is being graded
     * @param score The score the student got on the assignment
     */
    public static Grade createGrade(Assignment assignment, Student student, int score) {
        String gradeId = String.valueOf(random.nextInt(1000000));
        return new Grade(assignment, student, score);
    }
}
