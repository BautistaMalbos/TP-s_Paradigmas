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
        game.nextTurn();
    }

    public boolean canPlayOnCard(Card aCard) {
        return aCard.color.equals(this.color) || aCard.name().equals(this.name());
    }
}

