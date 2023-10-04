/**
 * Represents a grading output file with detailed on each student's assignment, based on the provided list of
 * categories
 */
public class DetailsOutputFile extends GradeOutputFile {
    /**
     * Creates a DetailsOutputFile
     *
     * @param categories The categories of assignments as a HashTable
     * @param grades The grades as a HashTable
     * @param students The students as a HashTable
     */
    public DetailsOutputFile(LinearHashTable<String, Category> categories, LinearHashTable<String, Grade> grades, LinearHashTable<String, Student> students) {
        super(categories, grades, students);
    }

    /**
     * Creates the details file
     *
     * @param filename The filename to write to
     */
    @Override
    public void createFile(String filename) {
        // Create the CSV headers (first line) and overall grades (second line)
        StringBuilder detailsFileHeaderBuilder = new StringBuilder("ID,Name,");
        StringBuilder detailsFileOverallBuilder = new StringBuilder(",OVERALL,");
        for (String categoryKey : categories.getKeys()) {
            Category category = categories.getElement(categoryKey);
            for (Assignment assignment : category.getAssignments()) {
                detailsFileHeaderBuilder.append(assignment.getAssignmentName()).append(",");
                detailsFileOverallBuilder.append(assignment.getMaxGrade()).append(",");
            }
        }

        // Convert them to actual strings, then strip the trailing comma
        String detailsFileHeaderFinal = detailsFileHeaderBuilder.substring(0, detailsFileHeaderBuilder.length() - 1);
        String detailsFileOverallFinal = detailsFileOverallBuilder.substring(0, detailsFileOverallBuilder.length() - 1);

        // Create the line of grades for each student
        StringBuilder detailsFileStudents = new StringBuilder();

        // Iterate on each student
        for (String studentKey : students.getKeys()) {
            Student student = students.getElement(studentKey);

            // Create each student's details line with their ID and name
            StringBuilder studentLine = new StringBuilder(student.getStudentId() + "," + student.getNameFormatted("\"%ln, %fn\"") + ",");

            // Iterate on each category
            for (String categoryKey : categories.getKeys()) {
                Category category = categories.getElement(categoryKey);
                StringBuilder categoryPart = new StringBuilder();

                // Iterate on each assignment, adding the grade of each assignment to the line representing
                // the student's grade; then append it to the line detailing their grade
                for (Assignment assignment : category.getAssignments()) {
                    categoryPart.append(student.getAssignmentGrade(assignment.getAssignmentName())).append(",");
                }
                studentLine.append(categoryPart);
            }

            // Append the finished line with a student's grade in detail to the String containing all other details
            detailsFileStudents.append(studentLine.substring(0, studentLine.length() - 1)).append("\n");
        }

        // Finalize the students' details line
        String detailsFileStudentsFinal = detailsFileStudents.toString();

        // Assemble the final file contents and write it to disk
        String detailsFileFinal = detailsFileHeaderFinal + "\n" + detailsFileOverallFinal + "\n" + detailsFileStudentsFinal;
        Utilities.writeToFile(filename, detailsFileFinal);
    }
}
