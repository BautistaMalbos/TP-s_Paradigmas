package Axiom;

class CommandIncreaseSpeed extends Command {
    public void execute(Drone drone) {
        drone.increaseSpeed();
    }

    public boolean canHandle(char command) {
        return command == 'i';
    }
}
