package Axiom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.*;

public class AxiomTests {

    @Test public void test01NewDroneWithInitialSpeedZero() {
        assertEquals(0, new Drone().getSpeed());
    }

    @Test public void test02NewDroneWithInitialDirectionNorth() {
        assertEquals("N", new Drone().getDirection());
    }

    @Test public void test03DroneWithDirectionNorthGoesWestAfterCommandLeft() {
        assertEquals("W", new Drone().executeCommand("l").getDirection());
    }

    @Test public void test04DroneWithSpeedZeroGoesFasterAfterCommandIncreaseSpeed() {
        Drone drone = new Drone();
        drone.executeCommand("ii");

        assertEquals(2, drone.getSpeed());
    }

    @Test public void test05DroneCannotHaveNegativeSpeed() {
        assertEquals(0, new Drone().executeCommand("s").getSpeed());
    }

    @Test public void test06CannotDeploySondaWhileSteady() {
        assertThrowsLike(Drone.AxiomNotMoving,() -> new Drone().executeCommand("d"));
    }

    @Test public void test07DeployingSondaDoesNotAffectSpeed() {
        Drone drone = new Drone();
        drone.executeCommand("i");
        int initialSpeed = drone.getSpeed();
        drone.executeCommand("di");

        assertEquals(initialSpeed+1, drone.getSpeed());
    }

    @Test public void test08FetchSonda() {
        Drone drone = droneWithSpeedOneAndDeployedSonda();

        assertTrue(drone.isSondaDeployed());

        drone.executeCommand("f");
        assertFalse(drone.isSondaDeployed());
    }


    @Test public void test09DeployedSondaPreventsSpeedReachingZero() {
        assertThrowsLike(Drone.CatastrophicError, () -> droneWithSpeedOneAndDeployedSonda().executeCommand("s"));
    }

    @Test public void test10FetchingSondaAllowsStopping() {
        Drone drone = droneWithSpeedOneAndDeployedSonda();

        drone.executeCommand("fs");
        assertEquals(0,drone.getSpeed());
    }


    @Test public void test11CannotFetchUndeployedSonda() {
        assertThrowsLike(Drone.SondaNotDeployed, () -> new Drone().fetchSonda());
    }

    @Test public void test12GoingLeftAndRightReturnsOriginalDirection() {
        Drone drone = new Drone();
        String originalDirection = drone.getDirection();

        drone.executeCommand("lr");
        assertEquals(originalDirection, drone.getDirection());

    }

    @Test public void test13RotateAllRight(){
        Drone drone = new Drone();
        String originalDirection = drone.getDirection();

        drone.executeCommand("rrrr");
        assertEquals(originalDirection, drone.getDirection());

    }

    @Test public void test14RotateAllLeft(){
        Drone drone = new Drone();
        String originalDirection = drone.getDirection();

        drone.executeCommand("llll");
        assertEquals(originalDirection, drone.getDirection());

    }

    @Test public void test15SondaCanBeDeployedAfterFetch(){
        Drone drone = new Drone();
        drone.executeCommand("idfd");

        assertTrue(drone.isSondaDeployed());
    }

    @Test public void test16DeployedSondaPreventsDirectionChange() {
        assertThrowsLike(Drone.CatastrophicError, () -> droneWithSpeedOneAndDeployedSonda().executeCommand("r"));
    }

    @Test public void test17UnknownCommand() {
        assertThrowsLike("Unknown command!", () -> new Drone().executeCommand("x"));
    }

    private static void assertThrowsLike(String msg, Executable executable) {
        assertEquals(msg,
                assertThrows(Exception.class, executable)
                        .getMessage());
    }

    private static Drone droneWithSpeedOneAndDeployedSonda() {
        Drone drone = new Drone();
        drone.executeCommand("id");
        return drone;
    }

}
