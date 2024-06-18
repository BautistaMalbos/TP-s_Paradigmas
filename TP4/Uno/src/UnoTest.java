
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

    {deckForTwo = new ArrayList<>(Arrays.asList(
            //                 MAZO DESPUES DE REPARTIR 5 PARA CADA UNO Y 1 AL PIT
            new DrawTwoCard("Red"), new NumberedCard("Blue", "8"),
            new DrawTwoCard("Green"), new NumberedCard("Green", "1"),

            //       CARTAS DE ALICE           AL REPARTIR            CARTAS DE BOB
            new NumberedCard("Yellow", "1"),               new NumberedCard("Green", "4"),
            new ReverseCard("Green"),               new ReverseCard("Yellow"),
            new WildCard(),                               new NumberedCard("Red", "0"),
            new DrawTwoCard("Green"),               new NumberedCard("Blue", "1"),
            new SkipCard("Green"),                  new NumberedCard("Green", "3"),
            new NumberedCard("Blue", "2"),  new NumberedCard("Red", "1"),
            new NumberedCard("Blue", "0"),  new NumberedCard("Red", "0"),
            new NumberedCard("Green", "0")
    ));               //                          PIT CARD
    }



    {deckForThree = new ArrayList<>(Arrays.asList(
            //                 MAZO DESPUES DE REPARTIR 6 PARA CADA UNO Y 1 AL PIT
            new NumberedCard("Green", "9"), new NumberedCard("Green", "8"), new NumberedCard("Green", "7"),
            new NumberedCard("Blue", "0"), new NumberedCard("Green", "2"), new NumberedCard("Green", "1"),

//                                      CARTAS DE CHARLIE                CARTAS DE ALICE                          CARTAS DE BOB
            new NumberedCard("Green", "4"), new NumberedCard("Green", "5"), new NumberedCard("Green", "6"),
            new NumberedCard("Yellow", "3"), new WildCard(),                            new NumberedCard("Red", "3"),
            new ReverseCard("Blue"),                new ReverseCard("Green"),                new NumberedCard("Red", "2"),
            new NumberedCard("Yellow", "1"), new DrawTwoCard("Green"),                  new NumberedCard("Red", "1"),
            new NumberedCard("Yellow", "0"), new NumberedCard("Green", "1"), new ReverseCard("Yellow"),
            new SkipCard("Green"),                 new SkipCard("Yellow"),                  new SkipCard("Blue"),
            new NumberedCard("Green", "0")
    ));               //                                        PIT CARD
    }


    @Test void test01CanCreateAndSetAGame() {
        UnoGame game = validUnoGameTwoPlayers();

        assertEqualsPitCardColorAndNumber("0", game, "Green");
    }

    @Test void test02NotEnoughPlayersToStartGame() {
        assertThrowsLike("Game cannot be started with less than two players.",
                ()-> new UnoGame(deckForTwo, "Alice"));
    }
    @Test void test03PlayerCanPlayACard() {
        UnoGame game = validUnoGameTwoPlayers();
        alicePlays0Blue(game);

        assertEqualsPitCardColorAndNumber("0", game, "Blue");
        assertEquals(6, game.playerHands.get("Alice").size());
    }

    @Test void test04PlayerCanPlayACardAfterOtherPlayer() {
        UnoGame game = validUnoGameTwoPlayers();
        alicePlays0Blue(game);
        bobPlays0Red(game);

        assertEqualsPitCardColorAndNumber("0", game, "Red");

    }
    @Test void test05PlayerCannotPlayACardIncompatibleWithPitCard() {
        assertThrowsLike("Incompatible card!",
                ()-> validUnoGameTwoPlayers().playCard("Alice", new NumberedCard("Blue","2")));
    }

    @Test void test06PlayerCannotPlayACardTheyDontHave() {
        assertThrowsLike("Player does not have the card!",
                ()-> validUnoGameTwoPlayers().playCard("Alice", new NumberedCard("Green","2")));
    }

    @Test void test07PlayerCanStealCardFromDeck() {
        UnoGame game = validUnoGameTwoPlayers();
        int deckSizeBefore = deckForTwo.size();
        game.stealACard("Alice");
        int deckSizeAfter = deckForTwo.size();

        assertEquals(deckSizeBefore-1, deckSizeAfter);
    }

    @Test void test08SkipCardSkipsOneTurn() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playCard("Alice", new SkipCard("Green"));

        assertThrowsLike("It's not player's turn!", ()-> game.playCard("Bob", new NumberedCard("Green", "3")));
        assertEqualsPitCardColorAndNumber("Skip", game, "Green");
    }

    @Test void test09 () {

    }

    @Test void test10DrawTwoCardMakesNextPlayerStealTwoCards() {
        UnoGame game = validUnoGameTwoPlayers();
        int bobHand = game.playerHands.get("Bob").size();
        game.playCard("Alice", new DrawTwoCard("Green"));
        game.playCard("Bob", new NumberedCard("Green", "3"));

        assertEqualsPitCardColorAndNumber("3", game, "Green");
        assertEquals(bobHand + 1, game.playerHands.get("Bob").size());
    }

    @Test void test11StealCompatibleCard() {
        UnoGame game = validUnoGameTwoPlayers();
        game.stealACard("Alice"); //roba carta, como es compatible la juega, pasa turno
        game.playCard("Bob", new NumberedCard("Blue", "1"));

        assertEqualsPitCardColorAndNumber("1", game, "Blue");
    }

    @Test void test12WildCardChangesCurrentPitColor() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playCard("Alice", new WildCard().chooseColor("Blue"));
        game.playCard("Bob", new NumberedCard("Blue", "1"));

        assertEqualsPitCardColorAndNumber("1", game, "Blue");
    }

    @Test void test13StealIncompatibleCard() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playCard("Alice", new WildCard().chooseColor("Yellow"));
        int bobHandBeforeStealing = game.playerHands.get("Bob").size();
        game.stealACard("Bob");
        game.playCard("Alice", new NumberedCard("Yellow", "1"));

        assertEquals(bobHandBeforeStealing+1, game.playerHands.get("Bob").size());
    }

    @Test void test14ReverseCardChangesTurnRotationDirection() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playCard("Alice", new ReverseCard("Green"));
        game.playCard("Bob", new NumberedCard("Green", "3"));
        //Segun las reglas, la carta Reverse cambia el sentido de la rotación de los turnos
        //Por lo tanto si el orden es Alice, Bob, la carta Reverse hace que el orden sea Bob, Alice
        //Como son dos jugadores la carta Reverse no tiene ningún impacto real en el funcionamiento del juego.
        // (Ejemplo: A,B,A,B son los turnos, si B juega Reverse, siempre le va a tocar a A.

        assertEqualsPitCardColorAndNumber("3", game, "Green");
    }

    @Test void test15PlayerStealsACompatibleDrawTwoCardAndEffectIsExecuted() {
        UnoGame game = validUnoGameTwoPlayers();
        game.stealACard("Alice");
        int aliceHandBeforeDrawTwo = game.playerHands.get("Alice").size();
        game.stealACard("Bob");

        assertEqualsPitCardColorAndNumber("Draw two", game, "Green");
        assertEquals(aliceHandBeforeDrawTwo + 2, game.playerHands.get("Alice").size());
    }

    @Test void test16ReverseCardWith3Players() {
        UnoGame game = unoGameWithThreePlayers();
        game.playCard("Alice", new ReverseCard("Green"));//el orden es Alice, Bob, Charlie
        game.playCard("Charlie", new NumberedCard("Green", "4"));//el orden es Charlie, Bob, Alice por el reverse

        assertEqualsPitCardColorAndNumber("4", game, "Green");
    }

    @Test void test17InvalidColorForWildCard() {
        assertThrowsLike("Invalid color",
                ()-> validUnoGameTwoPlayers().playCard("Alice", new WildCard().chooseColor("Pink")));
    }

    @Test void test18HandWith3Players() {
        UnoGame game = unoGameWithThreePlayers();
        game.playCard("Alice", new NumberedCard("Green", "1") );
        game.playCard("Bob", new NumberedCard("Green", "6"));
        game.playCard("Charlie", new NumberedCard("Green", "4"));
        game.playCard("Alice", new DrawTwoCard("Green"));
        game.playCard("Bob", new NumberedCard("Green", "1"));
        game.playCard("Charlie", new NumberedCard("Yellow", "1"));
        game.playCard("Alice", new WildCard().chooseColor("Blue"));
        game.stealACard("Bob");
        game.playCard("Charlie", new ReverseCard("Blue"));
        game.playCard("Bob", new SkipCard("Blue"));
        game.playCard("Charlie", new NumberedCard("Blue", "0"));

        assertEqualsPitCardColorAndNumber("0", game, "Blue");

    }

    @Test void test18PlayerPlaysLastCardAndWins() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playerHands.get("Alice").clear();
        game.playerHands.get("Alice").add(new NumberedCard("Green", "1"));
        game.playCard("Alice", new NumberedCard("Green", "1").shoutUno());

        assertThrowsLike("Game over!",
                () -> game.playCard("Bob", new NumberedCard("Green", "4")));
    }

    @Test void test19NotShoutingUnoPenalisation() {
        UnoGame game = validUnoGameTwoPlayers();
        game.playerHands.get("Alice").clear();
        game.playerHands.get("Alice").add(new NumberedCard("Green", "1"));
        game.playerHands.get("Alice").add(new NumberedCard("Green", "2"));
        game.playCard("Alice", new NumberedCard("Green", "1"));

        assertEquals(3, game.playerHands.get("Alice").size());

    }








    private UnoGame unoGameWithThreePlayers() {
        return new UnoGame(deckForThree, "Alice", "Bob", "Charlie");
    }

    private UnoGame validUnoGameTwoPlayers() {
        return new UnoGame(deckForTwo, "Alice", "Bob");
    }
    private static void alicePlays0Blue(UnoGame game) {
        game.playCard("Alice", new NumberedCard("Blue", "0"));
    }

    private static void bobPlays0Red(UnoGame game) {
        game.playCard("Bob", new NumberedCard("Red", "0"));
    }
    private void assertThrowsLike( String message, Executable codeBlock ) {
        assertEquals( message, assertThrows( Throwable.class, codeBlock ). getMessage() );
    }

    private static void assertEqualsPitCardColorAndNumber(String name, UnoGame game, String color) {
        assertEquals(name, game.pitCard().name());
        assertEquals(color, game.pitCard().color);
    }

}
