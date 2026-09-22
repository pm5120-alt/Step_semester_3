package inheritance_polymorphism.assignment_problems;

abstract class Shape {
    private static int nextId = 1000;
    private final String shapeId = "SH-" + (++nextId);

    public abstract double calculateArea();
    protected abstract void applyScale(double factor);

    public void scale(double factor) { applyScale(factor); }
    public void scale(double xFactor, double yFactor) {
        scale(xFactor);
        scale(yFactor);
    }
    public String getShapeId() { return shapeId; }
}

class CircleShape extends Shape {
    private double radius;
    public CircleShape(double radius) { this.radius = radius; }
    public double calculateArea() { return Math.PI * radius * radius; }
    protected void applyScale(double factor) { radius *= factor; }
}

class SquareShape extends Shape {
    private double side;
    public SquareShape(double side) { this.side = side; }
    public double calculateArea() { return side * side; }
    protected void applyScale(double factor) { side *= factor; }
}

public class Problem1BasicDrawingCanvas {
    static void printArea(Shape s) { System.out.println(s.calculateArea()); }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);
        printArea(c);
        sq.scale(2.0);
        printArea(sq);
        System.out.println(c.getShapeId());
    }
}