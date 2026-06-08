package entities;

import enums.StudentStatus;

public class Student extends Person {
    private int year;
    private StudentStatus status;

    public Student(int id, String name, String email, int year, StudentStatus status) {
        super(id, name, email);
        setYear(year);
        this.status = status;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year){
        this.year = year;
    }

    public StudentStatus getStatus() { return status; }
    public void setStatus(StudentStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Студент [ID: %d] %s | Email: %s | Курс: %d | Статус: %s",
                getId(), getName(), getEmail(), year, status);
    }
}
