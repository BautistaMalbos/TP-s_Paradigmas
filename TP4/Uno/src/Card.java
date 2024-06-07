public abstract class Card {

    protected String color;
    public Card(String color ) {
        this.color = color;
    }
//    public String color() {
//        return color;
//    }

    public boolean equals(Card aCard) {
        if (this == aCard) return true;
        if (aCard == null || getClass() != aCard.getClass()) return false;
        Card card = (Card) aCard;
        return color.equals(card.color) && number().equals(card.number());
    }
    public abstract String number();
    //public abstract String type();

    }




