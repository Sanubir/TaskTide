public class Cylinder {
    private double radius;
    private double height;

    Cylinder(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    Cylinder() {
        // empty body
    }

    public void setRadius(double newRadius) {
        radius = newRadius;
    }

    public void setHeight(double newHeight) {
        height = newHeight;
    }

    public double getRadius() {
        return radius;
    }

    public double getHeight() {
        return height;
    }

    public double getAreaOfBase() {
        return Math.PI * Math.pow(radius, 2);
    }

    public double getAreaOfSides() {
        return 2 * Math.PI * radius * height;
    }

    public double getArea() {
        return 2 * getAreaOfBase() + getAreaOfSides();
    }

    public double getVolume() {
        return getAreaOfBase() * height;
    }
}
