public class DrawTwoCard extends Card{
    public DrawTwoCard(String color){
        super(color);
    }

    public String name() {
        return "Draw two";
    }

    public void executeAction(UnoGame game, String playerName) {
        game.handleDrawTwoCard();
    }

    public boolean canPlayOnCard(Card aCard) {
        return aCard.color.equals(this.color);
    }
}
