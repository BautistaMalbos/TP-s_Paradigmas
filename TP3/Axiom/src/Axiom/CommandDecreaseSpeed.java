package Axiom;

class CommandDecreaseSpeed extends Command {
    public void execute(Drone drone) {
        drone.decreaseSpeed();
    }

    public boolean canHandle(char command) {
        return command == 's';
    }

}
