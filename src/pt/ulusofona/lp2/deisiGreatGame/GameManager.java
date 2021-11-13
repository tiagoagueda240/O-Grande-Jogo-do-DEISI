package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GameManager {
    ArrayList<Programmer> programadores = new ArrayList<>();
    int nrCasas;
    int nrTurnos = 1; // nr de turnos jogados
    int turnoAtual = 0; // turno atual, pode ser: 0, 1, 2 ou 3

    /*
    * Função que retorna o enum
    */
    ProgrammerColor encontrarCor(String cor){
        switch (cor){
            case "PURPLE":
                return ProgrammerColor.PURPLE;

            case "BLUE":
                return ProgrammerColor.BLUE;

            case "GREEN":
                return ProgrammerColor.GREEN;

            case "BROWN":
                return ProgrammerColor.BROWN;

            default:
                return null;
        }
    }

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int boardSize) {
        /*
        *  Fazer reset
        */
        programadores.clear();
        nrTurnos = 1;
        turnoAtual = 0;

        if (boardSize <= 1) { // Verifica o tamanho do tabuleiro
            return false;
        }

        nrCasas = boardSize;
        // Adiciona todos os jogadores à lista de jogadores
        for (int i = 0; i < playerInfo.length; i++) {
            ArrayList<String> languages = new ArrayList(Arrays.asList(playerInfo[i][2].split(";")));
            Programmer player = new Programmer(playerInfo[i][1], languages, Integer.parseInt(playerInfo[i][0]), encontrarCor(playerInfo[i][3].toUpperCase()), 1, "Em Jogo");
            programadores.add(player);
        }

        //Valida tamanho do tabuleiro
        if (programadores.size() > 4 || programadores.size() < 2 || nrCasas < programadores.size() * 2 ){
            return false;
        }

        HashSet<ProgrammerColor> colorDuplicado = new HashSet<>(); // não pode haver cores repetidas
        HashSet<Integer> idDuplicated = new HashSet<>(); // não pode haver iDs repetidos

        //Percorrer todos os jogadores para fazer todas as verificações enunciadas no enunciado
        for (Programmer programmer : programadores) {
            if (programmer.getId() == 0 || colorDuplicado.contains(programmer.getColor()) || idDuplicated.contains(programmer.getId()) ||
                    programmer.getColor() == null || programmer.getName().equals("")) {
                return false;
            }
            idDuplicated.add(programmer.getId());
            colorDuplicado.add(programmer.getColor());

        }
        //Ordena os jogadores por ordem dos ids
        programadores.sort(Comparator.comparing((Programmer programador1) -> programador1.getId()));
        getImagePng(1);
        getImagePng(boardSize);
        return true;
    }

    public String getImagePng(int position) {
        if (position > nrCasas) {
            return null;
        }
        if (position == nrCasas) {
            return "glory.png";
        }

        for (Programmer programmer: programadores) {
            if (programmer.getPosicao() == position) {
                return "player" + programmer.getColor().toString() + ".png";
            }
        }
        return null;
    }

    public ArrayList<Programmer> getProgrammers() {
        return programadores;
    }

    public ArrayList<Programmer> getProgrammers(int position) {
        // Verifica se a posição passada nos parametros está dentro do tabuleiro
        if (position < 1 || position > nrCasas){
            return null;
        }
        ArrayList<Programmer> programadoresNaPosicao = new ArrayList<>();
        for (Programmer programmer: programadores) {
            if (programmer.getPosicao() == position) { //Verifica se há jogadores na posição
                programadoresNaPosicao.add(programmer);
            }
        }

        return programadoresNaPosicao;
    }

    public int getCurrentPlayerID() {
        return programadores.get(turnoAtual).getId();
    }

    public boolean moveCurrentPlayer(int nrPositions) {
        if (nrPositions < 1 || nrPositions > 6) {
            return false;
        }
        for (Programmer programador: programadores) {
            if (programador.getId() == getCurrentPlayerID()){
                if (programador.getPosicao() + nrPositions <= nrCasas) { // Verifica se o jogador pode andar sem ultrapassar a meta
                    programador.mover(nrPositions);
                }else{
                    programador.recuar(nrPositions, nrCasas);
                }
            }
        }
        turnoAtual++;
        nrTurnos++;
        if(turnoAtual >= programadores.size()) { // Verifica se é o ultimo jogador
            turnoAtual = 0;
        }
        return true;
    }

    public boolean gameIsOver() {
        //Verifica se algum jogador chegou ao fim
        for (Programmer programador: programadores){
            if (programador.getPosicao() == nrCasas){
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getGameResults() {
        ArrayList<String> resultados = new ArrayList<>();
        resultados.add("O GRANDE JOGO DO DEISI");
        resultados.add("");
        resultados.add("NR. DE TURNOS");
        resultados.add(String.valueOf(nrTurnos));
        resultados.add("");
        resultados.add("VENCEDOR");
        programadores.sort(Comparator.comparing((Programmer programador1) -> programador1.getPosicao()).reversed());
        resultados.add(programadores.get(0).getName());
        resultados.add("");
        resultados.add("RESTANTES");
        for (Programmer programador: programadores){
            if (programadores.get(0).getId() == programador.getId()){
                continue;
            }
            resultados.add(programador.getName() + " " + programador.getPosicao());
        }

        return resultados;
    }

    public JPanel getAuthorsPanel() {
        JPanel credits = new JPanel();
        credits.setSize(new Dimension(300, 300));
        JLabel linha1 = new JLabel();
        linha1.setText("Projeto Deisi Great Game");
        credits.add(linha1);

        JLabel linha2 = new JLabel();
        linha1.setText("Tiago Águeda a22001757");
        credits.add(linha2);

        JLabel linha3 = new JLabel();
        linha1.setText("João Antas a22002629");
        credits.add(linha3);


        return credits;
    }
}