package pt.ulusofona.lp2.deisiGreatGame;
import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Test;

public class TestGameManager {
    GameManager gameManagerTestes = new GameManager();
    @Test
    public void test01_moveCurrentPlayer(){
        String[][] info = {{"22001757","Tiago Águeda","Java,C,Kotlin","Blue"},{"22002629","João Antas","Javascript,C++,Assembly","Green"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 100);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(5);
        assertEquals(6, gameManagerTestes.programadores.get(0).getPosicao());
    }

    @Test
    public void test02_moveCurrentPlayer(){
        String[][] info = {{"22001757","Tiago Águeda","Java,C,Kotlin","Blue"},{"22002629","João Antas","Javascript,C++,Assembly","Green"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 10);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(5);
        movimento = gameManagerTestes.moveCurrentPlayer(6);
        movimento = gameManagerTestes.moveCurrentPlayer(6);


        assertEquals(1, gameManagerTestes.programadores.get(1).getPosicao());
    }

    @Test
    public void test03_moveCurrentPlayer(){
        String[][] info = {{"22001757","Tiago Águeda","Java,C,Kotlin","Blue"},{"22002629","João Antas","Javascript,C++,Assembly","Green"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 100);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(5);
        movimento = gameManagerTestes.moveCurrentPlayer(6);
        movimento = gameManagerTestes.moveCurrentPlayer(2);

        assertEquals(14, gameManagerTestes.programadores.get(0).getPosicao());
    }

    @Test
    public void test04_moveCurrentPlayer(){
        String[][] info = {{"22001757","Tiago Águeda","Java,C,Kotlin","Blue"},{"22002629","João Antas","Javascript,C++,Assembly","Green"},{"19999639","Camelo Cabral","Python,C++","Purple"}};
        boolean iniciar = gameManagerTestes.createInitialBoard(info, 100);
        boolean movimento = gameManagerTestes.moveCurrentPlayer(5);
        movimento = gameManagerTestes.moveCurrentPlayer(5);
        movimento = gameManagerTestes.moveCurrentPlayer(4);
        movimento = gameManagerTestes.moveCurrentPlayer(1);
        movimento = gameManagerTestes.moveCurrentPlayer(6);
        movimento = gameManagerTestes.moveCurrentPlayer(3);
        movimento = gameManagerTestes.moveCurrentPlayer(2);
        movimento = gameManagerTestes.moveCurrentPlayer(2);
        movimento = gameManagerTestes.moveCurrentPlayer(4);
        assertEquals(1, gameManagerTestes.programadores.get(2).getPosicao());
    }
}
