import java.util.*;

public class UnoGame {
    public String winner;
    public int currentPlayerIndex;
    public List<String> players;
    public List<Card> deck;
    public List<Card> pit;
    public List<Card> playerHand;
    HashMap <String, List<Card>> playerHands;
    public GameStatus gameStatus;
    HashMap <String, GameStatus> playerStates;



    public UnoGame(List <Card> deck, String ... players ) {
        this.deck = deck;
        this.playerHands = new HashMap<>();
        this.players = new ArrayList<>();
        this.pit = new ArrayList<>();
        this.playerStates = new HashMap<>();
        this.playerHand = new ArrayList<>();

        for (String player : players) {
            playerHands.put(player, new ArrayList<>());
            this.players.add(player);
        }
        checkPlayers();

        this.gameStatus = new PlayerTurn(this);
        playerStates.put(players[0], new PlayerTurn(this));

        for (int i = 1; i < players.length; i++) {
            playerStates.put(players[i], new OtherPlayer(this));
        }
        this.currentPlayerIndex = 0;
        startGame();
    }


    public void playCard(String playerName, Card aCard) {
        this.playerHand = playerHands.get(playerName);
        playerStates.get(playerName).playCard(playerName, aCard);

    }

    public String currentTurn() {
        return players.get(currentPlayerIndex);
    }

    public Card pitCard() {
        return pit.get(pit.size() - 1);
    }

    public void stealACard(String aPlayer) {
        playerStates.get(aPlayer).stealCard(aPlayer);
    }

    public String declareWinner() {
        return winner;
    }



    // Manejo de Cartas
    public void nextTurn() {

        int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        String currentPlayer = players.get(currentPlayerIndex);
        String nextPlayer = players.get(nextPlayerIndex);

        GameStatus currentPlayerTurn = playerStates.get(currentPlayer);
        GameStatus nextPlayerTurn = playerStates.get(nextPlayer);
        playerStates.put(currentPlayer, nextPlayerTurn);
        playerStates.put(nextPlayer, currentPlayerTurn);

        currentPlayerIndex = nextPlayerIndex;
        gameStatus = playerStates.get(players.get(currentPlayerIndex));
    }

    public void handleSkipCard() {
        nextTurn();
        nextTurn();
    }


    public void handleDrawTwoCard() {
        nextTurn();
        playerHands.get(players.get(currentPlayerIndex)).add(deck.remove(0));
        playerHands.get(players.get(currentPlayerIndex)).add(deck.remove(0));

    }

    public void handleReverseCard() {
        Collections.reverse(players);
        currentPlayerIndex = players.size() - 1 - currentPlayerIndex;
        nextTurn();
    }




    // Creacion de los mazos y del juego
    private void checkPlayers() {
        if (playerHands.size() < 2) {
            throw new RuntimeException("Game cannot be started with less than two players.");
        }
    }

    private void startGame() {
        distributeCardsToPit();
        distributeCardsToPlayers(7);
    }

    private void distributeCardsToPit() {
        Card card = deck.remove(deck.size() - 1);
        pit.add(card);

    }

    public void distributeCardsToPlayers(int cardsPerPlayer) {
        for (int i = 0; i < cardsPerPlayer; i++) {
            for (String player : playerHands.keySet()) {
                Card card = deck.remove(deck.size() - 1);
                playerHands.get(player).add(card);
            }
        }
    }


}
