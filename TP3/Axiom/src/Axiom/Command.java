package Axiom;

public abstract class Command {
    public abstract void execute( Drone drone );
    public abstract boolean canHandle( char command );
}

class CommandDecreaseSpeed extends Command {
    public void execute(Drone drone) {
        drone.decreaseSpeed();
    }
    public boolean canHandle( char command ){
        return command == 's';
    }

}

class CommandDeploySonda extends Command {
    public void execute(Drone drone) {
        drone.deploySonda();
    }
    public boolean canHandle( char command ){
        return command == 'd';
    }

}

class CommandFetchSonda extends Command {
    public void execute(Drone drone) {
        drone.fetchSonda();
    }
    public boolean canHandle( char command ){
        return command == 'f';
    }

}

class CommandIncreaseSpeed extends Command {
    public void execute(Drone drone) {
        drone.increaseSpeed();
    }

    public boolean canHandle( char command ){
        return command == 'i';
    }
}

class CommandRotateLeft extends Command {
    public void execute(Drone drone) {
        drone.rotateLeft();
    }

    public boolean canHandle(char command) {
        return command == 'l';
    }
}

class CommandRotateRight extends Command {
    public void execute(Drone drone) {
        drone.rotateRight();
    }
    public boolean canHandle( char command ){
        return command == 'r';
    }
}

class CommandUnknown extends Command {
    public void execute( Drone drone ) {
        throw new RuntimeException("Unknown command!");
    }

    public boolean canHandle( char command ) {
        return true;
    }
}
