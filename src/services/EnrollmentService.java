package services;

import entities.Course;
import entities.Enrollment;
import entities.Student;
import enums.Grade;

public class EnrollmentService {
    private Enrollment[] enrollments = new Enrollment[5];
    private int size = 0;

    public void enrollStudent(Student student, Course course, int semester) {
        if (size == enrollments.length) {
            resize();
        }
        Enrollment newEnrollment = new Enrollment(student, course, semester);
        enrollments[size++] = newEnrollment;
    }

    private void resize() {
        Enrollment[] newArray = new Enrollment[enrollments.length * 2];
        System.arraycopy(enrollments, 0, newArray, 0, enrollments.length);
        enrollments = newArray;
    }

    public void printAllEnrollments() {
        if (size == 0) {
            System.out.println("Зарахувань у системі немає.");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(enrollments[i]);
        }
    }

    public void printEnrollmentsByStudent(int studentId) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (enrollments[i].getStudent().getId() == studentId) {
                System.out.println(enrollments[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Зарахувань для цього студента не знайдено.");
        }
    }

    public void setGrade(int studentId, int courseId, Grade grade) {
        for (int i = 0; i < size; i++) {
            if (enrollments[i].getStudent().getId() == studentId &&
                enrollments[i].getCourse().getId() == courseId) {
                enrollments[i].setGrade(grade);
                System.out.println("Оцінку успішно встановлено.");
                return;
            }
        }
        System.out.println("Зарахування студента на цей курс не знайдено.");
    }

    public void payForCourse(int studentId, int courseId) {
        for (int i = 0; i < size; i++) {
            if (enrollments[i].getStudent().getId() == studentId &&
                enrollments[i].getCourse().getId() == courseId) {
                enrollments[i].setPaid(true);
                System.out.println("Оплату успішно здійснено.");
                return;
            }
        }
        System.out.println("Зарахування студента на цей курс не знайдено.");
    }

    public void printUnpaidEnrollments() {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (!enrollments[i].isPaid()) {
                System.out.println(enrollments[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Неоплачених курсів не знайдено.");
        }
    }

    public Enrollment[] getEnrollmentsForStudent(int studentId) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (enrollments[i].getStudent().getId() == studentId) {
                count++;
            }
        }

        Enrollment[] result = new Enrollment[count];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (enrollments[i].getStudent().getId() == studentId) {
                result[index++] = enrollments[i];
            }
        }
        return result;
    }
    
    public Enrollment[] getAllEnrollments() {
        Enrollment[] currentEnrollments = new Enrollment[size];
        System.arraycopy(enrollments, 0, currentEnrollments, 0, size);
        return currentEnrollments;
    }
    
    public void setAllEnrollments(Enrollment[] newEnrollments) {
        if (newEnrollments == null) return;
        this.enrollments = new Enrollment[Math.max(5, newEnrollments.length)];
        System.arraycopy(newEnrollments, 0, this.enrollments, 0, newEnrollments.length);
        this.size = newEnrollments.length;
    }

    public void deleteEnrollmentsForStudent(int studentId) {
        int i = 0;
        while (i < size) {
            if (enrollments[i].getStudent().getId() == studentId) {
                for (int j = i; j < size - 1; j++) {
                    enrollments[j] = enrollments[j + 1];
                }
                enrollments[--size] = null;
            } else {
                i++;
            }
        }
    }

    public void deleteEnrollmentsForCourse(int courseId) {
        int i = 0;
        while (i < size) {
            if (enrollments[i].getCourse().getId() == courseId) {
                for (int j = i; j < size - 1; j++) {
                    enrollments[j] = enrollments[j + 1];
                }
                enrollments[--size] = null;
            } else {
                i++;
            }
        }
    }
}
