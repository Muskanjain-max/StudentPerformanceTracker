public class Student {
    private String name;
    private String rollNo;
    private int marks;

    public Student(String name, String rollNo, int marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public int getMarks() {
        return marks;
    }

    // Setters
    public void setMarks(int marks) {
        this.marks = marks;
    }

    // Display method
    @Override
    public String toString() {
        return "Name: " + name + ", Roll: " + rollNo + ", Marks: " + marks;
    }

}
