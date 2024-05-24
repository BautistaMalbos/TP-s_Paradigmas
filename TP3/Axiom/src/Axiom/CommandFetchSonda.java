package Axiom;

class CommandFetchSonda extends Command {
    public void execute(Drone drone) {
        drone.fetchSonda();
    }

    public boolean canHandle(char command) {
        return command == 'f';
    }

}
