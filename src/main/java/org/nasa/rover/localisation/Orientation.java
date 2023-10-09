package org.nasa.rover.localisation;


public enum Orientation {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W");

    private final String directionValue;
    private Orientation rightOrientation;
    private Orientation leftOrientation;
    private Orientation oppositeOrientation;

    Orientation(String actualDirection) {
        this.directionValue = actualDirection;
    }


    public Orientation toTheRight() {
        setRelativeDirections();
        return rightOrientation;
    }

    public Orientation toTheLeft() {
        setRelativeDirections();
        return leftOrientation;
    }

    public Orientation toTheOpposite(){
        setRelativeDirections();
        return oppositeOrientation;
    }


    public String value(){
        return directionValue;
    }

    public boolean isEqualTo(Orientation orientation){
        return this.equals(orientation);
    }


    private void setRelativeDirections() {
        switch (this.directionValue) {
            case "N" -> {
                rightOrientation = Orientation.EAST;
                leftOrientation = Orientation.WEST;
                oppositeOrientation = Orientation.SOUTH;
            }
            case "S" -> {
                rightOrientation = Orientation.WEST;
                leftOrientation = Orientation.EAST;
                oppositeOrientation = Orientation.NORTH;

            }
            case "W" -> {
                rightOrientation = Orientation.NORTH;
                leftOrientation = Orientation.SOUTH;
                oppositeOrientation = Orientation.EAST;

            }
            case "E" -> {
                rightOrientation = Orientation.SOUTH;
                leftOrientation = Orientation.NORTH;
                oppositeOrientation = Orientation.WEST;

            }
        }
    }
}

