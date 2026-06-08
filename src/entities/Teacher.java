package entities;


import enums.TeacherPosition;

public class Teacher extends Person {
    private TeacherPosition position;

    public Teacher(int id, String name, String email,TeacherPosition position){
        super(id, name,email);
        setPosition(position);
    }


    public TeacherPosition getPosition() { return position; }
    public void setPosition(TeacherPosition position){
        this.position = position;
    }

    @Override
    public String toString(){
        return String.format("Викладач [ID: %d] %s | Email: %s | Позиція: %s",
                getId(), getName(), getEmail(), position);
    }
}
