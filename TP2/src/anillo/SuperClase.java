package anillo;

public abstract class SuperClase {
    public abstract SuperClase next();
    public abstract Object current();
    public abstract SuperClase add(Object cargo);
    public abstract SuperClase remove();
}
