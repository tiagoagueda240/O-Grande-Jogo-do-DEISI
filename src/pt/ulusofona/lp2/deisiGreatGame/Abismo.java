package pt.ulusofona.lp2.deisiGreatGame;

import java.io.Serializable;
import java.util.HashSet;

public class Abismo implements Serializable {
    int id;
    String titulo;
    int posicao;
    HashSet<String> listaFerramentas = new HashSet<>();

    Abismo(int id, int posicao) {
        this.id = id;
        this.posicao = posicao;
    }

    public int getPosicao() {
        return posicao;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public HashSet<String> getFerramentas() {
        return listaFerramentas;
    }

    @Override
    public String toString() {
        return titulo;
    }
}