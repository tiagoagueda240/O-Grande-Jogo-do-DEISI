package pt.ulusofona.lp2.deisiGreatGame;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestInvalidInitialBoardException {
    GameManager gameManagerTestes = new GameManager();

    @Test
    public void test01_Exception() throws InvalidInitialBoardException {
        InvalidInitialBoardException excecao = new InvalidInitialBoardException( "1 / Abismo com informações incorretas.");
        assertEquals(false, excecao.isInvalidAbyss());
        assertEquals(false, excecao.isInvalidTool());
        assertEquals("1 ", excecao.getTypeId());
    }
}
