package pt.ulusofona.lp2.deisiGreatGame;


import java.io.Serializable;

public class Ferramenta implements Serializable {
    int id;
    String titulo;
    int posicao;

    Ferramenta(int id, int posicao) {
        this.titulo = titulo;
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

    @Override
    public String toString() {
        return titulo;
    }
}

