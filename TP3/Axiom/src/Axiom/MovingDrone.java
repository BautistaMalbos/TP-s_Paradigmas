package Axiom;

public class MovingDrone extends Speed {
    private int speed;
    private Speed previous;

    public MovingDrone(Speed previousSpeed){
        this.speed = previousSpeed.getSpeed() + 1;
        this.previous = previousSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public Speed decreaseSpeed() {
        return previous;
    }

    public Speed increaseSpeed() {
        return new Axiom.MovingDrone(this);
    }

    public Speed deploySonda() {
        return this;
    }

    public void decelerateWithDeployedSonda( Sonda sondaOfficer ){}
}
