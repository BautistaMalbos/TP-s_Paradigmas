package Axiom;

import java.util.ArrayList;
import java.util.Arrays;

public class Drone {

    public SpeedOfficer speedOfficer;
    public SondaOfficer sondaOfficer;

    public Direction direction;
    public static ArrayList<Command> commands = new ArrayList<>(Arrays.asList(new CommandIncreaseSpeed(),
                                new CommandDecreaseSpeed(),
                                new CommandRotateLeft(),
                                new CommandRotateRight(),
                                new CommandDeploySonda(),
                                new CommandFetchSonda(),
                                new CommandUnknown()));

    public static String AxiomNotMoving = "Axiom is not moving!!!";
    public static String CatastrophicError = "Catastrophic error!!!";
    public static String SondaDeployed = "Sonda already deployed!!!";

    public static String SondaNotDeployed = "Sonda not deployed!!!";

    public Drone() {
        this.speedOfficer = new SteadyDrone();
        this.sondaOfficer = new StoredSonda();
        this.direction = new North();
    }

    public int getSpeed() {
        return speedOfficer.getSpeed();
    }

    public String getDirection() {
        return direction.getName();
    }

    public boolean isSondaDeployed() {
        return sondaOfficer.checkSondaState();
    }

    public void increaseSpeed() {
        this.speedOfficer = speedOfficer.increaseSpeed();
    }

    public void decreaseSpeed() {
        sondaOfficer.checkSpeedAndSonda(speedOfficer.decreaseSpeed());
        speedOfficer = speedOfficer.decreaseSpeed();
    }

    public void rotateLeft() {
        sondaOfficer.rotate();
        direction = direction.rotateLeft();
    }

    public void rotateRight() {
        sondaOfficer.rotate();
        direction = direction.rotateRight();
    }

    public void deploySonda() {
        speedOfficer.deploySonda();
        sondaOfficer = sondaOfficer.deploySonda();

    }

    public void fetchSonda() {
        sondaOfficer = sondaOfficer.fetchSonda();
    }

    public Drone executeCommand(char commandChar) {
        commands.stream()
                .filter(command -> command.canHandle(commandChar))
                .findFirst()
                .ifPresent(command -> command.execute(this));
        return this;
    }

    public void executeSeriesOfCommands(String commandString) {
        commandString.chars()
                .mapToObj(c -> (char) c)
                .forEach(this::executeCommand);
    }

}
