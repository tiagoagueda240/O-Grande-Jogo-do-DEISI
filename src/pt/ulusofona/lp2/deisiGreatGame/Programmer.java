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
    boolean estado; // se o jogador está em jogo ou não

    public Programmer(String name, ArrayList<String> languages, int iD, ProgrammerColor colorAvatar, int posicao, boolean estado) {
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

    String getName() {
        return name;
    }

    ProgrammerColor getColor() {
        return colorAvatar;
    }

    public int getPosicao(){
        return posicao;
    }

    public void mover(int posicoes){
        posicao += posicoes;
    }

    public void recua(int posicoes, int nrCasas) {
        posicao = nrCasas - (nrCasas - posicao - posicoes);
    }

    public boolean verificaEstado(){ return estado;}
    /**
     public ArrayList<String> setLanguages(String listaLinguas, int posicao){
     Collections.addAll(languages, listaLinguas.split(";"));
     return languages;
     }
     */

    public boolean verificaProgramador(HashSet<Integer> idDuplicado, HashSet<ProgrammerColor> colorDuplicado) {
        if (!idDuplicado.add(iD)) {
            return false;
        }
        if (name == null && Objects.equals(getName(), "")) {
            return false;
        }
        if (colorAvatar == null && !colorDuplicado.add(colorAvatar)) {
            return false; //verificar se é uma das cores do enum
        }
        return true;
    }
    @Override
    public String toString() {
        return "Programmer{" +
                "name='" + name + '\'' +
                ", languages=" + languages +
                ", iD=" + iD +
                ", colorAvatar=" + colorAvatar +
                ", casaPartida=" + posicao +
                ", estado='" + estado + '\'' +
                '}';
    }
}