package pt.ulusofona.lp2.deisiGreatGame;

public class Abismo {
    int id;
    String titulo;
    int posicao;

    Abismo(String titulo, int id, int posicao) {
        this.titulo = titulo;
        this.id = id;
        this.posicao = posicao;
    }

    public int getPosicao(){
        return posicao;
    }

    public int getId(){
        return id;
    }

    public String getTitulo(){
        return titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
