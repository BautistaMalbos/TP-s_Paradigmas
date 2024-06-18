public class PlayerTurn extends GameInProcess{

    public PlayerTurn(UnoGame game) {
        super(game);
    }

    public void playCard(String aPlayer, Card aCard) {
        boolean cardFound = false;
        int cardIndex = 0;
        int jugadorPrevio;
        for (int i = 0; i < game.playerHand.size(); i++) {
            cardIndex = i;
            if (game.playerHand.get(i).equals(aCard)) {
                cardFound = true;
                break;
            }
        }
        if (!cardFound) {
            throw new RuntimeException("Player does not have the card!");
        } else {
            if (aCard.canPlayOnCard(game.pitCard())) {
                game.playerHand.remove(cardIndex);
                game.pit.add(aCard);
                jugadorPrevio = game.currentPlayerIndex;
                aCard.executeAction(this.game, aPlayer);
                gameCheckLastCard(aPlayer, aCard, jugadorPrevio);
                gameCheckMazoJugador(aPlayer);
            } else {
                throw new RuntimeException("Incompatible card!");
            }
        }
    }


    public void stealCard(String aPlayer) {
        Card stolenCard = game.deck.remove(game.deck.size() - 1);
        game.playerHands.get(aPlayer).add(stolenCard);

        if(stolenCard.canPlayOnCard(game.pitCard())) {
            game.pit.add(stolenCard);
            stolenCard.executeAction(this.game, aPlayer);
        }
        else{game.nextTurn();}
    }

    public void gameCheckMazoJugador(String aPlayer) {
        if (game.playerHands.get(aPlayer).size() == 0) {
            game.winner = aPlayer;
            new GameOver(game);
        }
    }

    private void gameCheckLastCard(String aPlayer, Card aCard, int jugadorPrevio) {
        if (game.playerHands.get(aPlayer).size() == 1 && !aCard.saidUno()) {
            game.playerHands.get(game.players.get(jugadorPrevio)).add(game.deck.remove(0));
            game.playerHands.get(game.players.get(jugadorPrevio)).add(game.deck.remove(0));
        }
    }

}
