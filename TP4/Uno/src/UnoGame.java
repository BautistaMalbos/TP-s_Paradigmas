import java.util.ArrayList;
import java.util.Collections;
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
        //int playerCount = playerHands.size();

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
                if (card.color == pitCard().color || card.name() == pitCard().name()){
                    playerHand.remove(card);
                    pit.add(card);
                    //System.out.println("Top of Pit: " + pitCard().name() + pitCard().color);
                    if(card.name() == "Skip"){
                        handleSkipCard();
                    }
                    else if(card.name() == "Draw two"){
                        int nextPlayer = nextTurn();
                        penaltyStealTwoCards(players.get(nextPlayer));
                        nextTurn();

                    }
                    else if(card.name() == "Reverse") {
                        Collections.reverse(players);
                        //nextTurn();
                    }
                    else {
                        nextTurn();
                    }
                } else if (card.isWildCard()) {
                    playerHand.remove(card);
                    ((WildCard) card).chooseColor();
                    pit.add(card);
                    nextTurn();
                }
                else {
                    throw new RuntimeException("Incompatible card!");
//                    penaltyStealTwoCards(playerName);
                }
            }
            System.out.println(playerName +" Played a: " + pitCard().name() + pitCard().color);
        }
        else{
            throw new RuntimeException("It's not player's turn!");
//            penaltyStealTwoCards(playerName);
        }
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

    public void stealCard(String aPlayer, List<Card> deck) {
        if(playersTurn(aPlayer)){
            if (!deck.isEmpty()) {
                Card stolenCard = deck.remove(deck.size() - 1);
                playerHands.get(aPlayer).add(stolenCard);
                System.out.println(aPlayer + " stole a card: " + stolenCard.name() + " " + stolenCard.color);
                if(stolenCard.color == pitCard().color || stolenCard.name() == pitCard().name()){
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
        nextTurn();
    }



    public void penaltyStealTwoCards(String aPlayer) {
        playerHands.get(aPlayer).add(deck.remove(0));
        playerHands.get(aPlayer).add(deck.remove(0));
        nextTurn();
    }





}
