package pt.ulusofona.lp2.deisiGreatGame;

public class ErroDeLogica extends Abismo {
    ErroDeLogica(int id, int posicao) {
        super(id, posicao);
        titulo = "Erro de lógica";
        listaFerramentas.add("Testes unitários");
        listaFerramentas.add("Ajuda Do Professor");
    }
}
