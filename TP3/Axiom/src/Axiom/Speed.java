package Axiom;

public abstract class Speed {
    public abstract int getSpeed();
    public abstract Speed decreaseSpeed();
    public abstract Speed increaseSpeed();
    public abstract Speed deploySonda();
    public abstract void decelerateWithDeployedSonda( Sonda sondaOfficer );

}
