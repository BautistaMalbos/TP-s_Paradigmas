public class SkipCard extends Card {
    public SkipCard(String color){
        super(color);
    }

    public String name() {
        return "Skip";
    }

    public void executeAction(UnoGame game, String playerName) {
        game.handleSkipCard();
    }

    public boolean canPlayOnCard(Card other) {
        return other.color.equals(this.color)||other.name().equals(this.name());
    }

}


