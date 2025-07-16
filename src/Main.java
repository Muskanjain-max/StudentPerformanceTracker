/* 
  Student Performance Tracker
  Author: Muskan Jain
  Description: Java console-based app to manage student records.
  Features: Add, View, Update, Delete, Save to File, Topper, Average
*/

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        while (true) {
            manager.showMenu();
            int choice = sc.nextInt();
            switch (choice) {
                case 1: manager.addStudent(sc); break;
                case 2: manager.displayAllStudents(); break;
                case 3: manager.updateStudentMarks(sc); break;
                case 4: manager.deleteStudent(sc); break;
                case 5: manager.showTopper(); break;
                case 6: manager.showAverage(); break;
                case 7:
                    System.out.println("👋 Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}

