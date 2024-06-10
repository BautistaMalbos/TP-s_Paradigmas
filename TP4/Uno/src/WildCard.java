public class WildCard extends Card {
    private String chosenColor;
    public WildCard(String chosenColor){
        super("Wild");
        this.chosenColor = chosenColor;
    }

    public String name() {
        return "Wild";
    }

    public void chooseColor() {
        this.color = chosenColor;
    }

    public boolean isWildCard() {
        return true;
    }
}
