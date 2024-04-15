package no.uib.inf101.sample.model;

public class Cylinder {

    public Position position;
    public int width;
    public int height;

    public Cylinder(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Cylinder(int x, int y, int width, int height) {
        this(new Position(x, y), width, height);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Position getPosition() {
        return position;
    }

    public Position getCentre() {
        return new Position(position.x + width / 2, position.y + height / 2);
    }

    public boolean isPositionInside(Position targetPosition) {
        return targetPosition.x >= position.x && targetPosition.y >= position.y
                && targetPosition.x < position.x + width && targetPosition.y < position.y + height;
    }
}
