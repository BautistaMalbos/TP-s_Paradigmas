public abstract class   GameInProcess extends GameStatus {
    public GameInProcess(UnoGame game) {
        super(game);
    }
    public abstract void playCard(String aPlayer, Card aCard);

    public abstract void stealCard(String aPlayer);

}

