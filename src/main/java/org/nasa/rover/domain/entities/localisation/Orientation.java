package org.nasa.rover.domain.entities.localisation;


public enum Orientation {
    NORTH,
    SOUTH,
    EAST,
    WEST;


    public Orientation toTheRight() {
        if(this == Orientation.NORTH) return Orientation.EAST;
        if(this == Orientation.SOUTH) return Orientation.WEST;
        if(this == Orientation.WEST) return Orientation.NORTH;
        if(this == Orientation.EAST) return Orientation.SOUTH;
        throw new IllegalArgumentException("This direction is not known...");
    }

    public Orientation toTheLeft() {
        if(this == Orientation.NORTH) return Orientation.WEST;
        if(this == Orientation.SOUTH) return Orientation.EAST;
        if(this == Orientation.WEST) return Orientation.SOUTH;
        if(this == Orientation.EAST) return Orientation.NORTH;
        throw new IllegalArgumentException("This direction is not known...");
    }

    public Orientation toTheOpposite(){
        if(this == Orientation.NORTH) return Orientation.SOUTH;
        if(this == Orientation.SOUTH) return Orientation.NORTH;
        if(this == Orientation.WEST) return Orientation.EAST;
        if(this == Orientation.EAST) return Orientation.WEST;
        throw new IllegalArgumentException("This direction is not known...");
    }

    @Override
    public String toString(){
        return this.name().substring(0,1);
    }

    public boolean isEqualTo(Orientation orientation){
        return this.equals(orientation);
    }

}

