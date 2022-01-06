package pt.ulusofona.lp2.deisiGreatGame;

public class FileNotFoundException extends Abismo {
    FileNotFoundException(int id, int posicao) {
        super(id, posicao);
        titulo = "File Not Found Exception";
        listaFerramentas.add("Ajuda Do Professor");
        listaFerramentas.add("Tratamento de Excepções");
    }
}
