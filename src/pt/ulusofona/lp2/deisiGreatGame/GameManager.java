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
    int nrDado;
    static HashMap<String, ArrayList<String>> listaTools = new HashMap<>();

    static void crialistaTools(){
        ArrayList<String> ferramentasPossiveis = new ArrayList<>();
        //ferramentasPossiveis.add();
        listaTools.put("", ferramentasPossiveis);
    }

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

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools){
        boolean verifica = createInitialBoard(playerInfo, worldSize);
        if (!verifica){
            return false;
        }
        for (int i = 0; i < abyssesAndTools.length; i++){
            if (abyssesAndTools[i][0].equals("0")){
                if (Integer.parseInt(abyssesAndTools[i][1]) >= 0 || Integer.parseInt(abyssesAndTools[i][1]) <= 9 || Integer.parseInt(abyssesAndTools[i][2]) > 0 || Integer.parseInt(abyssesAndTools[i][2]) <= worldSize){
                    abismos.add(criarAbismo(abyssesAndTools[i]));
                }else{
                    return false;
                }

            }else if(abyssesAndTools[i][0].equals("1")){
                if (Integer.parseInt(abyssesAndTools[i][1]) >= 0 || Integer.parseInt(abyssesAndTools[i][1]) <= 5 || Integer.parseInt(abyssesAndTools[i][2]) > 0 || Integer.parseInt(abyssesAndTools[i][2]) <= worldSize) {
                    ferramentas.add(criarFerramentas(abyssesAndTools[i]));
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        abismos.sort(Comparator.comparing((Abismo abismo1) -> abismo1.getPosicao()));
        ferramentas.sort(Comparator.comparing((Ferramenta ferramenta1) -> ferramenta1.getPosicao()));
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

        for (Abismo abismo: abismos) {
            if (abismo.getPosicao() == position) {
                return "abismo" + abismo.getId() + ".png";
            }
        }

        for (Ferramenta ferramenta: ferramentas) {
            if (ferramenta.getPosicao() == position) {
                return "ferramenta" + ferramenta.getId() + ".png";
            }
        }
        return null;
    }

    public String getTitle(int position){
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

    public List<Programmer> getProgrammers(boolean includeDefeated) {
        List<Programmer> listaProgramadores = new ArrayList<>();
        if (includeDefeated){
            listaProgramadores = programadores;
        }else{
            for (Programmer programador: programadores){
                if (programador.getEstado().equals("Em Jogo")){
                    listaProgramadores.add(programador);
                }
            }
        }
        return listaProgramadores;
    }


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

    public String getProgrammersInfo(){
        StringBuilder listaJogadores = new StringBuilder();
        for (Programmer programador: programadores){
            listaJogadores.append(programador.getName() + " : " + programador.criaListaFerramentas());
            if (programador != programadores.get(programadores.size() - 1)) {
                listaJogadores.append(" | ");
            }

        }
        return listaJogadores.toString();
    }


    public boolean moveCurrentPlayer(int nrPositions) {
        if (nrPositions < 1 || nrPositions > 6) {
            return false;
        }
        if (!programadores.get(turnoAtual).getValorPreso()){
            nrDado = nrPositions;
            for (Programmer programador: programadores) {
                if (programador.getId() == getCurrentPlayerID()){
                    if (programador.getPosicao() + nrPositions <= nrCasas) { // Verifica se o jogador pode andar sem ultrapassar a meta
                        programador.mover(nrPositions);

                    }else{
                        programador.avancarRecuar(nrPositions, nrCasas);
                    }
                }
            }
            return true;
        }

        return false;
    }


    public String reactToAbyssOrTool(){
        String mensagem = "";
        for (Ferramenta ferramenta: ferramentas){
            if (ferramenta.getPosicao() == programadores.get(turnoAtual).getPosicao()){
                programadores.get(turnoAtual).addFerramenta(ferramenta.getTitulo());
                mensagem = "ferramenta";
                break;
            }
        }
        for (Abismo abismo : abismos){
            if (abismo.getPosicao() == programadores.get(turnoAtual).getPosicao()){
                HashSet<String> listaFerramentasUteis = ferramentasUteis(abismo.getTitulo());
                if (!programadores.get(turnoAtual).contemFerramentaUtil(listaFerramentasUteis)){
                    // efeitos dos abismos
                    if (abismo.getTitulo().equals("Erro de sintaxe")){
                        programadores.get(turnoAtual).recuar(1);
                        mensagem = "Teste 1";
                    }else if (abismo.getTitulo().equals("Erro de lógica")){
                        programadores.get(turnoAtual).recuar(nrDado/2);
                        mensagem = "Teste 2";
                    }else if (abismo.getTitulo().equals("Exception")){
                        programadores.get(turnoAtual).recuar(2);
                        mensagem = "Teste 3";
                    }else if (abismo.getTitulo().equals("File Not Found Exception")){
                        programadores.get(turnoAtual).recuar(3);
                        mensagem = "Teste 4";
                    }else if (abismo.getTitulo().equals("Crash (aka Rebentanço)")){
                        programadores.get(turnoAtual).posicaoInicial();
                        mensagem = "Teste 5";
                    }else if (abismo.getTitulo().equals("Duplicated Code")){//
                        programadores.get(turnoAtual).saberPosicaoJogadas(1);
                        mensagem = "Teste 6";
                    }else if (abismo.getTitulo().equals("Efeitos secundários")){
                        programadores.get(turnoAtual).saberPosicaoJogadas(2);
                        mensagem = "Teste 7";
                    }else if (abismo.getTitulo().equals("Blue Screen of Death")){
                       programadores.get(turnoAtual).perdeu();
                        mensagem = "Teste 8";
                    }else if (abismo.getTitulo().equals("Ciclo infinito")){
                        HashSet<Programmer> programadoresPosicao = new HashSet<>();
                        for (Programmer programador : programadores){
                            if (programador.getPosicao() == abismo.getPosicao()){
                                programadoresPosicao.add(programador);
                            }
                        }
                        if (programadoresPosicao.size() == 1){
                            programadores.get(turnoAtual).alteraValorPreso(true);
                        }else{
                            for (Programmer programador : programadoresPosicao){
                                if (programador.getValorPreso()){
                                    programador.alteraValorPreso(false);
                                }else{
                                    programador.alteraValorPreso(true);
                                }
                            }
                        }
                        mensagem = "Teste 9";
                    }else if (abismo.getTitulo().equals("Segmentation Fault")){
                        ArrayList<Programmer> jogadoresEmPosicao = new ArrayList<>();
                        for (Programmer programador : programadores) {
                            if(programador.getPosicao() == abismo.getPosicao()) {
                                jogadoresEmPosicao.add(programador);
                            }
                        }
                        if (jogadoresEmPosicao.size() >= 2) {
                            for (Programmer programador : jogadoresEmPosicao) {
                                programador.recuar(3);
                            }
                        }
                        mensagem = "Teste 10";
                    }
                    break;
                }
            }
        }
        programadores.get(turnoAtual).adicionaPosicao(programadores.get(turnoAtual).getPosicao());
        turnoAtual++;
        nrTurnos++;
        if(turnoAtual >= programadores.size()) { // Verifica se é o ultimo jogador
            turnoAtual = 0;
        }
        if(programadores.get(turnoAtual).getValorPreso() || programadores.get(turnoAtual).getEstado().equals("Derrotado")){
            turnoAtual++;
        }
        if(turnoAtual >= programadores.size()) { // Verifica se é o ultimo jogador
            turnoAtual = 0;
        }

        if (mensagem.equals("")){
            return null;
        }else{
            return mensagem;
        }


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
        programadores.sort(Comparator.comparing((Programmer programador1) -> programador1.getName()));
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





    public HashSet<String> ferramentasUteis(String nome){
        HashSet<String> listaFerramentas = new HashSet<>();
        switch (nome){
            case "Erro de sintaxe":
                listaFerramentas.add("Ajuda do Professor");
                listaFerramentas.add("IDE");
                break;
            case "Erro de lógica":
                listaFerramentas.add("Ajuda do Professor");
                break;
            case "Exception":
                listaFerramentas.add("Ajuda do Professor");
                listaFerramentas.add("Tratamento de Excepções");
                break;
            case "File Not Found Exception":
                listaFerramentas.add("Ajuda do Professor");
                listaFerramentas.add("Tratamento de Excepções");
                break;
            case "Crash (aka Rebentanço)":
                listaFerramentas.add("Programação Funcional");
                break;
            case "Duplicated Code":
                listaFerramentas.add("Herança");
                break;
            case "Efeitos secundários":
                listaFerramentas.add("Testes unitários");
                break;
            case "Blue Screen of Death":
                listaFerramentas.add("teste8");
                break;
            case "Ciclo infinito":
                listaFerramentas.add("teste9");
                break;
            case "Segmentation Fault":
                listaFerramentas.add("teste10");
                break;
            default:
                return null;
        }
        return listaFerramentas;
    }

    Abismo criarAbismo(String[] info){
        switch (info[1]){
            case "0":
                return new Abismo("Erro de sintaxe", 0, Integer.valueOf(info[2]));
            case "1":
                return new Abismo("Erro de lógica", 1, Integer.valueOf(info[2]));
            case "2":
                return new Abismo("Exception", 2, Integer.valueOf(info[2]));
            case "3":
                return new Abismo("File Not Found Exception", 3, Integer.valueOf(info[2]));
            case "4":
                return new Abismo("Crash (aka Rebentanço)", 4, Integer.valueOf(info[2]));
            case "5":
                return new Abismo("Duplicated Code", 5, Integer.valueOf(info[2]));
            case "6":
                return new Abismo("Efeitos secundários", 6, Integer.valueOf(info[2]));
            case "7":
                return new Abismo("Blue Screen of Death", 7, Integer.valueOf(info[2]));
            case "8":
                return new Abismo("Ciclo infinito", 8, Integer.valueOf(info[2]));
            case "9":
                return new Abismo("Segmentation Fault", 9, Integer.valueOf(info[2]));

            default:
                return null;
        }
    }

    Ferramenta criarFerramentas(String[] info){
        switch (info[1]){
            case "0":
                return new Ferramenta("Herança", 0, Integer.valueOf(info[2]));
            case "1":
                return new Ferramenta("Programação Funcional", 1, Integer.valueOf(info[2]));
            case "2":
                return new Ferramenta("Testes unitários", 2, Integer.valueOf(info[2]));
            case "3":
                return new Ferramenta("Tratamento de Excepções", 3, Integer.valueOf(info[2]));
            case "4":
                return new Ferramenta("IDE", 4, Integer.valueOf(info[2]));
            case "5":
                return new Ferramenta("Ajuda Do Professor", 5, Integer.valueOf(info[2]));

            default:
                return null;
        }
    }
}

