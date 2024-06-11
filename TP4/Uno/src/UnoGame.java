import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class UnoGame {
    public List<Card> deck;
    public List<Card> pit;
    public List<String> players;
    public int currentPlayerIndex;
    HashMap <String, List<Card>> playerHands;


    public UnoGame(List <Card> deck, String ... players ) {
        this.deck = deck;
        this.playerHands = new HashMap<>();
        this.players = new ArrayList<>();
        this.pit = new ArrayList<>();
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
        distributeCardsToPlayers(6);
    }

    private void distributeCardsToPit() {
        if (!deck.isEmpty()) {
            Card card = deck.remove(deck.size() - 1);
            pit.add(card);
            System.out.println("Added to pit: " + pitCard().name() + pitCard().color );
        }
    }

    private void distributeCardsToPlayers(int cardsPerPlayer) {
        for (int i = 0; i < cardsPerPlayer; i++) {

            for (String player : players) {
                if (!deck.isEmpty()) {
                    Card card = deck.remove(deck.size() - 1);
                    playerHands.get(player).add(card);
                    System.out.println("Added to " + player + "'s hand: " + card.name() + card.color);
                }
            }
        }
        System.out.println("Remaining deck: " + deck.get(0).color + deck.get(0).name()
                                              + " "
                                              + deck.get(1).color + deck.get(1).name());
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
                if (card.canPlayOnCard(pitCard())) {
                    playerHand.remove(card);
                    pit.add(card);
                    card.executeAction(this, playerName);
                    nextTurn();
                }
//                    if(card.name() == "Skip"){
//                        handleSkipCard();
//                    }
//                    else if(card.name() == "Draw two"){
//                        int nextPlayer = nextTurn();
//                        handleDrawTwoCard(players.get(nextPlayer));
//                        nextTurn();
//
//                    }
//                    else if(card.name() == "Reverse") {
//                        handleReverseCard();
//                    }
//                    else {
//                        nextTurn();
//                    }
//                } else if (card.isWildCard()) {
//                    playerHand.remove(card);
//                    ((WildCard) card).chooseColor();
//                    pit.add(card);
//                    nextTurn();
//                }
                else {
                    throw new RuntimeException("Incompatible card!");
                }
            }
            System.out.println(playerName +" Played a: " + pitCard().name() + pitCard().color);
        }
        else{
            throw new RuntimeException("It's not player's turn!");
        }
    }

    public void handleReverseCard() {
        Collections.reverse(players);
        nextTurn();
    }

    private boolean playersTurn(String playerName) {
        return players.get(currentPlayerIndex).equals(playerName);
    }

    private int nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerHands.size();
        return currentPlayerIndex;
    }

    public String currentTurn() {
        return players.get(currentPlayerIndex);
    }

    public Card pitCard() {
        if (!pit.isEmpty()) {
            return pit.get(pit.size() - 1);
        }
        return null;
    }

    public void stealACard(String aPlayer, List<Card> deck) {
        if(playersTurn(aPlayer)){
            if (!deck.isEmpty()) {
                Card stolenCard = deck.remove(deck.size() - 1);
                playerHands.get(aPlayer).add(stolenCard);
                System.out.println(aPlayer + " stole a card: " + stolenCard.name() + " " + stolenCard.color);
                if(stolenCard.canPlayOnCard(pitCard())){
                    pit.add(stolenCard);
                    nextTurn();
                }
                else{
                    nextTurn();
                }
            } else {
                throw new RuntimeException("Deck is empty!");
            }
        } else {
            throw new RuntimeException("It's not player's turn!");
        }
    }

    public void handleSkipCard() {
        nextTurn();
    }



    public void handleDrawTwoCard() {
        int nextPlayer = nextTurn();
        playerHands.get(players.get(nextPlayer)).add(deck.remove(0));
        playerHands.get(players.get(nextPlayer)).add(deck.remove(0));
        nextTurn();
    }

}
