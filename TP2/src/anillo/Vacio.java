package anillo;

public class Vacio extends SuperClase {
    public SuperClase next(){
        throw new RuntimeException("Anillo vacío.");
    }

    public Object current(){
        throw new RuntimeException("Anillo vacío.");
    }


    public SuperClase add(Object cargo){
        return new Cargado(cargo);
    }


    public SuperClase remove(){
        throw new RuntimeException("Anillo vacío");
    }

    public Object CargoActual(){
        throw new RuntimeException("Anillo vacío");
    }
}
