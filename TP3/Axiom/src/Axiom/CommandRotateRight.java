package Axiom;

class CommandRotateRight extends Command {
    public void execute(Drone drone) {
        drone.rotateRight();
    }

    public boolean canHandle(char command) {
        return command == 'r';
    }
}
