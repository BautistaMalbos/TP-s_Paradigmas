public class WildCard extends Card {
    private String chosenColor;
    public WildCard(){
        super(null);
    }

    public String name() {
        return "Wild";
    }

    public Card chooseColor(String chosenColor) {
        if(chosenColor != "Yellow" && chosenColor != "Red" && chosenColor != "Green" && chosenColor != "Blue"){
            throw new RuntimeException("Invalid color");
        }
        this.color = chosenColor;
        return this;
    }
    public void executeAction(UnoGame game, String playerName) {
        game.nextTurn();
    }

    public boolean canPlayOnCard(Card aCard) {
        return true;
    }

}
