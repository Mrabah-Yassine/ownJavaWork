package org.nasa.rover.localisation;

public enum Pole {
    SOUTH,
    NORTH;

    public Pole toTheOpposite() {
        if (this == Pole.NORTH) return Pole.SOUTH;
        return Pole.NORTH;
    }

    public Pole selectPoleFromOrientation(Orientation orientation) {
        for (Pole pole : Pole.values()) {
            if (pole.name().equals(orientation.name())) {
                return pole;
            }
        }
        //default return
        return this;
    }
}
