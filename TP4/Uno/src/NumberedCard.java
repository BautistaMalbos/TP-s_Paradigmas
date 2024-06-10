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

