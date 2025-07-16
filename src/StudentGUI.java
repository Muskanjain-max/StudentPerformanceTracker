import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
//import java.io.*;

public class StudentGUI extends JFrame {
    private java.util.List<Student> students;

    public StudentGUI() {
        students = FileManager.loadStudentsFromFile();

        setTitle("Student Performance Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // 1️⃣ Add Tab
        JPanel addPanel = new JPanel(new GridLayout(4, 2));
        JTextField nameField = new JTextField();
        JTextField rollField = new JTextField();
        JTextField marksField = new JTextField();
        JButton addButton = new JButton("Add Student");

        addPanel.add(new JLabel("Name:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("Roll No:"));
        addPanel.add(rollField);
        addPanel.add(new JLabel("Marks:"));
        addPanel.add(marksField);
        addPanel.add(new JLabel());
        addPanel.add(addButton);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String rollNo = rollField.getText();
            int marks;
            try {
                marks = Integer.parseInt(marksField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid marks.");
                return;
            }

            List<Student> students = FileManager.loadStudentsFromFile();  // Load existing students

            // Check for duplicate roll number
            for (Student s : students) {
                if (s.getRollNo().equals(rollNo)) {
                    JOptionPane.showMessageDialog(this, "Roll number already exists! ");
                    return;
                }
            }

            students.add(new Student(name, rollNo, marks));
            FileManager.saveStudentsToFile(students);
            JOptionPane.showMessageDialog(this, "Student Added!");

            nameField.setText("");
            rollField.setText("");
            marksField.setText("");
        });

        // 2️⃣ View Tab
        JPanel viewPanel = new JPanel(new BorderLayout());
        JTextArea viewArea = new JTextArea();
        JButton refreshBtn = new JButton("Refresh List");

        viewPanel.add(new JScrollPane(viewArea), BorderLayout.CENTER);
        viewPanel.add(refreshBtn, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Student s : students) {
                sb.append("Name: ").append(s.getName())
                  .append(", Roll: ").append(s.getRollNo())
                  .append(", Marks: ").append(s.getMarks())
                  .append("\n");
            }
            viewArea.setText(sb.toString());
        });

        // 3️⃣ Update Tab
        JPanel updatePanel = new JPanel(new GridLayout(3, 2));
        JTextField updateRollField = new JTextField();
        JTextField updateMarksField = new JTextField();
        JButton updateButton = new JButton("Update Marks");

        updatePanel.add(new JLabel("Roll No:"));
        updatePanel.add(updateRollField);
        updatePanel.add(new JLabel("New Marks:"));
        updatePanel.add(updateMarksField);
        updatePanel.add(new JLabel());
        updatePanel.add(updateButton);

        updateButton.addActionListener(e -> {
            String roll = updateRollField.getText();
            int newMarks;
            try {
                newMarks = Integer.parseInt(updateMarksField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid marks entered.");
                return;
            }

            boolean found = false;
            for (Student s : students) {
                if (s.getRollNo().equals(roll)) {
                    s.setMarks(newMarks);
                    found = true;
                    break;
                }
            }

            if (found) {
                FileManager.saveStudentsToFile(students);
                JOptionPane.showMessageDialog(this, "Marks Updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }

            updateRollField.setText("");
            updateMarksField.setText("");
        });

        // 4️⃣ Delete Tab
        JPanel deletePanel = new JPanel(new GridLayout(2, 2));
        JTextField deleteRollField = new JTextField();
        JButton deleteButton = new JButton("Delete Student");

        deletePanel.add(new JLabel("Roll No:"));
        deletePanel.add(deleteRollField);
        deletePanel.add(new JLabel());
        deletePanel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            String roll = deleteRollField.getText();
            boolean removed = students.removeIf(s -> s.getRollNo().equals(roll));
            if (removed) {
                FileManager.saveStudentsToFile(students);
                JOptionPane.showMessageDialog(this, "Student Deleted!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
            deleteRollField.setText("");
        });

        // 5️⃣ Stats Tab (Topper + Average)
        JPanel statsPanel = new JPanel(new GridLayout(2, 1));
        JButton topperBtn = new JButton("Show Topper");
        JButton averageBtn = new JButton("Show Average");

        statsPanel.add(topperBtn);
        statsPanel.add(averageBtn);

        topperBtn.addActionListener(e -> {
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No data.");
                return;
            }
            Student topper = Collections.max(students, Comparator.comparingInt(Student::getMarks));
            JOptionPane.showMessageDialog(this, "Topper: " + topper.getName() + " (" + topper.getMarks() + ")");
        });

        averageBtn.addActionListener(e -> {
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No data.");
                return;
            }
            double avg = students.stream().mapToInt(Student::getMarks).average().orElse(0.0);
            JOptionPane.showMessageDialog(this, "Average Marks: " + avg);
        });

        // 🌟 Add all tabs in proper order
        tabs.addTab("Add", addPanel);
        tabs.addTab("View", viewPanel);
        tabs.addTab("Update", updatePanel);
        tabs.addTab("Delete", deletePanel);
        tabs.addTab("Stats", statsPanel);

        add(tabs);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGUI::new);
    }
}
