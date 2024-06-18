public abstract class Card {

    protected String color;
    protected boolean saidUno;
    public Card(String color ) {
        this.color = color;
        this.saidUno = false;
    }

    public boolean equals(Card aCard) {
        if (this == aCard) return true;
        if (aCard == null || getClass() != aCard.getClass()) return false;
        return (this.name().equals("Wild") && aCard.name().equals("Wild")) ||
                (color.equals(aCard.color) && name().equals(aCard.name()));
    }
    public abstract String name();

    public abstract void executeAction(UnoGame game, String playerName);

    public boolean canPlayOnCard(Card aCard) {
        return aCard.color.equals(this.color) || aCard.name().equals(this.name());
    }

    public Card shoutUno() {
        saidUno = true;
        return this;
    }

    public boolean saidUno() {
        return saidUno;
    }
}











