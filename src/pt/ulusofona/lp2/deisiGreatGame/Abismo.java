package pt.ulusofona.lp2.deisiGreatGame;

import java.util.HashSet;

public class Abismo {
    int id;
    String titulo;
    int posicao;
    HashSet<String> listaFerramentas = new HashSet<>();

    Abismo(String titulo, int id, int posicao) {
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

class ErroDeSintaxe extends Abismo {
    ErroDeSintaxe(String titulo, int id, int posicao) {
        super(titulo, id, posicao);
        listaFerramentas.add("Ajuda Do Professor");
        listaFerramentas.add("IDE");
    }
}

class ErroDeLogica extends Abismo {
    ErroDeLogica(String titulo, int id, int posicao) {
        super(titulo, id, posicao);
        listaFerramentas.add("Testes unitários");
        listaFerramentas.add("Ajuda Do Professor");
    }
}

class Exception extends Abismo {
    Exception(String titulo, int id, int posicao) {
        super(titulo, id, posicao);
        listaFerramentas.add("Ajuda Do Professor");
        listaFerramentas.add("Tratamento de Excepções");
    }
}

class FileNotFoundException extends Abismo {
    FileNotFoundException(String titulo, int id, int posicao) {
        super(titulo, id, posicao);
        listaFerramentas.add("Ajuda Do Professor");
        listaFerramentas.add("Tratamento de Excepções");
    }
}

class Crash extends Abismo {
    Crash(String titulo, int id, int posicao) {
        super(titulo, id, posicao);
    }
}

class DuplicatedCode extends Abismo {
    DuplicatedCode(String titulo, int id, int posicao) {
        super(titulo, id, posicao);
        listaFerramentas.add("Herança");
    }
}

class EfeitosSecundarios extends Abismo {
    EfeitosSecundarios(String titulo, int id, int posicao) {
        super(titulo, id, posicao);
        listaFerramentas.add("Programação Funcional");
    }
}

class BlueScreenOfDeath extends Abismo {
    BlueScreenOfDeath(String titulo, int id, int posicao) {
        super(titulo, id, posicao);

    }
}

class CicloInfinito extends Abismo {
    CicloInfinito(String titulo, int id, int posicao) {
        super(titulo, id, posicao);
        listaFerramentas.add("Programação Funcional");
    }
}

class SegmentationFault extends Abismo {
    SegmentationFault(String titulo, int id, int posicao) {
        super(titulo, id, posicao);
    }
}
