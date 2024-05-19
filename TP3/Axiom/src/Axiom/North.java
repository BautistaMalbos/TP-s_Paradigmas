package Axiom;

public class North extends Direction {
    public Direction rotateLeft() {
        return new West();
    }

    public Direction rotateRight() {
        return new East();
    }

    public String getName() {
        return "N";
    }
}