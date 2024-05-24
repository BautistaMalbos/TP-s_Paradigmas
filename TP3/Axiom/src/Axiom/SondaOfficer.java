package Axiom;

public class SondaOfficer {
    Sonda sonda = new StoredSonda();

    public boolean checkSondaState() {
        return sonda.checkSondaState();
    }

    public SondaOfficer deploySonda() {
        sonda = sonda.deploySonda();
        return this;
    }

    public SondaOfficer fetchSonda() {
        sonda = sonda.fetchSonda();
        return this;
    }

    public Sonda rotate() {
        return sonda.rotate();
    }

    public void checkSpeedAndSonda( SpeedOfficer previousSpeed ) {
        sonda.checkSpeedAndSonda( previousSpeed );
    }

}



