import java.io.*;
import java.util.*;

public class FileManager {

    private static final String FILE_NAME = "students.txt";

    public static void saveStudentsToFile(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.write(s.getName() + "," + s.getRollNo() + "," + s.getMarks());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving students: " + e.getMessage());
        }
    }

    public static List<Student> loadStudentsFromFile() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                String roll = data[1];
                int marks = Integer.parseInt(data[2]);
                students.add(new Student(name, roll, marks));
            }
        } catch (IOException e) {
            System.out.println("⚠️ No data file found. Starting fresh.");
        }
        return students;
    }
}

