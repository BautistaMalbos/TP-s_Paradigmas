package Axiom;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

public class Drone {
    //    public Drone axiom;
    public int speed;
    public Direction direction;
    public boolean sonda;
    private Command[] commands = new Command[128];


    public static String AxiomNotMoving = "Axiom is not moving!!!";
    public static String CatastrophicError = "Catastrophic error!!!";
    //public static String SondaDeployed = "Sonda already deployed!!!";

    public static String SondaNotDeployed = "Sonda not deployed!!!";

    public Drone() {
        this.speed = 0;
        this.direction = new North();
        this.sonda = false;
        initializeCommands();
    }

    public void initializeCommands() {
        commands['i'] = new CommandIncreaseSpeed();
        commands['s'] = new CommandDecreaseSpeed();
        commands['l'] = new CommandRotateLeft();
        commands['r'] = new CommandRotateRight();
        commands['d'] = new CommandDeploySonda();
        commands['f'] = new CommandFetchSonda();
    }

    public int getSpeed() {
        return speed;
    }

    public String getDirection() {
        return direction.getName();
    }

    public boolean isSondaDeployed() {
        return sonda;
    }

    public void increaseSpeed() {
        this.speed += 1;
    }

    public void decreaseSpeed() {
//        if( speed == 0 ) {
//            throw new RuntimeException(AxiomNotMoving);
//        }
        if( speed == 1 && sonda ) {
            throw new RuntimeException(CatastrophicError);
        }
        else if ( speed > 0 ){
            this.speed -= 1;
        }
    }

    public void rotateLeft() {
        if( isSondaDeployed() ){
            throw new RuntimeException(CatastrophicError);
        }
        direction = direction.rotateLeft();
    }

    public void rotateRight() {
        if( isSondaDeployed() ) {
            throw new RuntimeException(CatastrophicError);
        }
        direction = direction.rotateRight();
    }

    public void deploySonda() {
        if( speed > 0 ) {
//            if ( sonda ) {
//                throw new RuntimeException(SondaDeployed);
//            }
            if ( !sonda ) {
                this.sonda = true;
            }
        }
        else{
            throw new RuntimeException(AxiomNotMoving);
        }
    }

    public void fetchSonda() {
        if( !sonda ) {
            throw new RuntimeException(SondaNotDeployed);
        }
        else {
            this.sonda = false;
        }
    }

    public Drone executeCommand( char commandChar ) {
        Command command = commands[commandChar];
        if (command == null) {
            throw new IllegalArgumentException("Unknown command: " + commandChar);
        }
        command.execute(this);
        return this;
    }

//    public void executeSeriesOfCommands(String commands) {
//        char[] commandArray = commands.toCharArray();
//        for (int i = 0; i < commandArray.length; i++) {
//            char command = commandArray[i];
//            executeCommand(command);
//        }
//    }


    public void executeSeriesOfCommands(String commands) {
        for (char commandChar : commands.toCharArray()) {
            executeCommand(commandChar);
        }
    }


}
