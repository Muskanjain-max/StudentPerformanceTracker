import java.util.*;

public class StudentManager {
    private List<Student> students;

    public StudentManager() {
        students = FileManager.loadStudentsFromFile(); // Load on startup
    }

    public void showMenu() {
        System.out.println("\n🎯 Menu:");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Update Marks");
        System.out.println("4. Delete Student");
        System.out.println("5. Show Topper");
        System.out.println("6. Show Average");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    public void addStudent(Scanner sc) {
        System.out.print("Enter name: ");
        sc.nextLine(); // flush
        String name = sc.nextLine();
        System.out.print("Enter roll: ");
        String roll = sc.nextLine();
        System.out.print("Enter marks: ");
        int marks = sc.nextInt();

        // Check duplicate
        for (Student s : students) {
            if (s.getRollNo() == roll) {
                System.out.println("❌ Roll number already exists!");
                return;
            }
        }

        students.add(new Student(name, roll, marks));
        FileManager.saveStudentsToFile(students);
        System.out.println("✅ Student added!");
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("❌ No students yet.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    public void updateStudentMarks(Scanner sc) {
        System.out.print("Enter roll no to update: ");
        String roll = sc.nextLine();
        for (Student s : students) {
            if (s.getRollNo() == roll) {
                System.out.print("Enter new marks: ");
                int marks = sc.nextInt();
                s.setMarks(marks);
                FileManager.saveStudentsToFile(students);
                System.out.println("✅ Updated!");
                return;
            }
        }
        System.out.println("❌ Roll number not found.");
    }

    public void deleteStudent(Scanner sc) {
        System.out.print("Enter roll no to delete: ");
        String roll = sc.nextLine();
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().getRollNo() == roll) {
                it.remove();
                FileManager.saveStudentsToFile(students);
                System.out.println("🗑️ Deleted!");
                return;
            }
        }
        System.out.println("❌ Not found.");
    }

    public void showTopper() {
        if (students.isEmpty()) {
            System.out.println("❌ No students.");
            return;
        }
        Student top = Collections.max(students, Comparator.comparingInt(Student::getMarks));
        System.out.println("🏆 Topper: " + top);
    }

    public void showAverage() {
        if (students.isEmpty()) {
            System.out.println("❌ No students.");
            return;
        }
        double avg = students.stream().mapToInt(Student::getMarks).average().orElse(0);
        System.out.printf("📊 Average marks: %.2f\n", avg);
    }
}
