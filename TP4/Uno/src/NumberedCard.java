public class NumberedCard extends Card {
    private String name;

    public NumberedCard(String color, String number){
        super(color);
        this.name = number;
    }

    public String name(){
        return name;
    }

    public void executeAction(UnoGame game, String playerName) {
        // Do nothing
    }

    public boolean canPlayOnCard(Card aCard) {
        return aCard.color.equals(this.color) || aCard.name().equals(this.name());
    }
}

