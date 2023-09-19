package org.nasa.rover;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class TestRoverDirections {

    Rover rover;

    private int gridSize = 8;
    @BeforeEach
    void setUp() {
        Rover rover;
    }

/*
    @ParameterizedTest(name = "{index} => position={0}, facedDirection={1}, command={2}, newPosition={3}")
    @CsvSource({
            "'0,0', 'N', 'f', '0,1,N'",
            "'0,0', 'N', 'ff', '0,2,N'",
            "'0,0', 'N', 'fff', '0,3,N'"
    })
    void givenZeroZeroCoordinatesAndNorthDirectionMoveForward(String position,
                                                              String facedDirection,
                                                              String command,
                                                              String newPosition){
        String[] positionArray = position.split(",");
        rover = new Rover(positionArray, facedDirection);
        //Position coordinates = new Position(0, 0);
        rover.executeCommand(command);
        //assertions,
        Assertions.assertEquals(newPosition, rover.getActualPosition());
    }*/


    @ParameterizedTest(name = "{index} => position={0}, facedDirection={1}, command={2}, newPosition={3}")
    @CsvSource({
            "'2,1', 'N', 'f', '6,1,S'",
            "'2,1', 'N', 'ff', '6,2,S'",
            "'2,1', 'N', 'fff', '6,3,S'",
            "'2,1', 'N', 'fffffff', '6,7,S'",
            "'2,1', 'N', 'fffffffff', '2,8,N'",
            "'2,1', 'N', 'fffffffffffffff', '2,2,N'",
            "'2,1', 'N', 'ffffffffffffffff', '2,1,N'"
    })
    void givenGridEqualEightAndRoverOnYEqualOneAndFacedToTheNorthWhenWeMoveForwardThenRoverIsFacedSouthAndXEquals(String position,
                                                                                                                  String facedDirection,
                                                                                                                  String command,
                                                                                                                  String newPosition){
        String[] positionArray = position.split(",");

        rover = new Rover(positionArray, facedDirection);
        rover.setGridSize(gridSize);
        rover.executeCommand(command);
        //assertions,
        Assertions.assertEquals(newPosition, rover.getActualPosition());

    }



    @Test
    void whenZeroZeroCoordinatesThenThrowUnsupportedOperationException(){

        rover = new Rover(new String[]{"0", "0"}, "N");
        UnsupportedOperationException thrown = Assertions.assertThrows(UnsupportedOperationException.class, () ->
                rover.executeCommand("f"));

        Assertions.assertEquals("Unable to move the rover from 0,0 coordinates " +
                "because they are not defined", thrown.getMessage());
    }

}
