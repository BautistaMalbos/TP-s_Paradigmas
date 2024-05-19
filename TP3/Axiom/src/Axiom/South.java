package Axiom;

public class South extends Direction {
    public Direction rotateLeft() {
        return new East();
    }

    public Direction rotateRight() {
        return new West();
    }

    public String getName() {
        return "S";
    }
}