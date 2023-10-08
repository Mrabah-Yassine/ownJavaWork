package org.nasa.rover;

public enum Pole {
    SOUTH,
    NORTH;

    public Pole toTheOpposite() {
        if (this == Pole.NORTH) return Pole.SOUTH;
        return Pole.NORTH;
    }

    public Pole selectPoleFromDirection(Direction direction) {
        for (Pole pole : Pole.values()) {
            if (pole.name().equals(direction.name())) {
                return pole;
            }
        }
        //default return
        return this;
    }
}
