package services;

import entities.Student;
import util.GPAUtils;

public class StudentService {
    private Student[] students = new Student[5]; // Початковий розмір масиву
    private int size = 0;                        // Поточна кількість студентів

    // Додавання (з динамічним розширенням масиву)
    public void addStudent(Student student) {
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
}