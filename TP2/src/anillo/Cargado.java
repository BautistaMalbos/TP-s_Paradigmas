package anillo;

public class Cargado extends SuperClase {
    private Object cargoActual;
    private Cargado cargoSiguiente;

    public Cargado(Object cargo){
        cargoActual = cargo;
        cargoSiguiente = this;

    }

    public Cargado(Object cargo, Cargado cargoViejo){
        cargoActual = cargo;
        cargoSiguiente = cargoViejo;

    }
    public SuperClase next(){
        return cargoSiguiente;
    }

    public Object current(){
        return cargoActual;
    }

    public SuperClase add(Object cargo){
        Cargado nuevoCargo = new Cargado(cargo, this);
        Cargado siguienteCargado = this.cargoSiguiente;
        siguienteCargado.cargoSiguiente = nuevoCargo;
        return nuevoCargo;
    }
    public SuperClase remove(){
        if (cargoSiguiente == this){
            return new Vacio();
        }
        else{
            cargoActual = cargoSiguiente.current();

            return this;

        }
    }


}
