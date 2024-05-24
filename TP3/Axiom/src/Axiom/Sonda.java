package Axiom;

public abstract class Sonda {
    public abstract boolean checkSondaState();
    public abstract Sonda deploySonda();
    public abstract Sonda fetchSonda();
    public abstract Sonda rotate();
    public abstract void checkSpeedAndSonda( SpeedOfficer previousSpeed );
}
