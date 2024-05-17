public class Drone {
    //    public Drone axiom;
    public int speed;
    public String direction;

    public boolean sonda;

    public static String AxiomNotMoving = "Axiom is not moving!!!";
    public static String CatastrophicError = "Catastrophic error!!!";
    public static String SondaDeployed = "Sonda already deployed!!!";

    public static String SondaNotDeployed = "Sonda not deployed!!!";

    public Drone() {
        this.speed = 0;
        this.direction = "N";
        this.sonda = false;
    }

    public int getSpeed() {
        return this.speed;
    }

    public String getDirection() {
        return this.direction;
    }

    public Drone executeCommand( char command ) {

        if( command == 'r' ) {
            rotateRight();
        }
        else if( command == 'l' ) {
            rotateLeft();
        }
        else if( command == 's' ) {
            decreaseSpeed();
        }
        else if( command == 'i' ) {
            increaseSpeed();
        }
        else if( command == 'd' ) {
            deploySonda();
        }
        else if( command == 'f') {
            fetchSonda();
        }
        return this;
    }

    public void executeSeriesOfCommands(String commands) {
        char[] commandArray = commands.toCharArray();
        for (int i = 0; i < commandArray.length; i++) {
            char command = commandArray[i];
            executeCommand(command);
        }
    }

//    public void executeSeriesOfCommands( String multipleCommands ) {
//        for( char command : multipleCommands.toCharArray() ) {
//            executeCommand(command);
//        }
//    }


    public void rotateRight() {
        if ( !sonda ) {
            if (direction == "N") {
                this.direction = "E";
            }
            else if (direction == "E") {
                this.direction = "S";
            }
            else if (direction == "S") {
                this.direction = "W";
            }
            else if (direction == "W") {
                this.direction = "N";
            }
        }
    }

    public void rotateLeft() {
        if ( !sonda ) {
            if( direction == "N" ) {
                this.direction = "W";
            }
            else if( direction == "E" ) {
                this.direction = "N";
            }
            else if( direction == "S" ) {
                this.direction = "E";
            }
            else if( direction == "W" ) {
                this.direction = "S";
            }
        }
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

    public boolean isSondaDeployed() {
        return sonda;
    }

}
