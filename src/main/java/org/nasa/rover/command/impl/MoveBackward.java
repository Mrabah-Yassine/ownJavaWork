package org.nasa.rover.command.impl;

import org.nasa.rover.Coordinates;
import org.nasa.rover.Grid;
import org.nasa.rover.Orientation;
import org.nasa.rover.Pole;
import org.nasa.rover.command.itf.ICommand;

public class MoveBackward extends Move implements ICommand {

    private Orientation orientation;

    private Pole pole;

    private Coordinates coordinates;

    private Grid grid;

    public MoveBackward(Orientation orientation, Pole pole, Coordinates coordinates,
                        Grid grid ) {
        super(orientation, pole, coordinates, grid);
    }

    @Override
    public void execute() {
        if(this.isRoverInVerticalMode()){
            pole = pole.selectPoleFromDirection(this.orientation).toTheOpposite();
            moveOneStepCloserTo(pole);
            return;
        }
        if(this.orientation == Orientation.WEST){
            coordinates.incrementLongitudeInside(grid);
            return;

        }
        if(this.orientation == Orientation.EAST){
            coordinates.decrementLongitude();
            if (didTheRoverCrossLongitudeLimitLine()) {
                coordinates.updateLongitudeOverHorizontalLimitsOf(grid);
            }
        }
    }
}
