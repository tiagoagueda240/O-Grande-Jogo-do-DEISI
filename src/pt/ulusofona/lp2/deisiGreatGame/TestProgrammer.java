package pt.ulusofona.lp2.deisiGreatGame;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestProgrammer {
    GameManager gameManagerTestes = new GameManager();

    @Test
    public void test01_criarJogador() throws InvalidInitialBoardException {
        Programmer programador = new Programmer();
        ArrayList<String> languages = new ArrayList(Arrays.asList("Java; C; Kotlin".split(";")));
        Programmer programador2 = new Programmer("Tiago Águeda", languages, Integer.parseInt("22001757"), ProgrammerColor.PURPLE, 1, "Em Jogo");
        programador2.addFerramenta("Herança");
        programador2.addFerramenta("Programação Funcional");
        programador2.addFerramenta("Testes unitários");
        assertEquals("Herança;Programação Funcional;Testes unitários", programador2.criaListaFerramentas());
        assertEquals("22001757 | Tiago Águeda | 1 | Herança;Programação Funcional;Testes unitários |  C;  Kotlin; Java | Em Jogo", programador2.toString());
        programador.saberPosicaoJogadas(2);
        programador.recuar(3);
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        try {
            gameManagerTestes.createInitialBoard(info, 0);
        } catch (InvalidInitialBoardException erro) {
            assertEquals("Tamanho do tabuleiro incorreto", erro.getMessage());

        }
    }
}