package entities;

import enums.Grade;
import interfaces.Payable;

public class Enrollment implements Payable {
    private Student student;
    private Course course;
    private int semester;
    private Grade grade;
    private boolean paid;

    public Enrollment(Student student, Course course, int semester) {
        if (student == null) throw new IllegalArgumentException("Студент не може бути null");
        if (course == null) throw new IllegalArgumentException("Курс не може бути null");
        if (semester <= 0) throw new IllegalArgumentException("Семестр має бути більше 0");

        this.student = student;
        this.course = course;
        this.semester = semester;
        this.grade = Grade.NA; // Початкова оцінка NA
        this.paid = false;     // Початково не оплачено
    }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }

    @Override
    public void setPaid(boolean paid) { this.paid = paid; }

    @Override
    public boolean isPaid() { return paid; }

    @Override
    public String toString() {
        String paymentStatus = paid ? "Оплачено" : "Не оплачено";
        return String.format("Зарахування: %s на курс '%s' | Семестр: %d | Оцінка: %s | Статус оплати: %s",
                student.getName(), course.getName(), semester, grade.name(), paymentStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return student.equals(that.student) && course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(student, course);
    }
}
