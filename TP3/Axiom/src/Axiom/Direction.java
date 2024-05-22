package Axiom;

public abstract class Direction {
    public abstract Direction rotateLeft();
    public abstract Direction rotateRight();
    public abstract String getName();
}

class North extends Direction {
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

class East extends Direction {
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

class South extends Direction {
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