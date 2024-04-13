package anillo;

public class Ring {
    public SuperClase actual;

    public Ring(){
        this.actual = new Vacio();
    }
    public Ring next() {
        actual = actual.next();
        return this;
    }

    public Object current() {
        return actual.current();
    }

    public Ring add(Object cargo) {
        actual = actual.add(cargo);
        return this;
    }


    public Ring remove() {
        actual = actual.remove();
        return this;
    }


}