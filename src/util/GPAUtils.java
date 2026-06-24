package util;

import entities.Student;

public class GPAUtils {

    // Сортування бульбашкою за ПІБ (алфавітний порядок)
    public static void bubbleSortByName(Student[] students, int size) {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (students[j].getName().compareToIgnoreCase(students[j + 1].getName()) > 0) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }

    public static double calculateGPA(entities.Enrollment[] enrollments) {
        if (enrollments == null || enrollments.length == 0) return 0.0;
        
        double totalPoints = 0;
        int totalCourses = 0;
        
        for (entities.Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() != enums.Grade.NA) {
                totalPoints += enrollment.getGrade().getPoints();
                totalCourses++;
            }
        }
        
        return totalCourses == 0 ? 0.0 : totalPoints / totalCourses;
    }

    public static void sortStudentsByGPA(Student[] students, int size, services.EnrollmentService enrollmentService) {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                double gpa1 = calculateGPA(enrollmentService.getEnrollmentsForStudent(students[j].getId()));
                double gpa2 = calculateGPA(enrollmentService.getEnrollmentsForStudent(students[j + 1].getId()));
                
                if (gpa1 < gpa2) { // Сортування за спаданням (descending)
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }

    public static double calculateAverageGpaForCourse(int courseId, entities.Enrollment[] allEnrollments) {
        if (allEnrollments == null || allEnrollments.length == 0) return 0.0;

        double totalPoints = 0;
        int count = 0;

        for (entities.Enrollment e : allEnrollments) {
            if (e.getCourse().getId() == courseId && e.getGrade() != enums.Grade.NA) {
                totalPoints += e.getGrade().getPoints();
                count++;
            }
        }

        return count == 0 ? 0.0 : totalPoints / count;
    }
}