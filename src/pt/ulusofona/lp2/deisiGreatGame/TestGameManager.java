package pt.ulusofona.lp2.deisiGreatGame;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class TestGameManager {
    GameManager gameManagerTestes = new GameManager();

    @Test
    public void test01_AtivaFerramentas() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        String[][] ferramentasEAbismo = {{"1", "4", "2"}, {"1", "2", "4"}, {"1", "3", "6"}, {"0", "0", "9"}, {"0", "1", "10"}, {"0", "2", "13"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 20, ferramentasEAbismo);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(1);
        String mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(3);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(5);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(8);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals(2, gameManagerTestes.programadores.get(0).getPosicao());
        movimento = gameManagerTestes.moveCurrentPlayer(6);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals(10, gameManagerTestes.programadores.get(1).getPosicao());
        movimento = gameManagerTestes.moveCurrentPlayer(7);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals(6, gameManagerTestes.programadores.get(2).getPosicao());
    }

    @Test
    public void test02_SemAjuda() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}};
        String[][] ferramentasEAbismo = {{"0", "4", "14"}, {"0", "9", "16"}, {"0", "7", "17"}, {"0", "8", "19"}};

        boolean iniciar = gameManagerTestes.createInitialBoard(info, 25, ferramentasEAbismo);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(3);
        String mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(4);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(4);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(4);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(5);
        mensagem = gameManagerTestes.reactToAbyssOrTool();

        movimento = gameManagerTestes.moveCurrentPlayer(5);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals(1, gameManagerTestes.programadores.get(1).getPosicao());
        movimento = gameManagerTestes.moveCurrentPlayer(4);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(2);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals(17, gameManagerTestes.programadores.get(0).getPosicao());
        movimento = gameManagerTestes.moveCurrentPlayer(4);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals("Derrotado", gameManagerTestes.programadores.get(0).getEstado());
    }

    @Test
    public void test03_2Ferramentas() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Purple"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Brown"}};
        String[][] ferramentasEAbismo = {{"1", "0", "3"}, {"1", "0", "7"}, {"1", "4", "12"}};

        boolean iniciar = gameManagerTestes.createInitialBoard(info, 20, ferramentasEAbismo);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(2);
        String mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(4);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(4);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals(1, gameManagerTestes.programadores.get(0).getFerramentas().size());
        movimento = gameManagerTestes.moveCurrentPlayer(6);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(5);
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals(2, gameManagerTestes.programadores.get(0).getFerramentas().size());
    }

    @Test
    public void test04_CicloInfinito() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Purple"}};
        String[][] ferramentasEAbismo = {{"1", "0", "3"}, {"1", "4", "12"}, {"0", "8", "19"}};

        boolean iniciar = gameManagerTestes.createInitialBoard(info, 30, ferramentasEAbismo);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(2); //3
        String mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(5); //6
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(6); // 9
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(6); // 12
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(6); // 15
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(3); //15
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        movimento = gameManagerTestes.moveCurrentPlayer(4); //19
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals(true, gameManagerTestes.programadores.get(0).getValorPreso());
        movimento = gameManagerTestes.moveCurrentPlayer(4); //19
        mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals(false, gameManagerTestes.programadores.get(0).getValorPreso());
        assertEquals(true, gameManagerTestes.programadores.get(1).getValorPreso());
        assertEquals(22001757, gameManagerTestes.getCurrentPlayerID()); // testa para ver se esta a ir buscar bem o jogador
        assertEquals("Tiago Águeda : Herança | João Antas : IDE", gameManagerTestes.getProgrammersInfo()); // ve se as informações do jogador estão corretas
    }

}