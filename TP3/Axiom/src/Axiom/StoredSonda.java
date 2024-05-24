package Axiom;

public class StoredSonda extends Sonda {
    private boolean sondaState;

    public StoredSonda(){
        this.sondaState = false;
    }

    public boolean checkSondaState(){
        return sondaState;
    }
    public Sonda deploySonda(){
        return new DeployedSonda(this);
    }

    public Sonda fetchSonda(){
        throw  new RuntimeException(Drone.SondaNotDeployed);
    }

    public Sonda rotate() {
        return this;
    }

    public void checkSpeedAndSonda( SpeedOfficer previousSpeed ){}
}
