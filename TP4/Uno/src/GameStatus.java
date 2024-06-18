public class GameStatus{
    public UnoGame game;

    public GameStatus(UnoGame game) {
        this.game = game;
    }

    public void playCard(String aPlayer, Card aCard) {
        game.gameStatus.playCard(aPlayer, aCard);
    }

    public void stealCard(String aPlayer) {
        game.gameStatus.stealCard(aPlayer);
    }

    public void declareWinner(String aPlayer) {game.gameStatus.declareWinner(aPlayer);}

}
