import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // Random to generate ID numbers as needed
    private static final Random random = new Random();

    // Store the list of categories, grades, and students, respectively
    private static final LinearHashTable<String, Category> categories = new LinearHashTable<>(new SimpleStringHasher());
    private static final LinearHashTable<String, Grade> grades = new LinearHashTable<>(new SimpleStringHasher());
    private static final LinearHashTable<String, Student> students = new LinearHashTable<>(new SimpleStringHasher());

    /**
     * The main method
     * @param args (Command line) arguments
     */
    public static void main(String[] args) {
        // for easy testing in IntelliJ only
//        args = new String[] {"homework_1.csv", "homework_2.csv", "quizzes_1.csv", "quizzes_2.csv", "exams_1.csv", "exams_2.csv"};

        for (String filename : args) {
            // ignore any provided args that aren't csv files
            if (filename.endsWith(".csv")) {
                System.out.println("Processing '" + filename + "'");
                processCsv(filename);
            }
        }
        System.out.println();

        // create the details file
        DetailsOutputFile detailsOutputFile = new DetailsOutputFile(categories, grades, students);
        detailsOutputFile.createFile("output_details.csv");

        // create summary file
        SummaryOutputFile summaryOutputFile = new SummaryOutputFile(categories, grades, students);
        summaryOutputFile.createFile("output_summary.csv");

        // Show confirmation that everything is done.
        System.out.println();
        System.out.println("Done.");

    }

    /**
     * Takes in a filename for a CSV file with the desired grade format, and processes it
     * into each grade component
     *
     * @param filename The filename of the file to parse
     */
    public static void processCsv(String filename) {
        try {
            // attempt to read the file
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Split each line into its individual comma-separated components
            String[] columnHeaders = Utilities.parseCsvLine(scanner.nextLine());

            String[] filenameParts = filename.split("_");
//            int fileNum = Integer.parseInt(filenameParts[1]);

            // Create each category of assignments, if it isn't already created
            String categoryName = filenameParts[0];
            if (!categories.containsElement(categoryName)) {
                System.out.println("\tCreating assignment category '" + categoryName + "'");
                categories.addElement(categoryName, new Category(categoryName));
            }

            // Create each assignment, if it isn't already created
            Category currCategory = categories.getElement(categoryName);
            // Run from column headers 2 to end, since 0 and 1 are student "ID" and "Name"
            System.out.println("\tCreating assignments for category '" + categoryName +"'");
            for (int i = 3; i < columnHeaders.length; i++) {
                String header = columnHeaders[i];
                if (!currCategory.containsAssignment(header)) {
                    System.out.println("\t\tAdding assignment '" + header + "'");
                    currCategory.addAssignment(new Assignment(header));
                }
            }

            // Now we're in all remaining students.
            System.out.println("\tCreating student grades");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columnValues = Utilities.parseCsvLine(line);
                String studentId = columnValues[0];
                String studentName = columnValues[1];

                // the case that the max grade "students" assignments are also in that file
                if (studentName.equals("OVERALL")) {
                    for (int i = 3; i < columnValues.length; i++) {
                        Assignment currAssignment = currCategory.getAssignment(columnHeaders[i]);
                        System.out.println("\t\tAdding max grade for assignment '" + currAssignment.getAssignmentName() + "'");
                        int maxGrade = Integer.parseInt(columnValues[i]);
                        currAssignment.setMaxGrade(maxGrade);
                    }

                // Now deal with student grades
                } else {
                    // Split each student name into its parts
                    String[] nameParts = studentName.strip().split(",");
                    String studentFirstName = nameParts[1].strip();
                    String studentLastName = nameParts[0].strip();

                    // Create a student if they don't already exist
                    if (!students.containsElement(studentId)) {
                        System.out.println("\t\tCreating student '" + studentLastName + ", " + studentFirstName + "' <" + studentId + ">");
                        students.addElement(studentId, new Student(studentFirstName, studentLastName, studentId));
                    }

                    // Create a grade for each student
                    Student currStudent = students.getElement(studentId);
                    System.out.println("\t\tAdding grades for student '" + currStudent.getNameFormatted("%ln, %fn") + "' <" + currStudent.getStudentId() +">.");
                    for (int i = 3; i < columnValues.length; i++) {
                        Assignment currAssignment = currCategory.getAssignment(columnHeaders[i]);
                        int score = !columnValues[i].equals("") ? Integer.parseInt(columnValues[i]) : 0; // A score of 0 is assigned if a grade wasn't given
                        System.out.println("\t\t\tAdding grade of '" + score + "' for assignment '" + currAssignment.getAssignmentName() + "'");
                        createGrade(currAssignment, currStudent, score);
                    }
                }
            }

        // gracefully skip the file if it doesn't exist
        } catch (FileNotFoundException e) {
            System.out.println("\tFile not found: " + filename + ". Skipping...");
        }
    }

    /**
     * Creates a grade for a student on an assignment
     *
     * @param assignment The assignment that is being graded
     * @param student The student who is being graded
     * @param score The score the student got on the assignment
     */
    public static void createGrade(Assignment assignment, Student student, int score) {
        String gradeId = String.valueOf(random.nextInt(1000000));
        grades.addElement(gradeId, new Grade(assignment, student, score));
    }
}
