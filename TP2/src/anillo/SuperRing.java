package anillo;

public abstract class SuperRing {
    public abstract Ring next();
    public abstract Object current();
    public abstract Ring add();
    public abstract Ring remove();
}
