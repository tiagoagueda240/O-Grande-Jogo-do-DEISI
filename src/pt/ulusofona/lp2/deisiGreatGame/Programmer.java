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
    boolean preso = false;
    ArrayList<Integer> historicoPosicoes = new ArrayList<>();

    protected Programmer(String name, ArrayList<String> languages, int iD, ProgrammerColor colorAvatar, int posicao, String estado) {
        this.name = name;
        this.languages = languages;
        this.iD = iD;
        this.colorAvatar = colorAvatar;
        this.posicao = posicao;
        this.estado = estado;
    }

    protected Programmer() {
    }

    protected void alteraValorPreso(boolean valor){
        preso = valor;
    }

    protected boolean getValorPreso(){
        return preso;
    }

    protected void adicionaPosicao(int posicao){
        historicoPosicoes.add(posicao);
    }

    protected void saberPosicaoJogadas(int quantidade){
        if (historicoPosicoes.size() - quantidade >= 0){
            posicao = historicoPosicoes.get(historicoPosicoes.size() - quantidade);
        }else{
            posicao = 1;
        }
    }

    protected void posicaoInicial(){
        posicao = 1;
    }

    protected void perdeu(){
        estado = "Derrotado";
    }

    protected int getId() {
        return iD;
    }

    protected String getName() {
        return name;
    }

    protected String getEstado() {
        return estado;
    }

    protected ProgrammerColor getColor() {
        return colorAvatar;
    }

    protected int getPosicao() {
        return posicao;
    }

    protected  ArrayList<String> getFerramentas() {
        return ferramentas;
    }

    protected void mover(int posicoes) {
        posicao += posicoes;
    }

    protected void recuar(int posicoes) {
        if (posicao - posicoes < 0){
            posicao = 0;
        }else{
            posicao -= posicoes;
        }

    }

    protected void avancarRecuar(int posicoes, int nrCasas) {
        posicao = nrCasas + (nrCasas - posicao - posicoes);
    }

    protected void addFerramenta(String ferramenta){
        boolean verificaExistencia = true;
        for (String ferramentaProgramador : ferramentas){
            if (ferramentaProgramador.equals(ferramenta)) {
                verificaExistencia = false;
                break;
            }
        }
        if (verificaExistencia){
            ferramentas.add(ferramenta);
        }
    }

    protected boolean contemFerramentaUtil(HashSet<String> lista){
        for (String ferramenta : lista){
            int posicao = 0;
            for (String ferramentaPlayer : ferramentas){
                if(ferramenta.equals(ferramentaPlayer)){
                    ferramentas.remove(posicao); // remove a ferramenta
                    return true;
                }
                posicao++;
            }
        }
        return false;
    }

    protected String criaListaFerramentas(){
        StringBuilder listaFerramentas = new StringBuilder();
        if (ferramentas.size() == 0){
            return "No tools";
        }else{
            for (String ferramenta : ferramentas){
                listaFerramentas.append(ferramenta);
                if (!ferramenta.equals(ferramentas.get(ferramentas.size() - 1))) {
                    listaFerramentas.append(";");
                }
            }
            return listaFerramentas.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder listaLinguas = new StringBuilder();
        Collections.sort(languages);
        for (String lingua : languages) {
            listaLinguas.append(lingua);
            if (!lingua.equals(languages.get(languages.size() - 1))) {
                listaLinguas.append("; ");
            }
        }
        return iD + " | " + name + " | " + posicao + " | " + criaListaFerramentas() + " | " + listaLinguas + " | " + estado;
    }
}
