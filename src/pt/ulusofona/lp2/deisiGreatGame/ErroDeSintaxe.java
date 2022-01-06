package pt.ulusofona.lp2.deisiGreatGame;

public class ErroDeSintaxe extends Abismo {
    ErroDeSintaxe(int id, int posicao) {
        super(id, posicao);
        titulo = "Erro de sintaxe";
        listaFerramentas.add("Ajuda Do Professor");
        listaFerramentas.add("IDE");
    }
}
