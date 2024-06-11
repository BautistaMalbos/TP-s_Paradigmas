import java.util.Collections;
import java.util.List;

public class ReverseCard extends Card {
    public ReverseCard(String color){
        super(color);
    }

    public String name() {
        return "Reverse";
    }

    public void executeAction(UnoGame game, String playerName) {
        game.handleReverseCard();
    }

    public boolean canPlayOn(Card other) {
        return other.color.equals(this.color)||other.name().equals(this.name());
    }
}
