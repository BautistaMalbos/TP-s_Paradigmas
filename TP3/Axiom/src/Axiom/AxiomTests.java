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
        Drone drone = new Drone();

        assertEquals("W", drone.executeCommand('l').getDirection());
    }

    @Test public void test04DroneWithSpeedZeroGoesFasterAfterCommandIncrease() {
        Drone drone = new Drone();
        drone.executeSeriesOfCommands("ii");

        assertEquals(2, drone.getSpeed());
    }

    @Test public void test05DroneCannotHaveNegativeSpeed() {
        Drone drone = new Drone();

        assertEquals(0, drone.executeCommand('s').getSpeed());
    }

    @Test public void testDroneCanIncreaseSpeedAndInsistInStopping() {
        Drone drone = new Drone();
        drone.executeSeriesOfCommands("isss");

        assertEquals(0, drone.getSpeed());
    }

    @Test public void test06CannotDeploySondaWhileSteady() {
        assertThrowsLike(Drone.AxiomNotMoving,() -> new Drone().executeCommand('d'));
    }

    @Test public void test07DeployedSondaDoesNotAffectSpeed() {
        Drone drone = new Drone();
        drone.increaseSpeed();
        int initialSpeed = drone.getSpeed();
        drone.executeCommand('d');

        assertEquals(initialSpeed, drone.getSpeed());
    }

    @Test public void test08FetchSonda() {
        Drone drone = new Drone();
        drone.increaseSpeed();
        drone.deploySonda();

        assertTrue(drone.isSondaDeployed());

        drone.executeCommand('f');
        assertFalse(drone.isSondaDeployed());
    }

    @Test public void test09DeployedSondaPreventsSpeedReachingZero() {
        Drone drone = new Drone();
        drone.increaseSpeed();
        drone.deploySonda();

        assertThrowsLike(Drone.CatastrophicError, () -> drone.decreaseSpeed());
    }

    @Test public void test10FetchingSondaAllowsStopping() {
        Drone drone = new Drone();
        drone.increaseSpeed();
        drone.deploySonda();

        drone.executeCommand('f');
        drone.executeCommand('s');
        assertEquals(0,drone.getSpeed());
    }


    @Test public void test11CannotFetchUndeployedSonda() {
        assertThrowsLike(Drone.SondaNotDeployed, () -> new Drone().fetchSonda());
    }

    @Test public void test12ReturningNotrh() {
        Drone drone = new Drone();
        drone.increaseSpeed();
        drone.executeSeriesOfCommands("lr");
        assertEquals("N", drone.getDirection());

    }

    @Test public void test13RotateAllRigth(){
        Drone drone = new Drone();
        drone.increaseSpeed();
        drone.executeSeriesOfCommands("rrrr");
        assertEquals("N", drone.getDirection());

    }

    @Test public void test14RotateAllLegth(){
        Drone drone = new Drone();
        drone.increaseSpeed();
        drone.executeSeriesOfCommands("llll");
        assertEquals("N", drone.getDirection());

    }

    @Test public void test15SondaCanBeDesployAfterFetch(){
        Drone drone = new Drone();
        drone.executeSeriesOfCommands("iidfd");
        assertEquals(true, drone.isSondaDeployed());
    }

    @Test public void test11DeployedSondaPreventsDirectionChange() {
        Drone drone = new Drone();
        drone.executeSeriesOfCommands("id");

        assertThrowsLike(Drone.CatastrophicError, () -> drone.executeCommand('r'));
    }

    private static void assertThrowsLike(String msg, Executable executable) {
        assertEquals(msg,
                assertThrows(Exception.class, executable)
                        .getMessage());
    }

}
