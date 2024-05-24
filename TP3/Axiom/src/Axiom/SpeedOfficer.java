package Axiom;

public class SpeedOfficer {
    Speed actualSpeed = new SteadyDrone();

    public int getSpeed() {
        return actualSpeed.getSpeed();
    }

    public SpeedOfficer increaseSpeed() {
        actualSpeed = actualSpeed.increaseSpeed();
        return this;
    }

    public SpeedOfficer decreaseSpeed() {
        actualSpeed = actualSpeed.decreaseSpeed();
        return this;
    }

    public SpeedOfficer deploySonda() {
        actualSpeed = actualSpeed.deploySonda();
        return this;
    }

    public void decelerateWithDeployedSonda( Sonda sondaOfficer ) {
        actualSpeed.decelerateWithDeployedSonda( sondaOfficer );
    }

}

