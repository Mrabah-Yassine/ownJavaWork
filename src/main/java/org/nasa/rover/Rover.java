package org.nasa.rover;

public class Rover {

    private String actualNewPosition;
    private String direction;

    private int longitude;
    private int altitude;

    private int gridSize;

    private String command;

    public Rover(String[] position) {
        this.longitude = Integer.parseInt(position[0]);
        this.altitude = Integer.parseInt(position[1]);
        this.direction = position[2];
    }

    public void executeCommand(String command) {
        if (areCoordinatesNotValid()) {
            throw new UnsupportedOperationException("Unable to move the rover from 0,0 coordinates " +
                    "because they are not defined");
        }
        this.command = command;
        actualNewPosition = calculateAndGetNewPosition();
    }

    public String getNewActualPosition() {
        return actualNewPosition;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }


    private String calculateAndGetNewPosition() {
        int numberOfStepsSentByCommand = this.command.length();
        if (numberOfStepsSentByCommand > 0) {
            setNewAltitudeAfterCommandExecution(numberOfStepsSentByCommand);
            setNewRealPositionIfNorthPoleIsCrossed();
        }
        return wrapPosition();
    }


    private String wrapPosition() {
        return String.format("%s,%s,%s", longitude, altitude, direction);
    }


    private void setNewRealPositionIfNorthPoleIsCrossed() {
        if (isNorthPoleCrossed()) {
            setNewRealPositionAfterCrossingPole();
        }
    }


    private void setNewAltitudeAfterCommandExecution(int numberOfStepsSentByCommand) {
        for (int i = 0; i < numberOfStepsSentByCommand % (gridSize * 2); i++) {
            altitude--;
        }
    }


    private boolean isNorthPoleCrossed() {
        return altitude <= 0;
    }


    private void setNewRealPositionAfterCrossingPole() {
        longitude = getNewCalculatedLongitude();
        direction = getNewDirection();
        altitude = Math.abs(altitude - 1);
    }

    private String getNewDirection() {
        if (direction.equals("N")) {
            return "S";
        }
        return "N";
    }


    private int getNewCalculatedLongitude() {
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
