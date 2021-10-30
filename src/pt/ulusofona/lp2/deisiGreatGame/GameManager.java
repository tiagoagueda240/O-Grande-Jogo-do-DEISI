package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.util.*;

public class GameManager {

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int boardSize) {
        ArrayList<Programmer> adicionaJogador = new ArrayList<>(); // adiciona os jogadores
        if (boardSize <= 1){
            return false;
        }
        for (int i = 0; i < playerInfo.length; i++) {
            Programmer player = new Programmer();
            ArrayList<String> guardLanguages = new ArrayList<>(); // cada programador tem n linguagens
            for (int j = 0; j < playerInfo[j].length; j++) {
                switch (j) {
                    case 0:
                        player.iD = Integer.parseInt(playerInfo[i][j]);
                    case 1:
                        player.name = playerInfo[i][j];
                    case 2:
                        Collections.addAll(guardLanguages, playerInfo[i][j].split(";"));
                        player.languages = guardLanguages;
                    case 3:
                        player.colorAvatar = ProgrammerColor.valueOf(playerInfo[i][j]);
                }
            }
            adicionaJogador.add(player); // adiciona á lista
        }
        HashSet<Integer> idDuplicado = new HashSet<>(); // não pode haver iDs repetidos
        HashSet<ProgrammerColor> colorDuplicado = new HashSet<>(); // não pode haver cores repetidas
        for (Programmer adicionaJogadores : adicionaJogador) {
            if (adicionaJogadores.iD < 1 || !idDuplicado.add(adicionaJogadores.iD)) {
                return false;
            }
            if (adicionaJogadores.name == null && Objects.equals(adicionaJogadores.getName(), "")) {
                return false;
            }
            if (adicionaJogadores.colorAvatar == null && !colorDuplicado.add(adicionaJogadores.colorAvatar)) {
                return false;
            }
            idDuplicado.add(adicionaJogadores.iD);
            colorDuplicado.add(adicionaJogadores.colorAvatar);
        }
        return true;

        // O tabuleiro tem de ter, pelo menos duas posições por cada jogador que esteja em jogo.

    }

    public String getImagePng(int position) {

        return null;
    }

    public ArrayList<Programmer> getProgrammers() {

        return null;
    }

    public ArrayList<Programmer> getProgrammers(int position) {

        return null;
    }

    public int getCurrentPlayerID() {

        return 0;
    }

    public boolean moveCurrentPlayer(int nrPositions) {

        return false;
    }

    public boolean gameIsOver() {

        return false;
    }

    public ArrayList<String> getGameResults() {

        return null;
    }

    public JPanel getAuthorsPanel() {

        return null;
    }
}
