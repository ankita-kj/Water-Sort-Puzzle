package no.uib.inf101.sample.model;

/**
 * Cylinder class:
 * Defines a cylinder with a position to the top left corner,
 * and a width/height to represent the size of the cylinder
 */
public class Cylinder {
    /**
     * Top left corner of the cylinder
     */
    public Position position;
    /**
     * Width of the cylinder
     */
    public int width;
    /**
     * Height of the cylinder
     */
    public int height;

    /**
     * Creates a new cylinder
     * 
     * @param position Top left corner of the cylinder
     * @param width    Width of the cylinder
     * @param height   Height of the cylinder
     */
    public Cylinder(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    /**
     * @param x      X coordinate of the top left corner
     * @param y      Y coordinate of the top left corner
     * @param width  Width of the cylinder
     * @param height Height of the cylinder
     */
    public Cylinder(int x, int y, int width, int height) {
        this(new Position(x, y), width, height);
    }

    /**
     * Gets the height of the cylinder
     * 
     * @return Height of the cylinder
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the cylinder
     * 
     * @return Width of the cylinder
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the top left corner of the cylinder
     * 
     * @return Top left corner of the cylinder
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Gets the centre of the cylinder based on stored values
     * 
     * @return Centre coordinates of the cylinder
     */
    public Position getCentre() {
        return new Position(position.x + width / 2, position.y + height / 2);
    }

    /**
     * Checks if the targetPosition is inside the cylinder
     * 
     * @param targetPosition Position to test if it is inside the cylinder
     * @return True if the targetPosition is inside this cylinder
     */
    public boolean isPositionInside(Position targetPosition) {
        return targetPosition.x >= position.x && targetPosition.y >= position.y
                && targetPosition.x < position.x + width && targetPosition.y < position.y + height;
    }

    /**
     * Checks if Cylinder is intersecting with some otherCylinder
     * 
     * @param otherCylinder Other position to compare against for a collision
     * @return True if this Cylinder is intersecting the otherCylinder
     */
    public boolean isIntersecting(Cylinder otherCylinder) {
        if (position.y + height < otherCylinder.position.y)
            return false;
        if (position.y > otherCylinder.position.y + otherCylinder.height)
            return false;
        if (position.x + width < otherCylinder.position.x)
            return false;
        if (position.x > otherCylinder.position.x + otherCylinder.width)
            return false;
        return true;
    }
}
