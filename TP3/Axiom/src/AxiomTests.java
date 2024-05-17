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
        assertEquals("W", new Drone().executeCommand('l').getDirection());
    }


    @Test public void test04DroneWithSpeedZeroGoesFasterAfterCommandIncrease() {
        Drone drone = new Drone();
        drone.executeSeriesOfCommands("ii");

        assertEquals(2, drone.getSpeed());
    }

    @Test public void test05DroneCannotHaveNegativeSpeed() {
        //assertThrowsLike(Drone.AxiomNotMoving, () -> new Drone().executeCommand('s'));
        assertEquals(0, new Drone().executeCommand('s').getSpeed());
    }

    @Test public void testDroneCanIncreaseSpeedAndInsistInStopping() {
        Drone drone = new Drone();
        drone.executeSeriesOfCommands("isss");

        assertEquals(0, drone.getSpeed());
    }

    @Test public void test06CannotDeploySondaWhileSteady() {
        Drone drone = new Drone();
        assertThrowsLike(Drone.AxiomNotMoving,() -> drone.executeCommand('d'));
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

//    @Test public void testNNDroneCanExecuteMultipleCommandsAtOnce() {
//        Drone drone = new Drone();
//        drone.executeSeriesOfCommands("idifd");                  PUEDE SER INNECESARIO
//
//        assertEquals(true,drone.sonda);
//    }


//    @Test public void test11DeployedSondaPreventsDirectionChange() {
//        Drone drone = new Drone();
//        drone.deploySonda();                                                      MANEJAR CASO
//
//        assertThrowsLike(Drone.SondaDeployed, () -> drone.executeCommand('r'));
//    }




    private static void assertThrowsLike(String msg, Executable executable) {
        assertEquals(msg,
                assertThrows(Exception.class, executable)
                        .getMessage());
    }

}
