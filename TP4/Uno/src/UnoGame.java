import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnoGame {
    private List<Card> deck;
    private List<Card> pit;
    private List<String> players;
    private int currentPlayerIndex;
    HashMap <String, List<Card>> playerHands;


    public UnoGame(List <Card> deck, List<Card> pit, String ... players ) {
        this.deck = deck;
        this.pit = pit;
        this.playerHands = new HashMap<>();
        this.players = new ArrayList<>();

        for (String player : players) {
            playerHands.put(player, new ArrayList<>());
            this.players.add(player);
        }
        checkPlayers();
        startGame();
        this.currentPlayerIndex = 0;
    }

    private void checkPlayers() {
        if (playerHands.size() < 2) {
            throw new RuntimeException("Game cannot be started with less than two players.");
        }
    }

    private void startGame() {
        distributeCardsToPit();
        distributeCardsToPlayers();
    }

    private void distributeCardsToPit() {
        if (!deck.isEmpty()) {
            Card card = deck.remove(deck.size() - 1);
            pit.add(card);
            System.out.println("Added to pit: " + pitCard().number() + pitCard().color );
        }
    }

    private void distributeCardsToPlayers() {
        //int playerCount = playerHands.size();
        int cardsPerPlayer = 5;

        for (int i = 0; i < cardsPerPlayer; i++) {

            for (String player : players) {
                if (!deck.isEmpty()) {
                    Card card = deck.remove(deck.size() - 1);
                    playerHands.get(player).add(card);
                    System.out.println("Added to " + player + "'s hand: " + card.number() + card.color);
                }
            }
        }
        System.out.println("Remaining deck: " + deck.get(0).color + deck.get(0).number()
                                              + deck.get(1).color + deck.get(1).number());
    }


    public void playCard(String playerName, Card card) {
            List<Card> playerHand = playerHands.get(playerName);
            if (playerHand == null) {
                throw new RuntimeException("Invalid player name!");
            }
            else if (playersTurn(playerName)) {
            boolean cardFound = false;
            for (Card playerCard : playerHand) {
                if (playerCard.equals(card)) {
                    cardFound = true;
                    break;
                }
            }
            if (!cardFound) {
                throw new RuntimeException("Player does not have the card!");
            } else {
                if (card.color == pitCard().color || card.number() == pitCard().number()) {
                    playerHand.remove(card);
                    pit.add(card);
                    System.out.println("Top of Pit: " + pitCard().number() + pitCard().color);
//                    if(card.number() == "Skip"){}
                    nextTurn();
                } else {
                    throw new RuntimeException("Incompatible card!");
                }
            }
        }
        else{
            throw new RuntimeException("It's not player's turn!");
        }
    }

    private boolean playersTurn(String playerName) {
        return players.get(currentPlayerIndex).equals(playerName);
    }

    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerHands.size();
    }


    public Card pitCard() {
        if (!pit.isEmpty()) {
            return pit.get(pit.size() - 1);
        }
        return null;
    }

    public void stealCard(String aPlayer, List<Card> deck) {
        if(playersTurn(aPlayer)){
            if (!deck.isEmpty()) {
                Card stolenCard = deck.remove(deck.size() - 1);
                playerHands.get(aPlayer).add(stolenCard);
                System.out.println(aPlayer + " stole a card: " + stolenCard.number() + " " + stolenCard.color);
                nextTurn();
            } else {
                throw new RuntimeException("Deck is empty!");
            }
        } else {
            throw new RuntimeException("It's not player's turn!");
        }
    }
}
