package org.nasa.rover;

public class Rover {

    private String actualNewPosition;
    private Direction direction;
    private String command;
    private static int numberOfStepsSentByCommand;

    private static final int LONGITUDE_DEAD_VALUE = 0;
    public int howManyTimesDidItCrossPole = 0;

    private final Coordinates coordinates;

    private final IPlanet iPlanet;

    private final Grid grid;


    public Rover(IPlanet iPlanet, Coordinates coordinates, Direction direction,
                 Grid grid) {
        this.iPlanet = iPlanet;
        this.coordinates = coordinates;
        this.direction = direction;
        this.grid = grid;
    }

    public void executeCommand(String command) {
        this.command = command;
        numberOfStepsSentByCommand = getNumberOfCommandsSent();
        actualNewPosition = getUpdatedPosition();
    }

    public String getNewActualPosition() {
        return actualNewPosition;
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
        return coordinates.value().append(direction.value()).toString();
    }


    private void updatePositionAfterFullCommandExecution() {
        for (Character commandStep : this.command.toCharArray()) {
            updatePositionAfterExecutionOf(commandStep);
        }
    }


    private void updatePositionAfterExecutionOf(Character stepCommand) {

        Pole thisPole = Pole.NORTH;
        thisPole = thisPole.selectPoleFromDirection(this.direction);

        if (isItRotationCommand(stepCommand)) {
            rotateToThe(stepCommand);
            return;
        }
        if (isRoverInVerticalMode()) {
            moveVerticallyWithCommandTowards(stepCommand, thisPole);
            return;
        }

        if(isRoverInHorizontalMode()){

        }
        if (this.direction.isEqualTo(Direction.WEST)) {
            if (stepCommand == 'b') {
                coordinates.incrementLongitudeInside(grid);
                return;
            }
            if (stepCommand == 'f') {
                coordinates.decrementLongitude();
                if (didTheRoverCrossLongitudeLimitLine()) {
                    coordinates.updateLongitudeOverHorizontalLimitsOf(grid);
                }
            }
            return;
        }
        if (this.direction.isEqualTo(Direction.EAST)) {
            if (stepCommand == 'f') {
                coordinates.incrementLongitudeInside(grid);
                return;
            }
            if (stepCommand == 'b') {
                coordinates.decrementLongitude();
                if (didTheRoverCrossLongitudeLimitLine()) {
                    coordinates.updateLongitudeOverHorizontalLimitsOf(grid);
                }
            }
        }
    }

    private boolean isItRotationCommand(Character stepCommand) {
        return stepCommand == 'r' || stepCommand == 'l';
    }


    private boolean isRoverInVerticalMode() {
        return this.direction == Direction.NORTH || this.direction == Direction.SOUTH;
    }

    private boolean isRoverInHorizontalMode(){
        return this.direction == Direction.WEST || this.direction == Direction.EAST;
    }


    private void moveVerticallyWithCommandTowards(Character stepCommand, Pole pole) {
        if (stepCommand == 'b') {
            pole = pole.toTheOpposite();
        }
        moveOneStepCloserTo(pole);
    }


    private void rotateToThe(Character stepCommand) {
        if (stepCommand == 'l') {
            this.direction = this.direction.toTheLeft();
            return;
        }
        if (stepCommand == 'r') {
            this.direction = this.direction.toTheRight();
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
        direction = direction.toTheOpposite();
    }


    private boolean didTheRoverCrossPole() {
        return coordinates.doAltitudeViolateVerticalLimitsOf(grid);
    }

    private boolean didTheRoverCrossLongitudeLimitLine() {
        return coordinates.doLongitudeViolateHorizontalLimitsOf(grid);
    }

}
