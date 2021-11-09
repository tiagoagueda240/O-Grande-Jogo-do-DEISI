package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GameManager {
    ArrayList<Programmer> programadores = new ArrayList<>();
    int nrCasas;
    int turnos = 0;

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int boardSize) {
        if (boardSize <= 1) {
            return false;
        }

        nrCasas = boardSize;
        for (int i = 0; i < playerInfo.length; i++) {
            ArrayList<String> languages = new ArrayList();
            languages.addAll(Arrays.asList(playerInfo[i][2].split(";")));
            //Collections.addAll(languages, playerInfo[i][2].split(";"));
            Programmer player = new Programmer();
            player.iD = Integer.parseInt(playerInfo[i][0]);
            player.name = playerInfo[i][1];
            player.languages = languages;
            player.colorAvatar = ProgrammerColor.valueOf(playerInfo[i][3].toUpperCase());
            programadores.add(player);

        }
        if (programadores.size() > 4 || programadores.size() < 2 || programadores.size() * 2 > nrCasas){
            return false;
        }
        HashSet<Integer> idDuplicado = new HashSet<>(); // não pode haver iDs repetidos
        HashSet<ProgrammerColor> colorDuplicado = new HashSet<>(); // não pode haver cores repetidas
        for (Programmer programador : programadores) {
            if (programador.getId() == 0 || !colorDuplicado.add(programador.getColor()) || !idDuplicado.add(programador.getId()) ||
                    programador.getColor() == null || programador.getName().equals("")) {
                return false;
            }
            idDuplicado.add(programador.getId());
            colorDuplicado.add(programador.getColor());
            getImagePng(1);
            getImagePng(boardSize);
        }
        return true;

        // O tabuleiro tem de ter, pelo menos duas posições por cada jogador que esteja em jogo.

    }

    public String getImagePng(int position) {
        if (position > nrCasas) {
            return null;
        }
        if (position == nrCasas) {
            return "vitoria.png";
        }
        for (Programmer programador: programadores) {
            if (programador.getPosicao() == position) {
                return String.valueOf(programador.getColor());
            }
        }
        return null;
    }

    public ArrayList<Programmer> getProgrammers() {
        return programadores;
    }

    public ArrayList<Programmer> getProgrammers(int position) {
        if (position < 1 || position > nrCasas){
            return null;
        }
        ArrayList<Programmer> programadoresNaPosicao = new ArrayList<>();
        for (Programmer programador: programadores) {
            if (programador.getPosicao() == position) {
                programadoresNaPosicao.add(programador);
            }
        }
        /*if (programadoresNaPosicao.size() == 0 || position > nrCasas) { // Verificar numero de casas
            return null;
        }*/

        return programadoresNaPosicao;
    }

    public int getCurrentPlayerID() {
        for (Programmer programador: programadores) {
            if (programador.posicao == turnos) {
                return programador.getId();
            }
        }
        return 0;
    }

    public boolean moveCurrentPlayer(int nrPositions) {
        if (nrPositions < 1 || nrPositions > 6) {
            return false;
        }
        for (Programmer programador: programadores) {
            if (programador.getId() == getCurrentPlayerID() && nrPositions > 0 && nrPositions <= 6){
                if (programador.getPosicao() + nrPositions <= nrCasas) {
                    programador.mover(nrPositions); //falta verificar se está fora do tabuleiro
                    turnos++;
                }else{
                    programador.recuar(nrPositions, nrCasas); //falta verificar se está fora do tabuleiro
                    turnos++;
                }

                return true;
            }
        }
        return false;
    }

    public boolean gameIsOver() {
        for (Programmer programador: programadores){
            if (programador.getPosicao() == nrCasas){
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getGameResults() {
        Collections.sort(programadores, new ComparadorDePosicoes());
        ArrayList<String> resultados = new ArrayList<>();
        resultados.add("O GRANDE JOGO DO DEISI");
        resultados.add("\n");
        resultados.add("NR. DE TURNOS");
        resultados.add(String.valueOf(turnos));
        resultados.add("\n");
        resultados.add("VENCEDOR");
        resultados.add(programadores.get(0).getName() + " " + programadores.get(0).getPosicao());
        resultados.add("\n");
        resultados.add("RESTANTES");
        for (Programmer programador: programadores){
            if (programadores.get(0).getId() == programador.getId()){
                break;
            }
            resultados.add(programador.getName() + " " + programador.getPosicao());
        }

        return resultados;
    }

    public JPanel getAuthorsPanel() {
        JPanel painel = new JPanel();
        painel.setSize(new Dimension(300, 300));
        JLabel linha1 = new JLabel();
        linha1.setText("Projeto Deisi Great Game");
        painel.add(linha1);
        JLabel linha2 = new JLabel();
        linha1.setText("Tiago Águeda a22001757");
        painel.add(linha2);
        JLabel linha3 = new JLabel();
        linha1.setText("João Antas a22002629");
        painel.add(linha3);

        return painel;
    }

    class ComparadorDePosicoes implements Comparator<Programmer> {
        public int compare(Programmer o1, Programmer o2) {
            if (o1.getPosicao() > o2.getPosicao()) {
                return -1;
            } else if (o1.getPosicao() < o2.getPosicao()) {
                return +1;
            } else {
                return 0;
            }
        }
    }
}