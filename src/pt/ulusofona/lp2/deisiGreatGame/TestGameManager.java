package pt.ulusofona.lp2.deisiGreatGame;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.List;

public class TestGameManager {
    GameManager gameManagerTestes = new GameManager();

    @Test
    public void test02_tamanhoTabuleiro01() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 0);
        assertEquals(false, iniciar);
    }

    @Test
    public void test02_tamanhoTabuleiro02() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 2);
        assertEquals(false, iniciar);
    }

    @Test
    public void test02_ferramentaInvalida() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        String[][] ferramentasEAbismo = {{"1", "10", "2"}, {"1", "2", "4"}, {"1", "3", "6"}, {"0", "0", "9"}, {"0", "1", "10"}, {"0", "2", "13"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 15, ferramentasEAbismo);
        assertEquals(false, iniciar);
    }

    @Test
    public void test02_abismoInvalida() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        String[][] ferramentasEAbismo = {{"1", "4", "2"}, {"1", "2", "4"}, {"1", "3", "6"}, {"0", "10", "9"}, {"0", "1", "10"}, {"0", "2", "13"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 30, ferramentasEAbismo);
        assertEquals(false, iniciar);
    }

    @Test
    public void test02_idAbismoOuFerramenta() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        String[][] ferramentasEAbismo = {{"1", "4", "2"}, {"1", "2", "4"}, {"1", "3", "6"}, {"5", "4", "9"}, {"0", "1", "10"}, {"0", "2", "13"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 30, ferramentasEAbismo);
        assertEquals(false, iniciar);
    }

    @Test
    public void test02_corInvalida() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Yellow"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 20);
        assertEquals(false, iniciar);
    }

    @Test
    public void test02_limiteJogador() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}};
        String[][] ferramentasEAbismo = {{"1", "1", "2"}, {"1", "5", "4"}, {"1", "3", "6"}, {"5", "4", "9"}, {"0", "6", "10"}, {"0", "2", "13"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 20, ferramentasEAbismo);
        assertEquals(false, iniciar);
    }

    @Test
    public void test02_informacaoJogadores() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Yellow"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        String[][] ferramentasEAbismo = {{"0", "7", "2"}, {"0", "3", "10"}, {"0", "5", "13"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 20);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(1);
        String mensagem = gameManagerTestes.reactToAbyssOrTool();
        List<Programmer> infoJogadores = gameManagerTestes.getProgrammers(false); //verificar
        assertEquals("22001757 | Tiago Águeda | 2 | No tools | Java, C, Kotlin | Em Jogo", infoJogadores.get(0).toString());
        infoJogadores = gameManagerTestes.getProgrammers(true);
        assertEquals("22001757 | Tiago Águeda | 2 | No tools | Java, C, Kotlin | Em Jogo", infoJogadores.get(0).toString());
    }

    @Test
    public void test02_jogadoresEmPosicao() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Yellow"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        String[][] ferramentasEAbismo = {{"0", "5", "2"}, {"0", "1", "10"}, {"0", "2", "13"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 20);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(1);
        String mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals("22001757 | Tiago Águeda | 2 | No tools | Java, C, Kotlin | Em Jogo", gameManagerTestes.getProgrammers(2).get(0).toString());
        assertEquals(null, gameManagerTestes.getProgrammers(30));
    }

    @Test
    public void test02_moverIdIvalido() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Yellow"}, {"19999639", "Camelo Cabral", "Python, C++", "Purple"}};
        String[][] ferramentasEAbismo = {{"0", "5", "2"}, {"0", "1", "10"}, {"0", "2", "13"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 20);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(1);
        String mensagem = gameManagerTestes.reactToAbyssOrTool();
        assertEquals("22001757 | Tiago Águeda | 2 | No tools | Java, C, Kotlin | Em Jogo", gameManagerTestes.getProgrammers(2).get(0).toString());
        assertEquals(null, gameManagerTestes.getProgrammers(30));
    }

    @Test
    public void test02_imagemFerramentaAbismo() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}};
        String[][] ferramentasEAbismo = {{"1", "4", "2"}, {"1", "2", "4"}, {"1", "3", "6"}, {"0", "0", "9"}, {"0", "1", "10"}, {"0", "2", "13"}};

        boolean iniciar = gameManagerTestes.createInitialBoard(info, 25, ferramentasEAbismo);

        assertEquals("abismos/abismo2.png", gameManagerTestes.getImagePng(13));
        assertEquals("ferramentas/ferramenta4.png", gameManagerTestes.getImagePng(2));
        assertEquals(null, gameManagerTestes.getImagePng(26));
    }

    @Test
    public void test02_getTitulo() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Green"}};
        String[][] ferramentasEAbismo = {{"1", "4", "2"}, {"1", "2", "4"}, {"1", "3", "6"}, {"0", "0", "9"}, {"0", "1", "10"}, {"0", "2", "13"}};

        boolean iniciar = gameManagerTestes.createInitialBoard(info, 25, ferramentasEAbismo);

        assertEquals("Exception", gameManagerTestes.getTitle(13));
        assertEquals("IDE", gameManagerTestes.getTitle(2));
        assertEquals(null, gameManagerTestes.getTitle(26));
        assertEquals(null, gameManagerTestes.getTitle(3));
    }


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
        String[][] ferramentasEAbismo = {{"1", "0", "3"}, {"1", "0", "7"}, {"1", "4", "12"}, {"0", "6", "15"}};

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

    @Test
    public void test04_jogoCompleto01() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Purple"}};
        String[][] ferramentasEAbismo = {{"1", "5", "3"}, {"1", "3", "6"},{"1", "1", "11"}, {"1", "2", "12"},{"1", "0", "18"}, {"1", "4", "23"},
                {"0", "3", "8"}, {"0", "5", "15"},{"0", "0", "17"}, {"0", "7", "28"},{"0", "8", "26"}, {"0", "1", "25"}};

        boolean iniciar = gameManagerTestes.createInitialBoard(info, 30, ferramentasEAbismo);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(2); //0 -> 3
        String mensagem = gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(5); //1 -> 6
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(6); //0 -> 9
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(3); //1 -> 9
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(6); //0 -> 9
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(4); //1 -> 13
        gameManagerTestes.reactToAbyssOrTool();
        assertEquals(false, gameManagerTestes.gameIsOver());
        gameManagerTestes.moveCurrentPlayer(2); //0 -> 11
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(3); //1 -> 16
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(3); //0 -> 14
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(1); //1 -> 16
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(6); //0 -> 20
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(6); //1 -> 22
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(5); //0 -> 25
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(5); //1 -> 27
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(2); //0 -> 27
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(6); //1 -> 27
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(1); //0 -> 28
        gameManagerTestes.reactToAbyssOrTool();
        assertEquals(true, gameManagerTestes.gameIsOver());
        assertEquals("Tiago Águeda", gameManagerTestes.getGameResults().get(6));

        assertEquals(300,gameManagerTestes.getAuthorsPanel().getHeight());
        assertEquals(300,gameManagerTestes.getAuthorsPanel().getWidth());

    }

    @Test
    public void test04_jogoCompleto02() {
        String[][] info = {{"22001757", "Tiago Águeda", "Java, C, Kotlin", "Blue"}, {"22002629", "João Antas", "Javascript, C++, Assembly", "Purple"}};
        String[][] ferramentasEAbismo = {{"1", "4", "3"}, {"1", "4", "6"},{"1", "4", "11"}, {"1", "2", "12"},{"1", "0", "18"}, {"1", "4", "23"},
                {"0", "1", "8"}, {"0", "2", "10"},{"0", "3", "15"}, {"0", "9", "19"},{"0", "2", "26"}, {"0", "6", "20"}};

        boolean iniciar = gameManagerTestes.createInitialBoard(info, 30, ferramentasEAbismo);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(2); //0 -> 3
        String mensagem = gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(5); //1 -> 6
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(5); //0 -> 6
        gameManagerTestes.reactToAbyssOrTool();
        assertEquals(6, gameManagerTestes.programadores.get(0).getPosicao());
        gameManagerTestes.moveCurrentPlayer(4); //1 -> 8
        gameManagerTestes.reactToAbyssOrTool();
        assertEquals(8, gameManagerTestes.programadores.get(1).getPosicao());
        gameManagerTestes.moveCurrentPlayer(6); //0 -> 12
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(3); //1 -> 11
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(3); //0 -> 12
        gameManagerTestes.reactToAbyssOrTool();
        assertEquals(12, gameManagerTestes.programadores.get(0).getPosicao());
        gameManagerTestes.moveCurrentPlayer(6); //1 -> 17
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(4); //0 -> 16
        gameManagerTestes.reactToAbyssOrTool();
        gameManagerTestes.moveCurrentPlayer(2); //1 -> 19
        gameManagerTestes.reactToAbyssOrTool();
        assertEquals(19, gameManagerTestes.programadores.get(1).getPosicao());
        gameManagerTestes.moveCurrentPlayer(3); //0 -> 16
        gameManagerTestes.reactToAbyssOrTool();
        assertEquals(16, gameManagerTestes.programadores.get(0).getPosicao());
        gameManagerTestes.moveCurrentPlayer(1); //1 -> 20
        gameManagerTestes.reactToAbyssOrTool();
        assertEquals(17, gameManagerTestes.programadores.get(1).getPosicao());

    }
}