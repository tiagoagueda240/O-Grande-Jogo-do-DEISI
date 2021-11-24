package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Programmer {

    String name;
    ArrayList<String> languages = new ArrayList();
    int iD;
    ProgrammerColor colorAvatar;
    int posicao = 1; // primeira posição do tabuleiro
    String estado = "Em Jogo"; // se o jogador está em jogo ou não
    ArrayList<String> ferramentas = new ArrayList();

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

    public void posicaoInicial(){
        posicao = 1;
    }
    public void perdeu(){
        estado = "Derrotado";
    }
    public int getId() {
        return iD;
    }

    public String getName() {
        return name;
    }
    public String getEstado() {
        return estado;
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
    public void recuar(int posicoes) {
        if (posicao - posicoes < 0){
            posicao = 0;
        }else{
            posicao -= posicoes;
        }

    }

    public void avancarRecuar(int posicoes, int nrCasas) {
        posicao = nrCasas + (nrCasas - posicao - posicoes);
    }
    public void addFerramenta(String ferramenta){
        ferramentas.add(ferramenta);
    }

    public boolean contemFerramentaUtil(HashSet<String> lista){
        for (String ferramenta : lista){
            int posicao = 0;
            for (String ferramentaPlayer : ferramentas){
                if(ferramenta.equals(ferramentaPlayer)){
                    ferramentas.remove(posicao);
                    return true;
                }
                posicao++;
            }
        }
        return false;
    }

    public String criaListaFerramentas(){
        StringBuilder listaFerramentas = new StringBuilder();
        if (ferramentas.size() == 0){
            return "No tools";
        }else{
            for (String ferramenta : ferramentas){
                listaFerramentas.append(ferramenta);
                if (ferramenta != ferramentas.get(ferramentas.size() - 1)) {
                    listaFerramentas.append(";");
                }
            }
            return listaFerramentas.toString();
        }
    }
    /*
        public void ativaAbismo(int id){
            HashSet<String> listaFerramentas = new HashSet();
            for (String ferramenta: ferramentas){
                listaFerramentas.add(ferramenta);
            }
            switch (id){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
            }
        }
    */
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
        if (ferramentas.size() == 0){
            return iD + " | " + name + " | " + posicao + " | " + criaListaFerramentas() + " | " + listaLinguas + " | " + estado;
        }else{
            return iD + " | " + name + " | " + posicao + " | " + criaListaFerramentas() + " | " + listaLinguas + " | " + estado;
        }

    }
}