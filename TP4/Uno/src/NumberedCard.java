public class NumberedCard extends Card {
    private String name;

    public NumberedCard(String color, String number){
        super(color);
        this.name = number;
    }

    public String name(){
        return name;
    }


}

//{deck = new ArrayList<>(Arrays.asList(
//        //                 MAZO DESPUES DE REPARTIR 3 PARA CADA UNO Y 1 AL PIT
//                new NumberedCard("Green", "2"), new NumberedCard("Green", "1"), new NumberedCard("Blue", "0"),
//
////               CARTAS DE CHARLIE                CARTAS DE BOB                          CARTAS DE ALICE
//
//                new NumberedCard("Red", "0"), new NumberedCard("Blue", "1"), new NumberedCard("Red", "1"),
//                new NumberedCard("Blue", "2"), new NumberedCard("Red", "2"), new NumberedCard("Blue", "3"),
//                new NumberedCard("Red", "3"), new NumberedCard("Blue", "4"), new SkipCard("Green"),
//                            new NumberedCard("Green", "0")
//        ));               //                          PIT CARD
//pit = new ArrayList<>();
//        }
