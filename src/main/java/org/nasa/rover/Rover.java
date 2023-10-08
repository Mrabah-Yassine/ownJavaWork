package org.nasa.rover;

import org.nasa.rover.command.itf.ICommand;

import java.util.List;

public class Rover {

    private String actualNewPosition;
    private Orientation orientation;
    private String command;
    private static int numberOfStepsSentByCommand;


    private int numberOfCommandsReceived;

    private static final int LONGITUDE_DEAD_VALUE = 0;
    public int howManyTimesDidItCrossPole = 0;

    private final Coordinates coordinates;


    private final Grid grid;

    private List<ICommand> commandsList;


    public Rover(Coordinates coordinates, Orientation orientation,
                 Grid grid, List<ICommand> commandsList) {
        this.coordinates = coordinates;
        this.orientation = orientation;
        this.grid = grid;
        this.commandsList = commandsList;
    }


    public void navigate(){
        for (ICommand command : commandsList){
            command.execute();
        }
        actualNewPosition = getUpdatedPosition();
    }

    public void executeCommand(String command) {
        this.command = command;
        numberOfStepsSentByCommand = getNumberOfCommandsSent();
        actualNewPosition = getUpdatedPosition();
    }

    public String getNewActualPosition() {
        return actualNewPosition;
    }


    private int getNumberOfCommandsReceived(){
        return this.commandsList.size();
    }


    private int getNumberOfCommandsSent() {
        return this.command.length();
    }

    private String getUpdatedPosition() {
        if (isThereAnyCommandSent()) {
            updatePositionAfterFullCommandExecution();
        }
        return wrapPosition();
    }


    private boolean isThereAnyCommandSent() {
        return numberOfStepsSentByCommand > 0;
    }


    private String wrapPosition() {
        return coordinates.value().append(orientation.value()).toString();
    }


    private void updatePositionAfterFullCommandExecution() {
        for (Character commandStep : this.command.toCharArray()) {
            updatePositionAfterExecutionOf(commandStep);
        }
    }


    private void updatePositionAfterExecutionOf(Character stepCommand) {

        Pole thisPole = Pole.NORTH;
        thisPole = thisPole.selectPoleFromDirection(this.orientation);

        if (isItRotationCommand(stepCommand)) {
            rotateToThe(stepCommand);
            return;
        }
        if (isRoverInVerticalMode()) {
            moveVerticallyWithCommandTowards(stepCommand, thisPole);
            return;
        }

        if (isRoverMovingToTheWest(stepCommand)) {
            coordinates.decrementLongitude();
            if (didTheRoverCrossLongitudeLimitLine()) {
                coordinates.updateLongitudeOverHorizontalLimitsOf(grid);
            }
            return;
        }
        coordinates.incrementLongitudeInside(grid);
    }

    private boolean isItRotationCommand(Character stepCommand) {
        return stepCommand == 'r' || stepCommand == 'l';
    }


    private boolean isRoverInVerticalMode() {
        return this.orientation == Orientation.NORTH || this.orientation == Orientation.SOUTH;
    }

    private boolean isRoverMovingToTheWest(Character stepMoveCommand) {
        return (this.orientation.isEqualTo(Orientation.WEST) && stepMoveCommand == 'f') ||
                (this.orientation.isEqualTo(Orientation.EAST) && stepMoveCommand == 'b');
    }


    private void moveVerticallyWithCommandTowards(Character stepCommand, Pole pole) {
        if (stepCommand == 'b') {
            pole = pole.toTheOpposite();
        }
        moveOneStepCloserTo(pole);
    }


    private void rotateToThe(Character stepCommand) {
        if (stepCommand == 'l') {
            this.orientation = this.orientation.toTheLeft();
            return;
        }
        if (stepCommand == 'r') {
            this.orientation = this.orientation.toTheRight();
        }
    }


    private void moveOneStepCloserTo(Pole pole) {
        if (pole == Pole.NORTH) {
            coordinates.decrementAltitude();
        } else {
            coordinates.incrementAltitude();
        }
        if (didTheRoverCrossPole()) {
            pole = pole.toTheOpposite();
            moveOneStepCloserTo(pole);
            howManyTimesDidItCrossPole++;
            updateLongitudeAndDirection();
        }
    }


    private void updateLongitudeAndDirection() {
        coordinates.updateLongitudeOverVerticalLimitsOf(grid);
        orientation = orientation.toTheOpposite();
    }


    private boolean didTheRoverCrossPole() {
        return coordinates.doAltitudeViolateVerticalLimitsOf(grid);
    }

    private boolean didTheRoverCrossLongitudeLimitLine() {
        return coordinates.doLongitudeViolateHorizontalLimitsOf(grid);
    }

}
