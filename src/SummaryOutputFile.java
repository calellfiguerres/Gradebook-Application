/**
 * Represents a grading output file with only the summary of each category for each student, based on the provided
 * list of categories, grades, and students
 */
public class SummaryOutputFile extends GradeOutputFile {
    /**
     * Creates a SummaryOutputFile
     *
     * @param categories The categories of assignments as a HashTable
     * @param grades The grades as a HashTable
     * @param students The students as a HashTable
     */
    public SummaryOutputFile(LinearHashTable<String, Category> categories, LinearHashTable<String, Grade> grades, LinearHashTable<String, Student> students) {
        super(categories, grades, students);
    }

    /**
     * Creates the summary file
     *
     * @param filename The filename to write to
     */
    public void createFile(String filename) {
        // create the CSV file headers (first line) and overall (second line)
        StringBuilder summaryFileHeader = new StringBuilder("ID,Name,Final Grade,");
        StringBuilder summaryFileOverall = new StringBuilder(",OVERALL,,");

        // iterate through each category and append its name, capitalized to the file header
        // additionally, calculate the max points in that category to the overall
        for (String categoryKey : categories.getKeys()) {
            Category category = categories.getElement(categoryKey);
            summaryFileHeader.append(Utilities.capitalize(category.getCategoryName())).append(",");
            summaryFileOverall.append(category.getCategoryMaxPoints()).append(",");
        }

        // Finalize these Strings, removing the trailing comma
        String summaryFileHeaderFinal = summaryFileHeader.substring(0, summaryFileHeader.length() - 1);
        String summaryFileOverallFinal = summaryFileOverall.substring(0, summaryFileOverall.length() - 1);

        // Create the students' grades
        StringBuilder summaryFileStudents = new StringBuilder();

        // Iterate on each student
        for (String studentKey : students.getKeys()) {
            Student student = students.getElement(studentKey);

            // Calculate the student's final grade, and add it to their summary line, along with ID and name
            String studentFinalGradeString = String.format("%.2f", Math.round(student.getFinalGrade() * 10000.0) / 100.0);
            StringBuilder studentCategoryGrades = new StringBuilder(student.getStudentId() + "," + student.getNameFormatted("\"%ln, %fn\"") + "," + studentFinalGradeString + ",");

            // Iterate on each category
            for (String categoryKey : categories.getKeys()) {
                Category category = categories.getElement(categoryKey);
                int categoryGrade = 0;

                // Iterate on each assignment in a category, adding the assignment's grade to that category's
                // overall grade; then add it to the student's summary line
                for (Assignment assignment : category.getAssignments()) {
                    categoryGrade += student.getAssignmentGrade(assignment.getAssignmentName());
                }
                studentCategoryGrades.append(categoryGrade).append(",");
            }

            // Add the new line to the StringBuilder of all other student summaries, replacing the trailing comma
            // with a new line
            summaryFileStudents.append(studentCategoryGrades.substring(0, studentCategoryGrades.length() - 1)).append("\n");
        }

        // Finalize the students' summaries
        String summaryFileStudentsFinal = summaryFileStudents.toString();

        // Assemble the final string containing the CSV data; then write to the summary file
        String summaryFileFinal = summaryFileHeaderFinal + "\n" + summaryFileOverallFinal + "\n" + summaryFileStudentsFinal;
        Utilities.writeToFile(filename, summaryFileFinal);
    }
}
