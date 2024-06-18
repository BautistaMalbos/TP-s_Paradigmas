public class GameOver extends GameStatus {

    public GameOver(UnoGame game) {
        super(game);
        game.gameStatus = this;
        game.playerStates.put(game.players.get(game.currentPlayerIndex), game.gameStatus);
    }
    public void playCard(String playerName, Card card) {
        throw new RuntimeException("Game over!");
    }

    public void stealCard(String playerName) {
        throw new RuntimeException("Game over!");
    }

}
