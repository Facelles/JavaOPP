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

    // Тут на 16 тижні ти додаш метод обчислення GPA та сортування за GPA
}