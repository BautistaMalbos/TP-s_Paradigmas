public class NumberedCard extends Card {
    private String number;

    public NumberedCard(String color, String number){
        super(color);
        this.number = number;
    }

    public String number(){
        return number;
    }
    public String type(){
        return "Numbered";
    }


}
