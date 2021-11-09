package pt.ulusofona.lp2.deisiGreatGame;

public enum ProgrammerColor {
    PURPLE("playerPurple.png"), BLUE("playerBlue.png"), GREEN("playerGreen.png"), BROWN("playerBrown.png");

    String cor;

    ProgrammerColor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return cor;
    }

}