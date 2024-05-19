package Axiom;

public class East extends Direction {
    public Direction rotateLeft() {
        return new North();
    }

    public Direction rotateRight() {
        return new South();
    }

    public String getName() {
        return "E";
    }
}
