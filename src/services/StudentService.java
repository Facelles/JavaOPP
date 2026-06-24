package services;

import entities.Student;
import util.GPAUtils;

public class StudentService {
    private Student[] students = new Student[5];
    private int size = 0;

    public void addStudent(Student student) {
        if (findById(student.getId()) != null) {
            throw new IllegalArgumentException("Студент з таким ID вже існує!");
        }
        if (size == students.length) {
            resize();
        }
        students[size++] = student;
    }

    private void resize() {
        Student[] newArray = new Student[students.length * 2];
        System.arraycopy(students, 0, newArray, 0, students.length);
        students = newArray;
    }

    // Отримання всіх існуючих записів
    public void printAllStudents() {
        if (size == 0) {
            System.out.println("Студентів у системі немає.");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(students[i]);
        }
    }

    // Пошук за ID
    public Student findById(int id) {
        for (int i = 0; i < size; i++) {
            if (students[i].getId() == id) {
                return students[i];
            }
        }
        return null;
    }

    // Видалення (зі зсувом елементів вліво)
    public boolean deleteStudent(int id) {
        for (int i = 0; i < size; i++) {
            if (students[i].getId() == id) {
                for (int j = i; j < size - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[--size] = null; // занулюємо останній елемент
                return true;
            }
        }
        return false;
    }

    // Виклик сортування за ПІБ
    public void sortAndPrint() {
        GPAUtils.bubbleSortByName(students, size);
        printAllStudents();
    }

    public boolean updateStudent(int id, String newName, String newEmail, int newYear, enums.StudentStatus newStatus) {
        Student student = findById(id);
        if (student != null) {
            student.setName(newName);
            student.setEmail(newEmail);
            student.setYear(newYear);
            student.setStatus(newStatus);
            return true;
        }
        return false;
    }

    public void search(String query) {
        boolean found = false;
        String lowerQuery = query.toLowerCase();
        for (int i = 0; i < size; i++) {
            if (students[i].getName().toLowerCase().contains(lowerQuery) || 
                students[i].getEmail().toLowerCase().contains(lowerQuery)) {
                System.out.println(students[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Студентів за запитом '" + query + "' не знайдено.");
        }
    }

    public void filterByStatus(enums.StudentStatus status) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (students[i].getStatus() == status) {
                System.out.println(students[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Студентів зі статусом " + status + " не знайдено.");
        }
    }

    public void filterByYear(int year) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (students[i].getYear() == year) {
                System.out.println(students[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Студентів на " + year + " курсі не знайдено.");
        }
    }
    
    public Student[] getAllStudents() {
        Student[] currentStudents = new Student[size];
        System.arraycopy(students, 0, currentStudents, 0, size);
        return currentStudents;
    }
    
    public void setAllStudents(Student[] newStudents) {
        if (newStudents == null) return;
        this.students = new Student[Math.max(5, newStudents.length)];
        System.arraycopy(newStudents, 0, this.students, 0, newStudents.length);
        this.size = newStudents.length;
    }
}