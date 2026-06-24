package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Course;
import entities.Enrollment;
import entities.Student;
import entities.Teacher;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonStorage {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public static void saveStudents(Student[] students, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(students, writer);
        } catch (IOException e) {
            System.out.println("Помилка збереження студентів: " + e.getMessage());
        }
    }

    public static Student[] loadStudents(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Student[] loaded = gson.fromJson(reader, Student[].class);
            return loaded == null ? new Student[0] : loaded;
        } catch (IOException e) {
            return new Student[0];
        }
    }

    public static void saveTeachers(Teacher[] teachers, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(teachers, writer);
        } catch (IOException e) {
            System.out.println("Помилка збереження викладачів: " + e.getMessage());
        }
    }

    public static Teacher[] loadTeachers(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Teacher[] loaded = gson.fromJson(reader, Teacher[].class);
            return loaded == null ? new Teacher[0] : loaded;
        } catch (IOException e) {
            return new Teacher[0];
        }
    }

    public static void saveCourses(Course[] courses, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(courses, writer);
        } catch (IOException e) {
            System.out.println("Помилка збереження курсів: " + e.getMessage());
        }
    }

    public static Course[] loadCourses(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Course[] loaded = gson.fromJson(reader, Course[].class);
            return loaded == null ? new Course[0] : loaded;
        } catch (IOException e) {
            return new Course[0];
        }
    }

    public static void saveEnrollments(Enrollment[] enrollments, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(enrollments, writer);
        } catch (IOException e) {
            System.out.println("Помилка збереження зарахувань: " + e.getMessage());
        }
    }

    public static Enrollment[] loadEnrollments(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Enrollment[] loaded = gson.fromJson(reader, Enrollment[].class);
            return loaded == null ? new Enrollment[0] : loaded;
        } catch (IOException e) {
            return new Enrollment[0];
        }
    }
}
