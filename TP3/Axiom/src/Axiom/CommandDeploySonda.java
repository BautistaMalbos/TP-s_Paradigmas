package Axiom;

class CommandDeploySonda extends Command {
    public void execute(Drone drone) {
        drone.deploySonda();
    }

    public boolean canHandle(char command) {
        return command == 'd';
    }

}
