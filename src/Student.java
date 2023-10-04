/**
 * Represents a Student
 */
public class Student {
    // Stores the student's first name, last name, and ID, respectively
    private final String studentFirstName;
    private final String studentLastName;
    private final String studentId;

    // Stores the student's grades
    private final LinearHashTable<String, Grade> assignmentGrades = new LinearHashTable<>(new SimpleStringHasher());

    /**
     * Create a student with the given first name, last name, and ID
     *
     * @param studentFirstName The first name of the student
     * @param studentLastName The last name of the student
     * @param studentId The student's unique ID
     */
    public Student(String studentFirstName, String studentLastName, String studentId) {
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentId = studentId;
    }

    /**
     * Gets the student's unique ID
     *
     * @return The student's ID
     */
    public String getStudentId() {
        return this.studentId;
    }

    /**
     * Gets the student's first name
     *
     * @return The student's first name
     */
    public String getStudentFirstName() {
        return this.studentFirstName;
    }

    /**
     * Gets the student's last name
     *
     * @return The student's last name
     */
    public String getStudentLastName() {
        return this.studentLastName;
    }

    public void addGrade(Grade grade) {
        this.assignmentGrades.addElement(grade.getAssignmentName(), grade);
    }

    /**
     * Gets the student's name in the specified format
     * "%fn" will be replaced with the student's first name
     * "%ln" will be replaced with the student's last name
     *
     * @param format The string to format
     * @return The formatted string
     */
    public String getNameFormatted(String format) {
        return format.replace("%fn", this.studentFirstName).replace("%ln", this.studentLastName);
    }

    /**
     * Gets all the grades for a student, by assignment
     *
     * @return The student's grades by assignment
     */
    public LinearHashTable<String, Integer> getAssignmentGrades() {
        LinearHashTable<String, Integer> toReturn = new LinearHashTable<>(new SimpleStringHasher());

        for (String key : this.assignmentGrades.getKeys()) {
            Grade assignmentGrade = this.assignmentGrades.getElement(key);
            toReturn.addElement(assignmentGrade.getAssignmentName(), assignmentGrade.getScore());
        }

        return toReturn;
    }

    /**
     * Gets a specific grade on an assignment
     *
     * @param assignmentName The name of the assignment
     * @return The grade the student got on that assignment
     */
    public int getAssignmentGrade(String assignmentName) {
        return this.assignmentGrades.getElement(assignmentName).getScore();
    }

    /**
     * Gets the student's final grade based upon their assignments as a decimal (1.00 = 100%)
     *
     * @return The student's final grade
     */
    public double getFinalGrade() {
        double obtainedPoints = 0;
        double totalPoints = 0;
        for (String key : this.assignmentGrades.getKeys()) {
            Grade assignmentGrade = this.assignmentGrades.getElement(key);
            obtainedPoints += assignmentGrade.getScore();
            totalPoints += assignmentGrade.getAssignment().getMaxGrade();
        }
        return totalPoints > 0 ? obtainedPoints / totalPoints : 0.0;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentFirstName='" + studentFirstName + '\'' +
                ", studentLastName='" + studentLastName + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
