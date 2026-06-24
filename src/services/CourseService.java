package services;

import entities.Course;
import entities.Teacher;

public class CourseService {
    private Course[] courses = new Course[5];
    private int size = 0;

    public void addCourse(Course course) {
        if (findById(course.getId()) != null) {
            throw new IllegalArgumentException("Курс з таким ID вже існує!");
        }
        if (size == courses.length) {
            resize();
        }
        courses[size++] = course;
    }

    private void resize() {
        Course[] newArray = new Course[courses.length * 2];
        System.arraycopy(courses, 0, newArray, 0, courses.length);
        courses = newArray;
    }

    public void printAllCourses() {
        if (size == 0) {
            System.out.println("Курсів у системі немає.");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(courses[i]);
        }
    }

    public Course findById(int id) {
        for (int i = 0; i < size; i++) {
            if (courses[i].getId() == id) {
                return courses[i];
            }
        }
        return null;
    }

    public boolean deleteCourse(int id) {
        for (int i = 0; i < size; i++) {
            if (courses[i].getId() == id) {
                for (int j = i; j < size - 1; j++) {
                    courses[j] = courses[j + 1];
                }
                courses[--size] = null;
                return true;
            }
        }
        return false;
    }

    public void printCoursesByTeacher(int teacherId) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (courses[i].getTeacher().getId() == teacherId) {
                System.out.println(courses[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Курсів для цього викладача не знайдено.");
        }
    }

    public Course[] getAllCourses() {
        Course[] currentCourses = new Course[size];
        System.arraycopy(courses, 0, currentCourses, 0, size);
        return currentCourses;
    }
    
    public void setAllCourses(Course[] newCourses) {
        if (newCourses == null) return;
        this.courses = new Course[Math.max(5, newCourses.length)];
        System.arraycopy(newCourses, 0, this.courses, 0, newCourses.length);
        this.size = newCourses.length;
    }

    public boolean updateCourse(int id, String newName, int newCredits, Teacher newTeacher) {
        Course course = findById(id);
        if (course != null) {
            course.setName(newName);
            course.setCredits(newCredits);
            if (newTeacher != null) {
                course.setTeacher(newTeacher);
            }
            return true;
        }
        return false;
    }

    public void printCoursesByCredits(int credits) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (courses[i].getCredits() == credits) {
                System.out.println(courses[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Курсів з кількістю кредитів " + credits + " не знайдено.");
        }
    }
}
