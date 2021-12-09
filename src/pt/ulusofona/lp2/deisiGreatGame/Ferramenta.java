package pt.ulusofona.lp2.deisiGreatGame;


public class Ferramenta {
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

class Heranca extends Ferramenta {
    Heranca(int id, int posicao) {
        super(id, posicao);
        titulo = "Herança";
    }
}

class ProgramacaoFuncional extends Ferramenta {
    ProgramacaoFuncional(int id, int posicao) {
        super(id, posicao);
        titulo = "Programação Funcional";
    }
}

class TestesUnitarios extends Ferramenta {
    TestesUnitarios(int id, int posicao) {
        super(id, posicao);
        titulo = "Testes unitários";
    }
}

class TratamentoDeExcepcoes extends Ferramenta {
    TratamentoDeExcepcoes(int id, int posicao) {
        super(id, posicao);
        titulo = "Tratamento de Excepções";
    }
}

class IDE extends Ferramenta {
    IDE(int id, int posicao) {
        super(id, posicao);
        titulo = "IDE";
    }
}

class AjudaDoProfessor extends Ferramenta {
    AjudaDoProfessor(int id, int posicao) {
        super(id, posicao);
        titulo = "Ajuda Do Professor";
    }
}
