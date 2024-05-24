package Axiom;

class West extends Direction {
    public Direction rotateLeft() {
        return new South();
    }

    public Direction rotateRight() {
        return new North();
    }

    public String getName() {
        return "W";
    }
}