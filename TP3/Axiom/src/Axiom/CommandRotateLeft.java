package Axiom;

class CommandRotateLeft extends Command {
    public void execute(Drone drone) {
        drone.rotateLeft();
    }

    public boolean canHandle(char command) {
        return command == 'l';
    }
}
