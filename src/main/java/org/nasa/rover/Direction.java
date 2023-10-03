package org.nasa.rover;


public enum Direction {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W");

    private final String directionValue;
    private Direction actualDirection;
    private Direction rightDirection;
    private Direction leftDirection;

    Direction(String actualDirection) {
        this.directionValue = actualDirection;
    }


    public Direction toTheRight() {
        setRelativeDirections();
        return rightDirection;
    }

    public Direction toTheLeft() {
        setRelativeDirections();
        return leftDirection;
    }

    public Direction getActualDirection(){
        return actualDirection;
    }

    public String value(){
        return directionValue;
    }

    private void setRelativeDirections() {
        switch (this.directionValue) {
            case "N" -> {
                actualDirection = Direction.NORTH;
                rightDirection = Direction.EAST;
                leftDirection = Direction.WEST;
            }
            case "S" -> {
                actualDirection = Direction.SOUTH;
                rightDirection = Direction.WEST;
                leftDirection = Direction.EAST;
            }
            case "W" -> {
                actualDirection = Direction.WEST;
                rightDirection = Direction.NORTH;
                leftDirection = Direction.SOUTH;
            }
            case "E" -> {
                actualDirection = Direction.EAST;
                rightDirection = Direction.SOUTH;
                leftDirection = Direction.NORTH;
            }
            default -> throw new IllegalArgumentException("This type of direction is not supported");
        }
    }
}

