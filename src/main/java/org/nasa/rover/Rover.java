package org.nasa.rover;

public class Rover {

    private String actualNewPosition;
    private Direction direction;
    private int longitude;
    private int altitude;
    private int gridSize;
    private String command;
    private static int numberOfStepsSentByCommand;
    private static final int NORTH_POLE_DEAD_POINT = 0;
    private static int SOUTH_POLE_DEAD_POINT;
    private static final int LONGITUDE_DEAD_VALUE = 0;
    private int numberOfTimesPoleIsCrossed = 0;



    public Rover(String[] coordinates, Direction direction) {
        this.longitude = Integer.parseInt(coordinates[0]);
        this.altitude = Integer.parseInt(coordinates[1]);
        this.direction = direction;
    }

    public void executeCommand(String command) {
        if (areCoordinatesNotValid()) {
            throw new UnsupportedOperationException("Unable to move the rover from 0,0 coordinates " +
                    "because they are not defined");
        }
        this.command = command;
        numberOfStepsSentByCommand = getNumberOfCommandsSent();
        actualNewPosition = calculateAndGetNewPosition();
    }

    public String getNewActualPosition() {
        return actualNewPosition;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
        SOUTH_POLE_DEAD_POINT = gridSize;
    }

    private int getNumberOfCommandsSent() {
        return this.command.length();
    }

    private String calculateAndGetNewPosition() {
        if (isThereAnyCommandSent()) {
            setNewPositionAfterCommandExecution();
        }
        return wrapPosition();
    }


    private boolean isThereAnyCommandSent() {
        return numberOfStepsSentByCommand > 0;
    }


    private String wrapPosition() {
        return String.format("%s,%s,%s", longitude, altitude, direction.value());
    }


    private void setNewPositionAfterCommandExecution() {
        for (Character commandStep : this.command.toCharArray()) {
            setNewPositionAfterStepCommand(commandStep);
        }
    }


    private void setNewPositionAfterStepCommand(Character stepCommand) {
        if(stepCommand == 'l'){
            this.direction = this.direction.toTheLeft();
            return;
        }
        if (stepCommand == 'r') {
            this.direction = this.direction.toTheRight();
            return;
        }
        if (isDirection(Direction.NORTH)) {
            if (stepCommand == 'f') {
                goUp();
                return;
            }
            if (stepCommand == 'b') {
                goDown();
                return;
            }
            return;
        }
        if (isDirection(Direction.SOUTH)) {
            if (stepCommand == 'f') {
                goDown();
                return;
            }
            if (stepCommand == 'b') {
                goUp();
                return;
            }
            return;
        }
        if (isDirection(Direction.WEST)) {
            if (stepCommand == 'b') {
                longitude++;
                longitude %= gridSize;
                return;
            }
            if (stepCommand == 'f') {
                longitude--;
                if(isLongitudeDeadValueCrossed()){
                    longitude = getNewLongitudeAfterCrossingDeadValue();
                }
            }
            return;
        }
        if (isDirection(Direction.EAST)) {
            if (stepCommand == 'f') {
                longitude++;
                longitude %= gridSize;
                return;
            }
            if (stepCommand == 'b') {
                longitude--;
                if(isLongitudeDeadValueCrossed()){
                    longitude = getNewLongitudeAfterCrossingDeadValue();
                }
            }
        }
    }


    private boolean isLongitudeDeadValueCrossed(){
        return longitude >= LONGITUDE_DEAD_VALUE;
    }

    private int getNewLongitudeAfterCrossingDeadValue(){
        return gridSize - (longitude % gridSize);
    }

    /**
     * Moving the rover towards the South Pole of the planet
     */
    private void goDown() {
        altitude++;
        if (isPoleCrossed()) {
            altitude--;
            numberOfTimesPoleIsCrossed++;
            changeLongitudeAndDirection();
        }
    }

    /**
     * Moving the rover towards the North Pole of the planet
     */
    private void goUp() {
        altitude--;
        if (isPoleCrossed()) {
            altitude++;
            numberOfTimesPoleIsCrossed++;
            changeLongitudeAndDirection();
        }
    }

    private boolean isPoleCrossed(){
        return altitude > SOUTH_POLE_DEAD_POINT || altitude <= NORTH_POLE_DEAD_POINT;
    }


    private void changeLongitudeAndDirection() {
        longitude = getNewLongitudeAfterCrossingPole();
        direction = getNewDirectionAfterCrossingPole();
    }


    private Direction getNewDirectionAfterCrossingPole() {
        if (isDirection(Direction.NORTH)) {
            return Direction.SOUTH;
        }
        return Direction.NORTH;
    }

    private boolean isDirection(Direction direction) {
        return this.direction.equals(direction);
    }


    private int getNewLongitudeAfterCrossingPole() {
        if (longitude >= 1 && longitude <= gridSize / 2) {
            return longitude + gridSize / 2;
        }
        if (longitude > gridSize / 2 && longitude <= gridSize) {
            return longitude - gridSize / 2;
        }
        return longitude;
    }

    private boolean areCoordinatesNotValid() {
        return this.longitude == 0 || this.altitude == 0;
    }
}
