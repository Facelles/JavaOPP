package enums;

public enum Grade {
    A(4.0), B(3.5), C(3.0), D(2.0), F(0.0), NA(0.0);

    private final double points;

    Grade(double points) {
        this.points = points;
    }

    public double getPoints() {
        return points;
    }}
