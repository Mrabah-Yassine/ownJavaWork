package org.nasa.rover;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.nasa.rover.controller.RoverController;
import org.nasa.rover.controller.CommandRequester;
import org.nasa.rover.domain.application.localisation.OrientationFactory;
import org.nasa.rover.domain.entities.localisation.Orientation;
import org.nasa.rover.domain.entities.localisation.Position;
import org.nasa.rover.domain.entities.grid.CartesianGrid;
import org.nasa.rover.domain.application.localisation.ManageLatitude;
import org.nasa.rover.domain.application.localisation.ManageLongitude;
import org.nasa.rover.domain.entities.localisation.ICoordinate;
import org.nasa.rover.domain.entities.grid.IGrid;
import org.nasa.rover.domain.application.IPlanetRover;
import org.nasa.rover.domain.application.MarsRover;


public class TestRoverDirections {

    private IPlanetRover rover;

    private final IGrid iGrid = new CartesianGrid(8, 8, 0);


    private ICoordinate iLatitude;

    private ICoordinate iLongitude;

    private Orientation orientation;

    private Position position;


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
            "'4,8,N', 'fff', '4,5,N'",
            "'4,3,N', 'ffff', '8,2,S'",
            "'5,3,N', 'ffff', '1,2,S'",
            "'8,8,N', 'ffffffff', '4,1,S'",
            "'4,4,N', 'ffffffff', '8,5,S'",
            "'4,4,N', 'fffffffffffff', '4,7,N'",
            "'4,4,N', 'fffffffffffffff', '4,5,N'",
            "'4,8,N', 'fffffffffffffff', '8,8,S'",
            "'4,8,N', 'fffffffffffffffffffffffffffff', '8,6,S'",
            "'4,4,N', 'fffffffffffffffffffffff', '8,4,S'",
            "'1,1,N', 'fffffffffffffffffffffff', '5,7,S'",
            "'1,8,S', '', '1,8,S'",
            "'1,8,S', 'f', '5,8,N'",
            "'1,8,S', 'ff', '5,7,N'",
            "'1,8,S', 'fff', '5,6,N'",
            "'1,1,S', 'fff', '1,4,S'",
            "'5,5,S', 'ffffffff', '1,4,N'",
            "'8,8,S', 'ffffffff', '4,1,N'",
            "'7,6,S', 'ffffffff', '3,3,N'",
            "'7,6,S', 'ffffffffffffffff', '7,6,S'",
            "'7,6,S', 'fffffffffffffff', '7,5,S'",
            "'7,3,S', 'fffffffffffff', '3,1,N'",
            "'7,3,S', 'fffffffffffffff', '7,2,S'",
            "'7,7,S', 'fffffffffffffffff', '7,8,S'",
            "'4,8,S', 'fffffffffffffffffffffffffffff', '4,5,S'",
            "'1,8,S', '', '1,8,S'",
            "'1,8,S', 'b', '1,7,S'",
            "'1,8,S', 'bb', '1,6,S'",
            "'1,8,S', 'bbb', '1,5,S'",
            "'1,1,S', 'bbb', '5,3,N'",
            "'5,5,S', 'bbbbbbbb', '1,4,N'",
            "'8,8,S', 'bbbbbbbb', '4,1,N'",
            "'7,6,S', 'bbbbbbbb', '3,3,N'",
            "'7,6,S', 'bbbbbbbbbbbbbbbb', '7,6,S'",
            "'7,6,S', 'bbbbbbbbbbbbbbb', '7,7,S'",
            "'7,3,S', 'bbbbbbbbbbbbb', '7,6,S'",
            "'7,3,S', 'bbbbbbbbbbbbbbb', '7,4,S'",
            "'7,7,S', 'bbbbbbbbbbbbbbbbb', '7,6,S'",
            "'4,8,S', 'bbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '8,6,N'",
            "'1,8,N', 'b', '5,8,S'",
            "'1,8,N', 'bb', '5,7,S'",
            "'1,8,N', 'bbb', '5,6,S'",
            "'1,1,N', 'bbb', '1,4,N'",
            "'5,5,N', 'bbbbbbbb', '1,4,S'",
            "'8,8,N', 'bbbbbbbb', '4,1,S'",
            "'7,6,N', 'bbbbbbbb', '3,3,S'",
            "'7,6,N', 'bbbbbbbbbbbbbbbb', '7,6,N'",
            "'7,6,N', 'bbbbbbbbbbbbbbb', '7,5,N'",
            "'7,3,N', 'bbbbbbbbbbbbb', '3,1,S'",
            "'7,3,N', 'bbbbbbbbbbbbbbb', '7,2,N'",
            "'7,7,N', 'bbbbbbbbbbbbbbbbb', '7,8,N'",
            "'4,8,N', 'bbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '4,5,N'",
            "'1,8,S', 'r', '1,8,W'",
            "'1,8,S', 'l', '1,8,E'",
            "'1,8,S', 'rr', '1,8,N'",
            "'1,8,S', 'rrr', '1,8,E'",
            "'1,8,S', 'rrrr', '1,8,S'",
            "'1,8,S', 'lll', '1,8,W'",
            "'8,8,N', 'lll', '8,8,E'",
            "'1,8,S', 'frllbbfrb', '2,8,S'",
            "'8,8,W', 'bbb', '3,8,W'",
            "'1,8,W', 'ff', '7,8,W'",
            "'1,8,E', 'bbb', '6,8,E'",
            "'8,8,E', 'ffff', '4,8,E'",
            "'8,8,S', 'flffllbbrfrf', '5,8,E'",


    })
    void givenInitialDirectionIsNorthThenMoveForwardOnly(String inputPosition,
                                                     String command,
                                                     String expectedNewPosition){


        //given
        String[] inputArray = inputPosition.split(",");

        int inputLongitude = Integer.parseInt(inputArray[0]);
        int inputLatitude = Integer.parseInt(inputArray[1]);
        String inputOrientation = inputArray[2];

        iLatitude = new ManageLatitude(inputLatitude, iGrid);

        iLongitude = new ManageLongitude(inputLongitude, iGrid);

        orientation = OrientationFactory.getOrientationFrom(inputOrientation);

        position = new Position(iLongitude, iLatitude, orientation);

        rover = new MarsRover(position);

        //when
        CommandRequester roverCommandsFactory = new CommandRequester(command, rover);


        RoverController marsRoverController = new RoverController(roverCommandsFactory);

        marsRoverController.execute();

        //assertions,
        Assertions.assertEquals(expectedNewPosition, position.value());

    }



    @Test
    void whenZeroZeroCoordinatesThenThrowUnsupportedOperationException(){
        iLatitude = new ManageLatitude(0, iGrid);

        iLongitude = new ManageLongitude(0, iGrid);

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Position(iLongitude, iLatitude, Orientation.NORTH));

        Assertions.assertEquals("0,0 coordinates " +
                "are not valid", thrown.getMessage());
    }
}
