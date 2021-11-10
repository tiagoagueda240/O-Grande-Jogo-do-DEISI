package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

public class Programmer {

    String name;
    ArrayList<String> languages = new ArrayList();
    int iD;
    ProgrammerColor colorAvatar;
    int posicao = 1; // primeira posição do tabuleiro
    String estado = "Em Jogo"; // se o jogador está em jogo ou não

    public Programmer(String name, ArrayList<String> languages, int iD, ProgrammerColor colorAvatar, int posicao, String estado) {
        this.name = name;
        this.languages = languages;
        this.iD = iD;
        this.colorAvatar = colorAvatar;
        this.posicao = posicao;
        this.estado = estado;
    }

    public Programmer() {
    }

    public int getId() {
        return iD;
    }

    public String getName() {
        return name;
    }

    public ProgrammerColor getColor() {
        return colorAvatar;
    }

    public int getPosicao() {
        return posicao;
    }

    public void mover(int posicoes) {
        posicao += posicoes;
    }

    public void recuar(int posicoes, int nrCasas) {
        posicao = nrCasas - (nrCasas - posicao - posicoes);
    }

    @Override
    public String toString() {
        StringBuilder listaLinguas = new StringBuilder();
        Collections.sort(languages);
        for (String lingua : languages) {
            listaLinguas.append(lingua);
            if (lingua != languages.get(languages.size() - 1)) {
                listaLinguas.append("; ");
            }
        }
        return iD + " | " + name + " | " + posicao + " | " + listaLinguas + " | " + estado;
    }
}