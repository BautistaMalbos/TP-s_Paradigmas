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

    public abstract void executeAction(UnoGame game, String playerName);

    public boolean canPlayOnCard(Card aCard) {
        return aCard.color.equals(this.color) || aCard.name().equals(this.name());
    }

}




