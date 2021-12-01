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

    ProgrammerColor encontrarCor(String cor) { //Função que retorna o enum
        switch (cor) {
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

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
        // reset
        abismos.clear();
        ferramentas.clear();
        boolean verifica = createInitialBoard(playerInfo, worldSize);
        if (!verifica) {
            return false;
        }
        for (int i = 0; i < abyssesAndTools.length; i++) {
            if (abyssesAndTools[i][0].equals("0")) {
                if (Integer.parseInt(abyssesAndTools[i][1]) >= 0 && Integer.parseInt(abyssesAndTools[i][1]) <= 9 && Integer.parseInt(abyssesAndTools[i][2]) >= 0 && Integer.parseInt(abyssesAndTools[i][2]) < worldSize) {
                    abismos.add(criarAbismo(abyssesAndTools[i][1], abyssesAndTools[i][2]));
                } else {
                    return false;
                }

            } else if (abyssesAndTools[i][0].equals("1")) {
                if (Integer.parseInt(abyssesAndTools[i][1]) >= 0 && Integer.parseInt(abyssesAndTools[i][1]) <= 5 && Integer.parseInt(abyssesAndTools[i][2]) >= 0 && Integer.parseInt(abyssesAndTools[i][2]) < worldSize) {
                    ferramentas.add(criarFerramentas(abyssesAndTools[i][1], abyssesAndTools[i][2]));
                } else {
                    return false;
                }
            } else {
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

        // Valida tamanho do tabuleiro
        if (programadores.size() > 4 || programadores.size() < 2 || nrCasas < programadores.size() * 2) {
            return false;
        }

        HashSet<ProgrammerColor> colorDuplicado = new HashSet<>(); // não pode haver cores repetidas
        HashSet<Integer> idDuplicated = new HashSet<>(); // não pode haver iDs repetidos

        // Percorrer todos os jogadores para fazer todas as verificações enunciadas no enunciado
        for (Programmer programmer : programadores) {
            if (programmer.getId() == 0 || colorDuplicado.contains(programmer.getColor()) || idDuplicated.contains(programmer.getId()) ||
                    programmer.getColor() == null || programmer.getName().equals("")) {
                return false;
            }
            idDuplicated.add(programmer.getId());
            colorDuplicado.add(programmer.getColor());

        }
        // Ordena os jogadores por ordem dos ids
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

        for (Programmer programmer : programadores) {
            if (programmer.getPosicao() == position) {
                return "player" + programmer.getColor().toString() + ".png";
            }
        }

        for (Abismo abismo : abismos) {
            if (abismo.getPosicao() == position) {
                return "abismo" + abismo.getId() + ".png";
            }
        }

        for (Ferramenta ferramenta : ferramentas) {
            if (ferramenta.getPosicao() == position) {
                return "ferramenta" + ferramenta.getId() + ".png";
            }
        }
        return null;
    }

    public String getTitle(int position) {
        if (position < 1 || position > nrCasas) {
            return null;
        }
        for (Ferramenta ferramenta : ferramentas) {
            if (ferramenta.getPosicao() == position) {
                return ferramenta.getTitulo();
            }
        }
        for (Abismo abismo : abismos) {
            if (abismo.getPosicao() == position) {
                return abismo.getTitulo();
            }
        }
        return null;
    }

    public List<Programmer> getProgrammers(boolean includeDefeated) {
        List<Programmer> listaProgramadores = new ArrayList<>();
        if (includeDefeated) {
            listaProgramadores = programadores;
        } else {
            for (Programmer programador : programadores) {
                if (programador.getEstado().equals("Em Jogo")) {
                    listaProgramadores.add(programador);
                }
            }
        }
        return listaProgramadores;
    }

    public List<Programmer> getProgrammers(int position) {
        // Verifica se a posição passada nos parametros está dentro do tabuleiro
        if (position < 1 || position > nrCasas) {
            return null;
        }
        List<Programmer> programadoresNaPosicao = new ArrayList<>();
        for (Programmer programmer : programadores) {
            if (programmer.getPosicao() == position) { // Verifica se há jogadores na posição
                programadoresNaPosicao.add(programmer);
            }
        }

        return programadoresNaPosicao;
    }

    public int getCurrentPlayerID() {
        return programadores.get(turnoAtual).getId();
    }

    public String getProgrammersInfo() {
        programadores.sort(Comparator.comparing((Programmer programador1) -> programador1.getId()));
        StringBuilder listaJogadores = new StringBuilder();
        for (Programmer programador : programadores) {
            if (programador.getEstado().equals("Em Jogo")) {
                listaJogadores.append(programador.getName() + " : " + programador.criaListaFerramentas());
                if (programador != programadores.get(programadores.size() - 1)) {
                    listaJogadores.append(" | ");
                }
            }


        }
        return listaJogadores.toString();
    }

    public boolean moveCurrentPlayer(int nrPositions) {
        if (nrPositions < 1 || nrPositions > 6) {
            return false;
        }
        //if (!programadores.get(turnoAtual).getValorPreso()){
        nrDado = nrPositions;
        for (Programmer programador : programadores) {
            if (programador.getId() == getCurrentPlayerID() && !programadores.get(turnoAtual).getValorPreso()) {
                if (programador.getPosicao() + nrPositions <= nrCasas) { // Verifica se o jogador pode andar sem ultrapassar a meta
                    programador.mover(nrPositions);
                } else {
                    programador.avancarRecuar(nrPositions, nrCasas);
                }
                return true;
            }
        }
        //}
        return false;
    }

    public String reactToAbyssOrTool() {
        String mensagem = "";
        for (Ferramenta ferramenta : ferramentas) {
            if (ferramenta.getPosicao() == programadores.get(turnoAtual).getPosicao()) {
                programadores.get(turnoAtual).addFerramenta(ferramenta.getTitulo());
                mensagem = "Adquiriu a ferramenta " + ferramenta.getTitulo();
            }
        }
        for (Abismo abismo : abismos) {
            if (abismo.getPosicao() == programadores.get(turnoAtual).getPosicao()) {
                HashSet<String> listaFerramentasUteis = abismo.listaFerramentas;
                if (!programadores.get(turnoAtual).contemFerramentaUtil(listaFerramentasUteis)) {
                    if (abismo.getTitulo().equals("Erro de sintaxe")) {
                        programadores.get(turnoAtual).recuar(1);
                        mensagem = "Teve um erro de sintaxe, recua 2 casas!";
                    } else if (abismo.getTitulo().equals("Erro de lógica")) {
                        programadores.get(turnoAtual).recuar(nrDado / 2);
                        mensagem = "Teve um erro de lógica, recue" + nrDado / 2 + "casa(s)!";
                    } else if (abismo.getTitulo().equals("Exception")) {
                        programadores.get(turnoAtual).recuar(2);
                        mensagem = "Exception! Recue 2 casas.";
                    } else if (abismo.getTitulo().equals("File Not Found Exception")) {
                        programadores.get(turnoAtual).recuar(3);
                        mensagem = "File Not Found Exception! Recue 3 casas.";
                    } else if (abismo.getTitulo().equals("Crash (aka Rebentanço)")) {
                        programadores.get(turnoAtual).posicaoInicial();
                        mensagem = "Crashou o programa! Volte a casa de partida.";
                    } else if (abismo.getTitulo().equals("Duplicated Code")) {
                        programadores.get(turnoAtual).saberPosicaoJogadas(1);
                        mensagem = "Duplicated Code! Volte para a casa onde estava antes desta jogada.";
                    } else if (abismo.getTitulo().equals("Efeitos secundários")) {
                        programadores.get(turnoAtual).saberPosicaoJogadas(2);
                        mensagem = "Teve um efeito secundário, recue no tempo 2 jogadas.";
                    } else if (abismo.getTitulo().equals("Blue Screen of Death")) {
                        programadores.get(turnoAtual).perdeu();
                        mensagem = "Blue Screen of Death! Perdeu o jogo.";
                    } else if (abismo.getTitulo().equals("Ciclo infinito")) {
                        HashSet<Programmer> programadoresPosicao = new HashSet<>();
                        for (Programmer programador : programadores) {
                            if (programador.getPosicao() == abismo.getPosicao()) {
                                programadoresPosicao.add(programador);
                            }
                        }
                        if (programadoresPosicao.size() == 1) {
                            programadores.get(turnoAtual).alteraValorPreso(true);
                        } else {
                            for (Programmer programador : programadoresPosicao) {
                                if (programador.getValorPreso()) {
                                    programador.alteraValorPreso(false);
                                } else {
                                    programador.alteraValorPreso(true);
                                }
                            }
                        }
                        mensagem = "Ciclo infinito! Aguarde por ajuda.";
                    } else if (abismo.getTitulo().equals("Segmentation Fault")) {
                        ArrayList<Programmer> jogadoresEmPosicao = new ArrayList<>();
                        for (Programmer programador : programadores) {
                            if (programador.getPosicao() == abismo.getPosicao()) {
                                jogadoresEmPosicao.add(programador);
                            }
                        }
                        if (jogadoresEmPosicao.size() >= 2) {
                            for (Programmer programador : jogadoresEmPosicao) {
                                programador.recuar(3);
                            }
                        }
                        mensagem = "Segmentation Fault! Se tiver companhia recue 3 casas.";
                    }
                } else {
                    mensagem = "Utilizou uma ferramenta";
                }
            }
        }
        programadores.get(turnoAtual).adicionaPosicao(programadores.get(turnoAtual).getPosicao());
        turnoAtual++;
        nrTurnos++;
        if (turnoAtual >= programadores.size()) { // Verifica se é o ultimo jogador
            turnoAtual = 0;
        }
        if(programadores.get(turnoAtual).getEstado().equals("Derrotado")){
            turnoAtual++;
        }else{
            return validaMensagem(mensagem);
        }
        if(turnoAtual >= programadores.size()) { // Verifica se é o ultimo jogador
            turnoAtual = 0;
        }
        if(programadores.get(turnoAtual).getEstado().equals("Derrotado")){
            turnoAtual++;
        }else{
            return validaMensagem(mensagem);
        }
        if(turnoAtual >= programadores.size()) { // Verifica se é por estar mais que um jogador bloqueados
            turnoAtual = 0;
        }

        return validaMensagem(mensagem);
    }

    String validaMensagem(String mensagem) {
        if (mensagem.equals("")) {
            return null;
        } else {
            return mensagem;
        }
    }

    public boolean gameIsOver() {
        //Verifica se algum jogador chegou ao fim
        int jogadoresEmJogo = 0;
        for (Programmer programador : programadores) {
            if (programador.getEstado().equals("Em Jogo")) {
                jogadoresEmJogo++;
            }
        }
        for (Programmer programador : programadores) {
            if (programador.getPosicao() == nrCasas || jogadoresEmJogo < 2) {
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
        for (Programmer programador : programadores) {
            if (programadores.get(0).getId() == programador.getId()) { // percorre os jogadores
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

    Abismo criarAbismo(String info, String posicao) {
        switch (info) {
            case "0":
                return new ErroDeSintaxe(0, Integer.valueOf(posicao));
            case "1":
                return new ErroDeLogica(1, Integer.valueOf(posicao));
            case "2":
                return new Exception(2, Integer.valueOf(posicao));
            case "3":
                return new FileNotFoundException(3, Integer.valueOf(posicao));
            case "4":
                return new Crash(4, Integer.valueOf(posicao));
            case "5":
                return new DuplicatedCode(5, Integer.valueOf(posicao));
            case "6":
                return new EfeitosSecundarios(6, Integer.valueOf(posicao));
            case "7":
                return new BlueScreenOfDeath(7, Integer.valueOf(posicao));
            case "8":
                return new CicloInfinito(8, Integer.valueOf(posicao));
            case "9":
                return new SegmentationFault(9, Integer.valueOf(posicao));
            default:
                return null;
        }
    }

    Ferramenta criarFerramentas(String info, String posicao) {
        switch (info) {
            case "0":
                return new Ferramenta("Herança", 0, Integer.valueOf(posicao));
            case "1":
                return new Ferramenta("Programação Funcional", 1, Integer.valueOf(posicao));
            case "2":
                return new Ferramenta("Testes unitários", 2, Integer.valueOf(posicao));
            case "3":
                return new Ferramenta("Tratamento de Excepções", 3, Integer.valueOf(posicao));
            case "4":
                return new Ferramenta("IDE", 4, Integer.valueOf(posicao));
            case "5":
                return new Ferramenta("Ajuda Do Professor", 5, Integer.valueOf(posicao));

            default:
                return null;
        }
    }
}