import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * Represents a Category of Assignments
 */
public class Category {
    // Stores the name of the category
    private final String categoryName;

    // Stores the assignments in this category
    private final LinearHashTable<String, Assignment> assignments = new LinearHashTable<>(new SimpleStringHasher());

    /**
     * Creates a named category
     * @param categoryName The name of the category
     */
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the category's name
     *
     * @return The name of the category
     */
    public String getCategoryName() {
        return this.categoryName;
    }

    /**
     * Checks to see if an assignment is in this category
     *
     * @param assignmentName The name of the assignment to check
     * @return Whether the assignment is in this category
     */
    public boolean containsAssignment(String assignmentName) {
        return this.assignments.containsElement(assignmentName);
    }

    /**
     * Gets a specific assignment from this category
     * @param assignmentName THe name of the assignment to get
     * @return The assignment
     */
    public Assignment getAssignment(String assignmentName) {
        return this.assignments.getElement(assignmentName);
    }

    /**
     * Gets a list of names of all assignments in this category, sorted alphabetically
     *
     * @return The list of assignment names
     */
    public ArrayList<String> getAssignmentNames() {
        Vector<String> names = this.assignments.getKeys();

        // Sort the keys and return them in an ArrayList
        // (technically a Vector would be fine...I just prefer ArrayList)
        Collections.sort(names);
        return new ArrayList<>(names);
    }

    /**
     * Gets a list of all Assignments in this category
     *
     * @return The list of assignments
     */
    public ArrayList<Assignment> getAssignments() {
        ArrayList<Assignment> toReturn = new ArrayList<>();
        for (String assignmentNames : this.getAssignmentNames()) {
            toReturn.add(this.assignments.getElement(assignmentNames));
        }
        return toReturn;
    }

    /**
     * Get and calculate the "out of" points in this category
     *
     * @return THe max points this category is "out of"
     */
    public int getCategoryMaxPoints() {
        int maxPoints = 0;
        for (Assignment assignment : getAssignments()) {
            maxPoints += assignment.getMaxGrade();
        }
        return maxPoints;
    }

    /**
     * Add an assignment to this category
     *
     * @param assignment The assignment to add
     */
    public void addAssignment(Assignment assignment) {
        this.assignments.addElement(assignment.getAssignmentName(), assignment);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}
