
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnoTest {
    private List<Card> deck;
    private List<Card> pit;


    {deck = new ArrayList<>(Arrays.asList(
            //                 MAZO DESPUES DE REPARTIR 5 PARA CADA UNO Y 1 AL PIT
                new NumberedCard("Green", "2"), new NumberedCard("Green", "1"),

                 //                 CARTAS DE BOB           AL REPARTIR            CARTAS DE ALICE
                new NumberedCard("Blue", "0"), new NumberedCard("Red", "0"),
                new NumberedCard("Blue", "1"), new NumberedCard("Red", "1"),
                new NumberedCard("Blue", "2"), new NumberedCard("Red", "2"),
                new NumberedCard("Blue", "3"), new NumberedCard("Red", "3"),
                new NumberedCard("Blue", "4"), new NumberedCard("Red", "4"),
                            new NumberedCard("Green", "0")
        ));               //                          PIT CARD
        pit = new ArrayList<>();
    }


    @Test void test01CanCreateAndSetAGame() {
        UnoGame game = validUnoGame();

        assertEqualsPitCardColorAndNumber("0", game, "Green");
    }

    @Test void test02NotEnoughPlayersToStartGame() {
        assertThrowsLike("Game cannot be started with less than two players.", ()-> new UnoGame(deck,pit,"Alice"));
    }
    @Test void test03PlayerCanPlayACard() {
        UnoGame game = validUnoGame();
        alicePlays0Red(game);

        assertEqualsPitCardColorAndNumber("0", game, "Red");
    }

    @Test void test04PlayerCanPlayACardAfterOtherPlayer() {
        UnoGame game = validUnoGame();
        alicePlays0Red(game);
        bobPlays0Blue(game);

        assertEqualsPitCardColorAndNumber("0", game, "Blue");

    }
    @Test void test05PlayerCannotPlayACardIncompatibleWithPitCard() {
        assertThrowsLike("Incompatible card!",
                ()-> validUnoGame().playCard("Alice", new NumberedCard("Red","2")));
    }

    @Test void test06PlayerCannotPlayACardTheyDontHave() {
        assertThrowsLike("Player does not have the card!",
                ()-> validUnoGame().playCard("Alice", new NumberedCard("Green","2")));
    }

    @Test void test07InvalidName() {
        assertThrowsLike("Invalid player name!",
                ()-> validUnoGame().playCard("Pepe", new NumberedCard("Green","2")));
    }

    @Test void test08PlayerCannotPlayTwiceInARow() {
        UnoGame game = validUnoGame();
        alicePlays0Red(game);

        assertThrowsLike("It's not player's turn!",
                ()-> game.playCard("Alice", new NumberedCard("Red", "2")));
    }

    @Test void test09PlayerCanStealCardFromDeck() {
        UnoGame game = validUnoGame();
        int aliceHandBeforeStealing = game.playerHands.get("Alice").size();
        int deckSizeBefore = deck.size();
        game.stealCard("Alice", deck);
        int aliceHandAfterStealing= game.playerHands.get("Alice").size();
        int deckSizeAfter = deck.size();

        assertEquals(aliceHandBeforeStealing + 1, aliceHandAfterStealing);
        assertEquals(deckSizeBefore-1,deckSizeAfter);
    }











    private UnoGame validUnoGame() {
        return new UnoGame(deck, pit, "Alice", "Bob");
    }
    private static void alicePlays0Red(UnoGame game) {
        game.playCard("Alice", new NumberedCard("Red", "0"));
    }

    private static void bobPlays0Blue(UnoGame game) {
        game.playCard("Bob", new NumberedCard("Blue", "0"));
    }
    private void assertThrowsLike( String message, Executable codeBlock ) {
        assertEquals( message, assertThrows( Throwable.class, codeBlock ). getMessage() );
    }

    private static void assertEqualsPitCardColorAndNumber(String number, UnoGame game, String Red) {
        assertEquals(number, game.pitCard().number());
        assertEquals(Red, game.pitCard().color);
    }


}
