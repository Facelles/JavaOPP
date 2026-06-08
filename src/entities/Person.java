package entities;

public abstract class Person {
    private int id;
    private String name;
    private String email;

    public Person(int id, String name, String email) {
        this.id = id;
        setName(name);
        setEmail(email);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ім'я не може бути порожнім");
        }
        this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Некоректний формат email");
        }
        this.email = email;
    }
}