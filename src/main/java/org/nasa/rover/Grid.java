package org.nasa.rover;

public class Grid {

    public int size;

    public int minLimit;

    public int maxLimit;


    public Grid(int size) {
        this.size = size;
        maxLimit = size;
        minLimit = 0;
    }
}
