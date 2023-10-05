package org.nasa.rover;

public class Rover {

    private String actualNewPosition;
    private Direction direction;
    private String command;
    private static int numberOfStepsSentByCommand;

    private static final int LONGITUDE_DEAD_VALUE = 0;
    private int numberOfTimesPoleIsCrossed = 0;

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
        actualNewPosition = calculateAndGetNewPosition();
    }

    public String getNewActualPosition() {
        return actualNewPosition;
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
        return String.format("%s,%s,%s", coordinates.LONGITUDE, coordinates.ALTITUDE, direction.value());
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
        if (this.direction.isEqualTo(Direction.NORTH)) {
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
        if (this.direction.isEqualTo(Direction.SOUTH)) {
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
        if (this.direction.isEqualTo(Direction.WEST)) {
            if (stepCommand == 'b') {
                coordinates.LONGITUDE++;
                coordinates.LONGITUDE %= grid.size;
                return;
            }
            if (stepCommand == 'f') {
                coordinates.LONGITUDE--;
                if(isLongitudeDeadValueCrossed()){
                    coordinates.LONGITUDE = getNewLongitudeAfterCrossingDeadValue();
                }
            }
            return;
        }
        if (this.direction.isEqualTo(Direction.EAST)) {
            if (stepCommand == 'f') {
                coordinates.LONGITUDE++;
                coordinates.LONGITUDE %= grid.size;
                return;
            }
            if (stepCommand == 'b') {
                coordinates.LONGITUDE--;
                if(isLongitudeDeadValueCrossed()){
                    coordinates.LONGITUDE = getNewLongitudeAfterCrossingDeadValue();
                }
            }
        }
    }


    private boolean isLongitudeDeadValueCrossed(){
        return coordinates.LONGITUDE <= grid.minLimit;
    }

    private int getNewLongitudeAfterCrossingDeadValue(){
        return grid.size - (coordinates.LONGITUDE % grid.size);
    }

    /**
     * Moving the rover towards the South Pole of the planet
     */
    private void goDown() {
        coordinates.ALTITUDE++;
        if (didItCrossPole(coordinates)) {
            coordinates.ALTITUDE--;
            numberOfTimesPoleIsCrossed++;
            changeLongitudeAndDirection();
        }
    }

    /**
     * Moving the rover towards the North Pole of the planet
     */
    private void goUp() {
        coordinates.ALTITUDE--;
        if (didItCrossPole(coordinates)) {
            coordinates.ALTITUDE++;
            numberOfTimesPoleIsCrossed++;
            changeLongitudeAndDirection();
        }
    }


    private boolean didItCrossPole(Coordinates coordinates) {
        return coordinates.ALTITUDE > grid.maxLimit || coordinates.ALTITUDE <= grid.minLimit;
    }



    private void changeLongitudeAndDirection() {
        coordinates.LONGITUDE = getNewLongitudeAfterCrossingPole();
        direction = getNewDirectionAfterCrossingPole();
    }


    private Direction getNewDirectionAfterCrossingPole() {
        if (this.direction.isEqualTo(Direction.NORTH)) {
            return Direction.SOUTH;
        }
        return Direction.NORTH;
    }


    private int getNewLongitudeAfterCrossingPole() {
        if (coordinates.LONGITUDE >= 1 && coordinates.LONGITUDE <= grid.size / 2) {
            return coordinates.LONGITUDE + grid.size / 2;
        }
        if (coordinates.LONGITUDE > grid.size / 2 && coordinates.LONGITUDE <= grid.size) {
            return coordinates.LONGITUDE - grid.size / 2;
        }
        return coordinates.LONGITUDE;
    }

}
