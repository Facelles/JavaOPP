package services;

import entities.Teacher;

public class TeacherService {
    private Teacher[] teachers = new Teacher[5];
    private int size = 0;

    public void addTeacher(Teacher teacher) {
        if (findById(teacher.getId()) != null) {
            throw new IllegalArgumentException("Викладач з таким ID вже існує!");
        }
        if (size == teachers.length) {
            resize();
        }
        teachers[size++] = teacher;
    }

    private void resize() {
        Teacher[] newArray = new Teacher[teachers.length * 2];
        System.arraycopy(teachers, 0, newArray, 0, teachers.length);
        teachers = newArray;
    }

    public void printAllTeachers() {
        if (size == 0) {
            System.out.println("Викладачів у системі немає.");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(teachers[i]);
        }
    }

    public Teacher findById(int id) {
        for (int i = 0; i < size; i++) {
            if (teachers[i].getId() == id) {
                return teachers[i];
            }
        }
        return null;
    }

    public boolean deleteTeacher(int id) {
        for (int i = 0; i < size; i++) {
            if (teachers[i].getId() == id) {
                for (int j = i; j < size - 1; j++) {
                    teachers[j] = teachers[j + 1];
                }
                teachers[--size] = null;
                return true;
            }
        }
        return false;
    }

    public Teacher[] getAllTeachers() {
        Teacher[] currentTeachers = new Teacher[size];
        System.arraycopy(teachers, 0, currentTeachers, 0, size);
        return currentTeachers;
    }
    
    public void setAllTeachers(Teacher[] newTeachers) {
        if (newTeachers == null) return;
        this.teachers = new Teacher[Math.max(5, newTeachers.length)];
        System.arraycopy(newTeachers, 0, this.teachers, 0, newTeachers.length);
        this.size = newTeachers.length;
    }

    public boolean updateTeacher(int id, String newName, String newEmail, enums.TeacherPosition newPosition) {
        Teacher teacher = findById(id);
        if (teacher != null) {
            teacher.setName(newName);
            teacher.setEmail(newEmail);
            teacher.setPosition(newPosition);
            return true;
        }
        return false;
    }
}
