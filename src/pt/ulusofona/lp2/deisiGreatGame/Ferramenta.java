package pt.ulusofona.lp2.deisiGreatGame;

public class Ferramenta {
    int id;
    String titulo;
    int posicao;

    Ferramenta(String titulo, int id, int posicao) {
        this.titulo = titulo;
        this.id = id;
        this.posicao = posicao;

    }

    public int getPosicao(){
        return posicao;
    }

    public String getTitulo(){
        return titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }
}