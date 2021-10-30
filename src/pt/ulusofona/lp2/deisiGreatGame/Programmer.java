package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;

public class Programmer {

    String name;
    ArrayList<String> languages = new ArrayList();
    int iD;
    ProgrammerColor colorAvatar;
    int casaPartida = 1; // primeira posição do tabuleiro
    String estado; // se o jogador está em jogo ou não

    public Programmer(String name, ArrayList<String> languages, int iD, ProgrammerColor colorAvatar, int casaPartida, String estado) {
        this.name = name;
        this.languages = languages;
        this.iD = iD;
        this.colorAvatar = colorAvatar;
        this.casaPartida = casaPartida;
        this.estado = estado;
    }

    public Programmer() {
    }

    int getId() {
        return iD;
    }

    String getName() {
        return name;
    }

    ProgrammerColor getColor() {
        return colorAvatar;
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "name='" + name + '\'' +
                ", languages=" + languages +
                ", iD=" + iD +
                ", colorAvatar=" + colorAvatar +
                ", casaPartida=" + casaPartida +
                ", estado='" + estado + '\'' +
                '}';
    }
}
