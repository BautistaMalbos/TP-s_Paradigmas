
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnoTest {
    private List<Card> deckForTwo;
    private List<Card> deckForThree;
    private List<Card> pit;


    {deckForTwo = new ArrayList<>(Arrays.asList(
            //                 MAZO DESPUES DE REPARTIR 5 PARA CADA UNO Y 1 AL PIT
                new NumberedCard("Green", "2"), new NumberedCard("Green", "1"),

                 //                 CARTAS DE BOB           AL REPARTIR            CARTAS DE ALICE
                new WildCard("Wild"),            new ReverseCard("Green"),
                new NumberedCard("Blue", "0"), new NumberedCard("Red", "0"),
                new NumberedCard("Blue", "1"), new WildCard("Wild"),
                new NumberedCard("Blue", "2"), new NumberedCard("Red", "2"),
                new NumberedCard("Blue", "3"), new DrawTwoCard("Green"),
                new NumberedCard("Green", "3"), new SkipCard("Green"),
                            new NumberedCard("Green", "0")
        ));               //                          PIT CARD
        pit = new ArrayList<>();
    }


    {deckForThree = new ArrayList<>(Arrays.asList(
        //                 MAZO DESPUES DE REPARTIR 3 PARA CADA UNO Y 1 AL PIT
                new NumberedCard("Green", "3"), new NumberedCard("Green", "2"), new NumberedCard("Green", "1"),

//                                      CARTAS DE CHARLIE                CARTAS DE BOB                          CARTAS DE ALICE
                new NumberedCard("Green", "4"), new NumberedCard("Green", "5"), new NumberedCard("Green", "6"),
                new NumberedCard("Yellow", "3"), new NumberedCard("Blue", "3"), new NumberedCard("Red", "3"),
                new NumberedCard("Yellow", "2"), new NumberedCard("Blue", "2"), new NumberedCard("Red", "2"),
                new NumberedCard("Yellow", "1"), new NumberedCard("Blue", "1"), new NumberedCard("Red", "1"),
                new NumberedCard("Yellow", "0"), new NumberedCard("Blue", "1"), new NumberedCard("Red", "0"),
                new SkipCard("Yellow"),                 new SkipCard("Blue"),                  new SkipCard("Green"),
                                                    new NumberedCard("Green", "0")
        ));               //                                        PIT CARD
    pit = new ArrayList<>();
        }


    @Test void test01CanCreateAndSetAGame() {
        UnoGame game = validUnoGameTwoPlayers();

        assertEqualsPitCardColorAndNumber("0", game, "Green");
    }

    @Test void test02NotEnoughPlayersToStartGame() {
        assertThrowsLike("Game cannot be started with less than two players.", ()-> new UnoGame(deckForTwo,pit,"Alice"));
    }
    @Test void test03PlayerCanPlayACard() {
        UnoGame game = validUnoGameTwoPlayers();
        alicePlays0Red(game);

        assertEqualsPitCardColorAndNumber("0", game, "Red");
    }

    @Test void test04PlayerCanPlayACardAfterOtherPlayer() {
        UnoGame game = validUnoGameTwoPlayers();
        alicePlays0Red(game);
        bobPlays0Blue(game);

        assertEqualsPitCardColorAndNumber("0", game, "Blue");

    }
    @Test void test05PlayerCannotPlayACardIncompatibleWithPitCard() {
        assertThrowsLike("Incompatible card!",
                ()-> validUnoGameTwoPlayers().playCard("Alice", new NumberedCard("Red","2")));
    }

    @Test void test06PlayerCannotPlayACardTheyDontHave() {
        assertThrowsLike("Player does not have the card!",
                ()-> validUnoGameTwoPlayers().playCard("Alice", new NumberedCard("Green","2")));
    }

    @Test void test07InvalidName() {
        assertThrowsLike("Invalid player name!",
                ()-> validUnoGameTwoPlayers().playCard("Pepe", new NumberedCard("Green","2")));
    }

    @Test void test08PlayerCannotPlayTwiceInARowReceivesPenalty() {
        UnoGame game = validUnoGameTwoPlayers();
        alicePlays0Red(game);

        assertThrowsLike("It's not player's turn!",
                ()-> game.playCard("Alice", new NumberedCard("Red", "2")));


    }

    @Test void test09PlayerCanStealCardFromDeck() {
        UnoGame game = validUnoGameTwoPlayers();
        int aliceHandBeforeStealing = game.playerHands.get("Alice").size();
        int deckSizeBefore = deckForTwo.size();
        game.stealCard("Alice", deckForTwo);
        int aliceHandAfterStealing= game.playerHands.get("Alice").size();
        int deckSizeAfter = deckForTwo.size();

        assertEquals(aliceHandBeforeStealing + 1, aliceHandAfterStealing);
        assertEquals(deckSizeBefore-1, deckSizeAfter);
    }

    @Test void test10SkipCardSkipsOneTurn() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playCard("Alice", new SkipCard("Green"));

        assertThrowsLike("It's not player's turn!", ()-> game.playCard("Bob", new NumberedCard("Blue", "1")));
        assertEqualsPitCardColorAndNumber("Skip", game, "Green");
        assertEquals("Alice", game.currentTurn());

    }

    @Test void test11SkipCardCanBePlayedOnAnotherSkipCard() {
        UnoGame game = unoGameWithThreePlayers();
        game.playCard("Alice", new SkipCard("Green"));
        game.playCard("Charlie", new SkipCard("Yellow") );

        assertEqualsPitCardColorAndNumber("Skip",game,"Yellow");
        assertEquals("Bob", game.currentTurn());
    }

    @Test void test12DrawTwoCardMakesNextPlayerStealTwoCards() {
        UnoGame game = validUnoGameTwoPlayers();
        int bobHand = game.playerHands.get("Bob").size();
        game.playCard("Alice", new DrawTwoCard("Green"));


        assertEqualsPitCardColorAndNumber("Draw two", game, "Green");
        assertEquals(bobHand + 2, game.playerHands.get("Bob").size());
    }

    @Test void test13StealCompatibleCard() {
        UnoGame game = validUnoGameTwoPlayers();
        game.stealCard("Alice",deckForTwo);
        game.playCard("Bob", new NumberedCard("Blue", "1"));

        assertEqualsPitCardColorAndNumber("1", game, "Blue");
    }

    @Test void test14WildCardChangesCurrentPitColor() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playCard("Alice", new WildCard("Blue"));
        game.playCard("Bob", new NumberedCard("Blue", "1"));

        assertEqualsPitCardColorAndNumber("1", game, "Blue");
    }

    @Test void test15StealIncompatibleCard() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playCard("Alice", new WildCard("Yellow"));
        int bobHandBeforeStealing = game.playerHands.get("Bob").size();
        game.stealCard("Bob",deckForTwo);

        assertEquals("Alice", game.currentTurn());
        assertEquals(bobHandBeforeStealing+1, game.playerHands.get("Bob").size());
    }

    @Test void test16ReverseCardChangesTurnRotationDirection() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playCard("Alice", new ReverseCard("Green"));
        game.playCard("Bob", new NumberedCard("Green", "3"));

        assertEqualsPitCardColorAndNumber("3", game, "Green");
    }






    private UnoGame unoGameWithThreePlayers() {
        return new UnoGame(deckForThree, pit, "Alice", "Bob", "Charlie");
    }

    private UnoGame validUnoGameTwoPlayers() {
        return new UnoGame(deckForTwo, pit, "Alice", "Bob");
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

    private static void assertEqualsPitCardColorAndNumber(String name, UnoGame game, String color) {
        assertEquals(name, game.pitCard().name());
        assertEquals(color, game.pitCard().color);
    }


}
