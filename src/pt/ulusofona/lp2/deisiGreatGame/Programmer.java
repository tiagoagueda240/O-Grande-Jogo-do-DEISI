package pt.ulusofona.lp2.deisiGreatGame;

import javax.sql.rowset.serial.SerialBlob;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Programmer implements Serializable {
    String name;
    List<String> languages = new ArrayList();
    int iD;
    ProgrammerColor colorAvatar;
    int posicao = 1; // primeira posição do tabuleiro
    String estado = "Em Jogo"; // se o jogador está em jogo ou não
    ArrayList<String> ferramentas = new ArrayList();
    boolean preso = false;
    ArrayList<Integer> historicoPosicoes = new ArrayList<>();
    ArrayList<Integer> posicoes = new ArrayList<>();
    ArrayList<String> historicoAbismos = new ArrayList<>();

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

    public void alteraValorPreso(boolean valor) {
        preso = valor;
    }

    public boolean getValorPreso() {
        return preso;
    }

    public void adicionaPosicao(int posicao) {
        historicoPosicoes.add(posicao);
    }

    public void posicao(int posicao) {
        posicoes.add(posicao);
    }

    public void saberPosicaoJogadas(int quantidade) {
        if (historicoPosicoes.size() - quantidade >= 0) {
            posicao = historicoPosicoes.get(historicoPosicoes.size() - quantidade);
        } else {
            posicao = 1;
        }
    }

    public void adicionaListaAbismos(String titulo) {
        historicoAbismos.add(titulo);
    }

    public void posicaoInicial() {
        posicao = 1;
    }

    public void perdeu() {
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

    public List<String> getLinguagens() {
        return languages;
    }

    public ProgrammerColor getColor() {
        return colorAvatar;
    }

    public int getPosicao() {
        return posicao;
    }

    public ArrayList<String> getFerramentas() {
        return ferramentas;
    }

    public ArrayList<Integer> getHistoricoPosicoes() {
        return historicoPosicoes;
    }

    public void mover(int posicoes) {
        posicao += posicoes;
    }

    public void recuar(int posicoes) {
        if (posicao - posicoes < 0) {
            posicao = 0;
        } else {
            posicao -= posicoes;
        }
    }

    public void colocaPosicaoMedia() {
        int media = 0;
        int counter = 0;
        for (int i = historicoPosicoes.size() - 1; i >= 0 && counter <= 3; i--) {
            media += historicoPosicoes.get(i);
            counter++;
        }

        int tamanho = 0;
        if (historicoPosicoes.size() > 3) {
            tamanho = 3;
        } else {
            tamanho = historicoPosicoes.size();
        }
        // Arredondamento
        if ((media / tamanho) % 1 == 0) {
            posicao = (int) Math.ceil(media / tamanho);
        }else{
            posicao = media / tamanho;
        }
    }

    public int saberAvancarRecuar(int posicoes, int nrCasas) {
        return nrCasas + (nrCasas - posicao - posicoes);
    }

    public void avancarRecuar(int posicoes, int nrCasas) {
        posicao = nrCasas + (nrCasas - posicao - posicoes);
    }

    public void addFerramenta(String ferramenta) {
        boolean verificaExistencia = true;
        for (String ferramentaProgramador : ferramentas) {
            if (ferramentaProgramador.equals(ferramenta)) {
                verificaExistencia = false;
                break;
            }
        }
        if (verificaExistencia) {
            ferramentas.add(ferramenta);
        }
    }

    public boolean contemFerramentaUtil(HashSet<String> lista) {
        for (String ferramenta : lista) {
            int posicao = 0;
            for (String ferramentaPlayer : ferramentas) {
                if (ferramenta.equals(ferramentaPlayer)) {
                    ferramentas.remove(posicao); // remove a ferramenta
                    return true;
                }
                posicao++;
            }
        }
        return false;
    }

    public String criaListaFerramentas() {
        StringBuilder listaFerramentas = new StringBuilder();
        if (ferramentas.size() == 0) {
            return "No tools";
        } else {
            for (String ferramenta : ferramentas) {
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
        if (colorAvatar.toString().equals("Blue") && criaListaFerramentas().contains("IDE")) {
            return name + " : Blue Is IDE";
        } else {
            return iD + " | " + name + " | " + posicao + " | " + criaListaFerramentas() + " | " + listaLinguas + " | " + estado;
        }
    }
}