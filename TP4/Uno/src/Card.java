public abstract class Card {

    protected String color;
    public Card(String color ) {
        this.color = color;
    }

    public boolean equals(Card aCard) {
        if (this == aCard) return true;
        if (aCard == null || getClass() != aCard.getClass()) return false;
        return color.equals(aCard.color) && name().equals(aCard.name());
    }
    public abstract String name();

    public boolean isWildCard() {
        return false;
    }

    }




