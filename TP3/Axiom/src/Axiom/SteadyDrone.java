package Axiom;

public class SteadyDrone extends Speed {
    private int speed;
    private Speed previous;
    public SteadyDrone(){
        this.speed = 0;
        this.previous = this;
    }

    public int getSpeed() {
        return speed;
    }

    public Speed decreaseSpeed() {
        return previous;
    }

    public Speed increaseSpeed() {
        return new MovingDrone(this);
    }

    public Speed deploySonda() {
        throw new RuntimeException(Drone.AxiomNotMoving);
    }

    public void decelerateWithDeployedSonda( Sonda sondaOfficer ) {
        throw new RuntimeException(Drone.CatastrophicError);
    }
}
