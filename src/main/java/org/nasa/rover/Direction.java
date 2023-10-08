package org.nasa.rover;


public enum Direction {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W");

    private final String directionValue;
    private Direction rightDirection;
    private Direction leftDirection;
    private Direction oppositeDirection;

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

    public Direction toTheOpposite(){
        setRelativeDirections();
        return oppositeDirection;
    }


    public String value(){
        return directionValue;
    }

    public boolean isEqualTo(Direction direction){
        return this.equals(direction);
    }


    private void setRelativeDirections() {
        switch (this.directionValue) {
            case "N" -> {
                rightDirection = Direction.EAST;
                leftDirection = Direction.WEST;
                oppositeDirection = Direction.SOUTH;
            }
            case "S" -> {
                rightDirection = Direction.WEST;
                leftDirection = Direction.EAST;
                oppositeDirection = Direction.NORTH;

            }
            case "W" -> {
                rightDirection = Direction.NORTH;
                leftDirection = Direction.SOUTH;
                oppositeDirection = Direction.EAST;

            }
            case "E" -> {
                rightDirection = Direction.SOUTH;
                leftDirection = Direction.NORTH;
                oppositeDirection = Direction.WEST;

            }
        }
    }
}

