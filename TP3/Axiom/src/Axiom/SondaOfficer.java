package Axiom;

public abstract class SondaOfficer {
    public abstract boolean checkSondaState();

    public abstract SondaOfficer deploySonda();

    public abstract SondaOfficer fetchSonda();

    public abstract SondaOfficer rotate();

    public abstract void checkSpeedAndSonda( SpeedOfficer previousSpeed );
}

class DeployedSonda extends SondaOfficer {
    private boolean sondaState;
    private SondaOfficer deploy;
    private SondaOfficer fetch;

    public DeployedSonda(SondaOfficer sondaStored) {
        this.sondaState = true;
        this.deploy = this;
        this.fetch = sondaStored;
    }

    public boolean checkSondaState() {
        return this.sondaState;
    }

    public  SondaOfficer deploySonda(){
        return this.deploy;
    }
    public SondaOfficer fetchSonda() {
        return this.fetch;
    }

    public SondaOfficer rotate() {
        throw new RuntimeException("Catastrophic error!!!");
    }

    public void checkSpeedAndSonda(SpeedOfficer previousSpeed) {
        previousSpeed.decelerateWithDeployedSonda(this);
    }
}

class StoredSonda extends SondaOfficer {
    private boolean sondaState;

    public StoredSonda(){
        this.sondaState = false;
    }

    public boolean checkSondaState(){
        return sondaState;
    }
    public SondaOfficer deploySonda(){
        return new DeployedSonda(this);
    }

    public SondaOfficer fetchSonda(){
        throw  new RuntimeException("Sonda not deployed!!!");
    }

    public SondaOfficer rotate() {
        return this;
    }

    public void checkSpeedAndSonda( SpeedOfficer previousSpeed ){}
}


