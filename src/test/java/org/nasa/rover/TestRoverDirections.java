package org.nasa.rover;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class TestRoverDirections {

    private Rover rover;

    private final int gridSize = 8;


    @ParameterizedTest(name = "{index} => inputPosition={0}, command={1}, expectedNewPosition={2}")
    @CsvSource({
            "'1,8,N', '', '1,8,N'",
            "'1,8,N', 'f', '1,7,N'",
            "'1,8,N', 'ff', '1,6,N'",
            "'1,8,N', 'fff', '1,5,N'",
            "'1,8,N', 'ffffff', '1,2,N'",
            "'1,8,N', 'fffffff', '1,1,N'",
            "'1,8,N', 'ffffffff', '5,1,S'",
            "'1,8,N', 'fffffffff', '5,2,S'",
            "'1,8,N', 'ffffffffff', '5,3,S'",
            "'1,5,N', 'ffff', '1,1,N'",
            "'1,1,N', 'f', '5,1,S'",
            "'1,1,N', 'ff', '5,2,S'",
            "'1,1,N', 'ffff', '5,4,S'",
            "'1,2,N', 'ffff', '5,3,S'",
            "'1,2,N', 'fffffffff', '5,8,S'",
            "'1,7,N', 'ffffff', '1,1,N'",
            "'1,7,N', 'ffffffffffff', '5,6,S'",
            "'1,6,N', 'fffffffffffff', '5,8,S'",
            "'1,4,N', 'fff', '1,1,N'",
            "'1,4,N', 'ffff', '5,1,S'",
            "'1,4,N', 'fffffff', '5,4,S'",
            "'1,4,N', 'ffffffffffffffff', '1,4,N'",
            "'1,4,N', 'fffffffffffffffff', '1,3,N'",
            "'1,4,N', 'fffffffffffffffffffffff', '5,4,S'",
            "'1,4,N', 'ffffffffffffffffffffffff', '5,5,S'",
            "'1,8,N', 'fffffffffffffffffffffff', '1,1,N'",

    })
    void givenInitialDirectionIsNorthThenMoveForward(String inputPosition,
                     String command,
                     String expectedNewPosition){


        String[] positionArray = inputPosition.split(",");

        rover = new Rover(positionArray);

        rover.setGridSize(gridSize);
        rover.executeCommand(command);
        //assertions,
        Assertions.assertEquals(expectedNewPosition, rover.getNewActualPosition());

    }



    @Test
    void whenZeroZeroCoordinatesThenThrowUnsupportedOperationException(){

        rover = new Rover(new String[]{"0", "0", "N"});
        UnsupportedOperationException thrown = Assertions.assertThrows(UnsupportedOperationException.class, () ->
                rover.executeCommand("f"));

        Assertions.assertEquals("Unable to move the rover from 0,0 coordinates " +
                "because they are not defined", thrown.getMessage());
    }

}
