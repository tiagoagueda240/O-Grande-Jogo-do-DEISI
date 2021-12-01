package pt.ulusofona.lp2.deisiGreatGame;

import java.util.HashSet;

public class Abismo {
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

    public HashSet<String> getFerramentas(){
        return listaFerramentas;
    }

    @Override
    public String toString() {
        return titulo;
    }
}

class ErroDeSintaxe extends Abismo {
    ErroDeSintaxe(int id, int posicao) {
        super(id, posicao);
        titulo = "Erro de sintaxe";
        listaFerramentas.add("Ajuda Do Professor");
        listaFerramentas.add("IDE");
    }
}

class ErroDeLogica extends Abismo {
    ErroDeLogica(int id, int posicao) {
        super(id, posicao);
        titulo = "Erro de lógica";
        listaFerramentas.add("Testes unitários");
        listaFerramentas.add("Ajuda Do Professor");
    }
}

class Exception extends Abismo {
    Exception(int id, int posicao) {
        super(id, posicao);
        titulo = "Exception";
        listaFerramentas.add("Ajuda Do Professor");
        listaFerramentas.add("Tratamento de Excepções");
    }
}

class FileNotFoundException extends Abismo {
    FileNotFoundException(int id, int posicao) {
        super(id, posicao);
        titulo = "File Not Found Exception";
        listaFerramentas.add("Ajuda Do Professor");
        listaFerramentas.add("Tratamento de Excepções");
    }
}

class Crash extends Abismo {
    Crash(int id, int posicao) {
        super(id, posicao);
        titulo = "Crash (aka Rebentanço)";
    }
}

class DuplicatedCode extends Abismo {
    DuplicatedCode(int id, int posicao) {
        super(id, posicao);
        titulo = "Duplicated Code";
        listaFerramentas.add("Herança");
    }
}

class EfeitosSecundarios extends Abismo {
    EfeitosSecundarios(int id, int posicao) {
        super(id, posicao);
        titulo = "Efeitos secundários";
        listaFerramentas.add("Programação Funcional");
    }
}

class BlueScreenOfDeath extends Abismo {
    BlueScreenOfDeath(int id, int posicao) {
        super(id, posicao);
        titulo = "Blue Screen of Death";

    }
}

class CicloInfinito extends Abismo {
    CicloInfinito(int id, int posicao) {
        super(id, posicao);
        titulo = "Ciclo infinito";
        listaFerramentas.add("Programação Funcional");
    }
}

class SegmentationFault extends Abismo {
    SegmentationFault(int id, int posicao) {
        super(id, posicao);
        titulo = "Segmentation Fault";
    }
}