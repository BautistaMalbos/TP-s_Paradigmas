package Axiom;

public abstract class SpeedOfficer {
    public abstract int getSpeed();

    public abstract SpeedOfficer decreaseSpeed();

    public abstract SpeedOfficer increaseSpeed();

    public abstract SpeedOfficer deploySonda();

    public abstract void decelerateWithDeployedSonda( SondaOfficer sondaOfficer );

}

class MovingDrone extends SpeedOfficer {
    private int speed;
    private SpeedOfficer previous;

    public MovingDrone(SpeedOfficer previousSpeed){
        this.speed = previousSpeed.getSpeed() + 1;
        this.previous = previousSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public SpeedOfficer decreaseSpeed() {
        return previous;
    }

    public SpeedOfficer increaseSpeed() {
        return new MovingDrone(this);
    }

    public SpeedOfficer deploySonda() {
        return this;
    }

    public void decelerateWithDeployedSonda( SondaOfficer sondaOfficer ){}
}

class SteadyDrone extends SpeedOfficer {
    private int speed;
    private SpeedOfficer previous;
    public SteadyDrone(){
        this.speed = 0;
        this.previous = this;
    }

    public int getSpeed() {
        return speed;
    }

    public SpeedOfficer decreaseSpeed() {
        return previous;
    }

    public SpeedOfficer increaseSpeed() {
        return new MovingDrone(this);
    }

    public SpeedOfficer deploySonda() {
        throw new RuntimeException("Axiom is not moving!!!");
    }

    public void decelerateWithDeployedSonda( SondaOfficer sondaOfficer ) {
        throw new RuntimeException("Catastrophic error!!!");
    }
}
