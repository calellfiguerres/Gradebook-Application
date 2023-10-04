/**
 * An abstract class that is useful for all OutputFiles to extend
 */
public abstract class GradeOutputFile {
    // The stored tables of categories, grades, and students
    protected LinearHashTable<String, Category> categories;
    protected LinearHashTable<String, Grade> grades;
    protected LinearHashTable<String, Student> students;

    /**
     * Constructor to create a GradeOutputFile
     *
     * @param categories The categories of assignments as a HashTable
     * @param grades The grades as a HashTable
     * @param students The students as a HashTable
     */
    public GradeOutputFile(LinearHashTable<String, Category> categories, LinearHashTable<String, Grade> grades, LinearHashTable<String, Student> students) {
        this.categories = categories;
        this.grades = grades;
        this.students = students;
    }

    /**
     * Creates the output file
     *
     * @param filename The filename to write to
     */
    public abstract void createFile(String filename);
}
