package pt.ulusofona.lp2.deisiGreatGame;

public enum ProgrammerColor {
    PURPLE("purplePlayer.png"), BLUE("bluePlayer.png"), GREEN("greenPlayer.png"), BROWN("brownPlayer.png");

    String cor;

    ProgrammerColor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return cor;
    }

}