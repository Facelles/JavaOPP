package entities;

public class Course {
    private int id;
    private String name;
    private int credits;
    private Teacher teacher;

    public Course(int id, String name, int credits, Teacher teacher) {
        this.id = id;
        setName(name);
        setCredits(credits);
        setTeacher(teacher);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва курсу не може бути порожньою");
        }
        this.name = name;
    }

    public int getCredits() { return credits; }
    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Кількість кредитів має бути більшою за 0");
        }
        this.credits = credits;
    }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Курс повинен мати викладача");
        }
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return String.format("Курс [ID: %d] %s | Кредити: %d | Викладач: %s",
                id, name, credits, teacher.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
