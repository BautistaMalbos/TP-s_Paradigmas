package Axiom;

public class DeployedSonda extends Sonda {
    private boolean sondaState;
    private Sonda deploy;
    private Sonda fetch;

    public DeployedSonda(Sonda sondaStored) {
        this.sondaState = true;
        this.deploy = this;
        this.fetch = sondaStored;
    }

    public boolean checkSondaState() {
        return this.sondaState;
    }

    public  Sonda deploySonda(){
        return this.deploy;
    }
    public Sonda fetchSonda() {
        return this.fetch;
    }

    public Sonda rotate() {
        throw new RuntimeException("Catastrophic error!!!");
    }

    public void checkSpeedAndSonda(SpeedOfficer previousSpeed) {
        previousSpeed.decelerateWithDeployedSonda(this);
    }
}
