package org.nasa.rover.localisation.impl;

import org.nasa.rover.localisation.itf.IGrid;

public class CartesianGrid implements IGrid {

    private final int height;

    private final int width;

    private final int origin;


    public CartesianGrid(int height, int width, int origin) {
        this.height = height;
        this.width = width;
        this.origin = origin;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int origin() {
        return origin;
    }
}
