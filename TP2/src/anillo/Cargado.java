package anillo;

public class Cargado extends SuperClase {
    private Object cargoActual;
    private Object cargoPrevio;
    private SuperClase cargoSiguiente;

    public Cargado(Object cargo){
        cargoSiguiente = this;
        cargoPrevio = this;
        cargoActual = cargo;
    }

    public Cargado(Cargado cargoViejo, Object cargo){
        cargoViejo.cargoPrevio = this;
        cargoSiguiente = cargoViejo;
        cargoActual = cargo;
    }
    @Override
    public SuperClase next(){
        return this.cargoSiguiente;
    }

    @Override
    public Object current(){
        return cargoActual;
    }

    @Override
    public SuperClase add(Object cargo){
        SuperClase nuevaCarga = new Cargado(this, cargo);

        ((Cargado)(this).cargoSiguiente).cargoSiguiente = nuevaCarga;

        return nuevaCarga;
    }


    public Object CargoActual(){
        return cargoActual;
    }


    @Override
    public SuperClase remove(){
        if (this.cargoSiguiente == this){
            return new Vacio();
        }
        else{
            cargoActual = cargoSiguiente.CargoActual();
            cargoPrevio = this;

            return this;

        }
    }


}
