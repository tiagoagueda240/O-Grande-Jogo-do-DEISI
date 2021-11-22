package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameManager {
    ArrayList<Programmer> programadores = new ArrayList<>();
    ArrayList<Abismo> abismos = new ArrayList<>();
    ArrayList<Ferramenta> ferramentas = new ArrayList<>();
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

    Abismo criarAbismo(String[] info){
        switch (info[1]){
            case "0":
                return new Abismo("Erro de sintaxe", 0, Integer.valueOf(info[3]));
            case "1":
                return new Abismo("Erro de lógico", 1, Integer.valueOf(info[3]));
            case "2":
                return new Abismo("Exception", 2, Integer.valueOf(info[3]));
            case "3":
                return new Abismo("File Not Found Exception", 3, Integer.valueOf(info[3]));
            case "4":
                return new Abismo("Crash (aka Rebentanço)", 4, Integer.valueOf(info[3]));
            case "5":
                return new Abismo("Duplicated Code", 5, Integer.valueOf(info[3]));
            case "6":
                return new Abismo("Efeitos secundários", 6, Integer.valueOf(info[3]));
            case "7":
                return new Abismo("Blue Screen Of Death", 7, Integer.valueOf(info[3]));
            case "8":
                return new Abismo("Ciclo infinito", 8, Integer.valueOf(info[3]));
            case "9":
                return new Abismo("Segmentation Fault", 9, Integer.valueOf(info[3]));

            default:
                return null;
        }
    }

    Ferramenta criarFerramentas(String[] info){
        switch (info[1]){
            case "0":
                return new Ferramenta("Herança", 0, Integer.valueOf(info[3]));
            case "1":
                return new Ferramenta("Programação Funcional", 1, Integer.valueOf(info[3]));
            case "2":
                return new Ferramenta("Testes unitários", 2, Integer.valueOf(info[3]));
            case "3":
                return new Ferramenta("Tratamento de Excepções", 3, Integer.valueOf(info[3]));
            case "4":
                return new Ferramenta("IDE", 4, Integer.valueOf(info[3]));
            case "5":
                return new Ferramenta("Ajuda do Professor", 5, Integer.valueOf(info[3]));

            default:
                return null;
        }
    }

    public GameManager() {
    }

    boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools){
        boolean verifica = createInitialBoard(playerInfo, worldSize);
        if (!verifica){
            return false;
        }
        for (int i = 0; i < abyssesAndTools.length; i++){
            if (abyssesAndTools[i][0].equals("0")){
                abismos.add(criarAbismo(abyssesAndTools[i]));
            }else if(abyssesAndTools[i][0].equals("1")){
                ferramentas.add(criarFerramentas(abyssesAndTools[i]));
            }
        }
        return true;
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
        if (position > nrCasas) { // verificação do jogador saiu do tabuleiro
            return null;
        }
        if (position == nrCasas) {// verifica se o jogador chegou a ultima casa
            return "glory.png";
        }

        for (Programmer programmer: programadores) {
            if (programmer.getPosicao() == position) {
                return "player" + programmer.getColor().toString() + ".png";
            }
        }
        return null;
    }

    String getTitle(int position){
        if (position < 1 || position > nrCasas){
            return null;
        }
        for (Ferramenta ferramenta: ferramentas){
            if (ferramenta.getPosicao() == position){
                return ferramenta.getTitulo();
            }
        }
        for (Abismo abismo: abismos){
            if (abismo.getPosicao() == position){
                return abismo.getTitulo();
            }
        }
        return null;
    }
/*
    public List<Programmer> getProgrammers(boolean includeDefeated) {

        return ;
    }
*/

    public List<Programmer> getProgrammers(int position) {
        // Verifica se a posição passada nos parametros está dentro do tabuleiro
        if (position < 1 || position > nrCasas){
            return null;
        }
        List<Programmer> programadoresNaPosicao = new ArrayList<>();
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

    /* String getProgrammersInfo(){

    }
    */

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

    public List<String> getGameResults() {
        List<String> resultados = new ArrayList<>();
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
            if (programadores.get(0).getId() == programador.getId()){ // percorre os jogadores
                continue;
            }
            resultados.add(programador.getName() + " " + programador.getPosicao()); // adiciona o nome e o valor da posição
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