public class OtherPlayer extends GameInProcess {
    public OtherPlayer(UnoGame game) {
        super(game);
    }

    public void playCard(String aPlayer, Card aCard) {
        throw new RuntimeException("It's not player's turn!");
    }

    public void stealCard(String aPlayer) {
        throw new RuntimeException("It's not player's turn!");
    }
}
