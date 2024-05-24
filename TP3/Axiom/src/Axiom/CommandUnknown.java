package Axiom;

class CommandUnknown extends Command {
    public void execute(Drone drone) {
        throw new RuntimeException("Unknown command!");
    }

    public boolean canHandle(char command) {
        return true;
    }
}
