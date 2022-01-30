package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GameManager implements Serializable {
    List<Programmer> programadores = new ArrayList<>();
    List<Abismo> abismos = new ArrayList<>();
    List<Ferramenta> ferramentas = new ArrayList<>();
    int nrCasas;
    int nrTurnos = 1; // nr de turnos jogados
    int turnoAtual = 0; // turno atual, pode ser: 0, 1, 2 ou 3
    int nrDado;
    boolean empatado = false;

    private ProgrammerColor encontrarCor(String cor) { //Função que retorna o enum
        return switch (cor) {
            case "PURPLE" -> ProgrammerColor.PURPLE;
            case "BLUE" -> ProgrammerColor.BLUE;
            case "GREEN" -> ProgrammerColor.GREEN;
            case "BROWN" -> ProgrammerColor.BROWN;
            default -> null;
        };
    }

    public GameManager() {
    }

    public void createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) throws InvalidInitialBoardException {
        // reset
        abismos.clear();
        ferramentas.clear();
        createInitialBoard(playerInfo, worldSize);

        if (abyssesAndTools != null) {
            for (String[] abyssesAndTool : abyssesAndTools) {
                if (abyssesAndTool[0].equals("0")) {
                    if (Integer.parseInt(abyssesAndTool[1]) >= 0 && Integer.parseInt(abyssesAndTool[1]) <= 10 && Integer.parseInt(abyssesAndTool[2]) >= 0 && Integer.parseInt(abyssesAndTool[2]) < worldSize) {
                        abismos.add(criarAbismo(abyssesAndTool[1], Integer.parseInt(abyssesAndTool[2])));
                    } else {
                        throw new InvalidInitialBoardException(Integer.parseInt(abyssesAndTool[1]) + " / Abismo com informações incorretas.");
                    }
                } else if (abyssesAndTool[0].equals("1")) {
                    if (Integer.parseInt(abyssesAndTool[1]) >= 0 && Integer.parseInt(abyssesAndTool[1]) <= 5 && Integer.parseInt(abyssesAndTool[2]) >= 0 && Integer.parseInt(abyssesAndTool[2]) < worldSize) {
                        ferramentas.add(criarFerramentas(abyssesAndTool[1], Integer.parseInt(abyssesAndTool[2])));
                    } else {
                        throw new InvalidInitialBoardException(Integer.parseInt(abyssesAndTool[1]) + " / Ferramenta com informações incorretas.");
                    }
                } else {
                    throw new InvalidInitialBoardException("Não é um abismo nem ferramenta.");
                }
            }

            abismos.sort(Comparator.comparing((Abismo abismo1) -> abismo1.getPosicao()));
            ferramentas.sort(Comparator.comparing((Ferramenta ferramenta1) -> ferramenta1.getPosicao()));
        }
    }

    public void createInitialBoard(String[][] playerInfo, int boardSize) throws InvalidInitialBoardException {
        /*
         *  Fazer reset
         */
        programadores.clear();
        nrTurnos = 1;
        turnoAtual = 0;
        this.empatado = false;

        if (boardSize <= 1) { // Verifica o tamanho do tabuleiro
            throw new InvalidInitialBoardException("Tamanho do tabuleiro incorreto");
        }

        nrCasas = boardSize;
        // Adiciona todos os jogadores à lista de jogadores
        for (String[] strings : playerInfo) {
            ArrayList<String> languages = new ArrayList(Arrays.asList(strings[2].split(";")));
            Programmer player = new Programmer(strings[1], languages, Integer.parseInt(strings[0]), encontrarCor(strings[3].toUpperCase()), 1, "Em Jogo");
            programadores.add(player);
        }

        // Valida tamanho do tabuleiro
        if (programadores.size() > 4 || programadores.size() < 2 || nrCasas < programadores.size() * 2) {
            throw new InvalidInitialBoardException("Tamanho do tabuleiro incorreto");
        }

        HashSet<ProgrammerColor> colorDuplicado = new HashSet<>(); // não pode haver cores repetidas
        HashSet<Integer> idDuplicated = new HashSet<>(); // não pode haver iDs repetidos

        // Percorrer todos os jogadores para fazer todas as verificações enunciadas no enunciado
        for (Programmer programmer : programadores) {
            if (programmer.getId() == 0 || colorDuplicado.contains(programmer.getColor()) || idDuplicated.contains(programmer.getId()) ||
                    programmer.getColor() == null || programmer.getName().equals("")) {
                throw new InvalidInitialBoardException("Informações do programador incorretas");
            }
            idDuplicated.add(programmer.getId());
            colorDuplicado.add(programmer.getColor());

        }
        // Ordena os jogadores por ordem dos ids
        programadores.sort(Comparator.comparing((Programmer programador1) -> programador1.getId()));
        getImagePng(1);
        getImagePng(boardSize);
    }

    public String getImagePng(int position) {
        if (position > nrCasas) { // verificação do jogador saiu do tabuleiro
            return null;
        }
        if (position == nrCasas) {// verifica se o jogador chegou a ultima casa
            return "glory.png";
        }

        for (Abismo abismo : abismos) {
            if (abismo.getPosicao() == position) {
                return "abismos/abismo" + abismo.getId() + ".png";
            }
        }


        for (Ferramenta ferramenta : ferramentas) {
            if (ferramenta.getPosicao() == position) {
                return "ferramentas/ferramenta" + ferramenta.getId() + ".png";
            }
            continue;
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
        programadores.get(turnoAtual).posicao(programadores.get(turnoAtual).getPosicao());
        for (Abismo abismo : abismos) {
            if (abismo.getPosicao() == programadores.get(turnoAtual).getPosicao()) {
                if (!programadores.get(turnoAtual).contemFerramentaUtil(abismo.getFerramentas())) {
                    programadores.get(turnoAtual).adicionaListaAbismos(abismo.getTitulo());
                    switch (abismo.getTitulo()) {
                        case "Erro de sintaxe" -> {
                            programadores.get(turnoAtual).recuar(1);
                            mensagem = "Teve um erro de sintaxe, recue 2 casas!";
                        }
                        case "Erro de lógica" -> {
                            programadores.get(turnoAtual).recuar(nrDado / 2);
                            mensagem = "Teve um erro de lógica, recue " + nrDado / 2 + " casa(s)!";
                        }
                        case "Exception" -> {
                            programadores.get(turnoAtual).recuar(2);
                            mensagem = "Exception! Recue 2 casas.";
                        }
                        case "File Not Found Exception" -> {
                            programadores.get(turnoAtual).recuar(3);
                            mensagem = "File Not Found Exception! Recue 3 casas.";
                        }
                        case "Crash (aka Rebentanço)" -> {
                            programadores.get(turnoAtual).posicaoInicial();
                            mensagem = "Crashou o programa! Volte a casa de partida.";
                        }
                        case "Duplicated Code" -> {
                            programadores.get(turnoAtual).saberPosicaoJogadas(1);
                            mensagem = "Duplicated Code! Volte para a casa onde estava antes desta jogada.";
                        }
                        case "Efeitos secundários" -> {
                            programadores.get(turnoAtual).saberPosicaoJogadas(2);
                            mensagem = "Teve um efeito secundário, recue no tempo 2 jogadas.";
                        }
                        case "Blue Screen of Death" -> {
                            programadores.get(turnoAtual).perdeu();
                            mensagem = "Blue Screen of Death! Perdeu o jogo.";
                        }
                        case "Ciclo infinito" -> {
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
                                    programador.alteraValorPreso(!programador.getValorPreso());
                                }
                            }
                            mensagem = "Ciclo infinito! Aguarde por ajuda.";
                        }
                        case "Segmentation Fault" -> {
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
                        case "Vamos Fazer Contas" -> {
                            programadores.get(turnoAtual).colocaPosicaoMedia();
                            mensagem = "Vamos Fazer Contas!";
                        }
                    }
                } else {
                    mensagem = "Utilizou uma ferramenta";
                }
            }
        }
        programadores.get(turnoAtual).adicionaPosicao(programadores.get(turnoAtual).getPosicao());

        turnoAtual++;
        nrTurnos++;

        for (int i = turnoAtual; i <= programadores.size(); i++) {
            if (turnoAtual >= programadores.size()) { // Verifica se é o ultimo jogador
                turnoAtual = 0;
                i = 0;
            }
            if (programadores.get(turnoAtual).getEstado().equals("Derrotado")) {
                turnoAtual++;
            } else {
                break;
            }
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
        // Verifica se algum jogador chegou ao fim
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
        int jogadoresImpedidosMover = 0;
        for (Programmer programmer : getProgrammers(false)) {
            if(programmer.preso) {
                jogadoresImpedidosMover++;
            }
        }
        if(jogadoresImpedidosMover == getProgrammers(false).size()) {
            this.empatado = true;
            return true;
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
        if(this.empatado){
            resultados.add("O jogo terminou empatado");
            resultados.add("");
            resultados.add("Participantes:");
            programadores.sort(Comparator.comparing((Programmer programador1) -> programador1.getName()));
            programadores.sort(Comparator.comparing((Programmer programador1) -> programador1.getPosicao()).reversed());
            for (Programmer programador : programadores) {
                if (programadores.get(0).getId() == programador.getId()) { // percorre os jogadores
                    continue;
                }
                resultados.add(programador.getName() + " : " + programador.getPosicao() + " : " + programador.historicoAbismos.get(programador.historicoAbismos.size()-1)); // adiciona o nome e o valor da posição
            }
            return resultados;
        }
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
        linha2.setText("João Antas a22002629");
        credits.add(linha2);

        return credits;
    }

    public boolean saveGame(File file) {
        try {
            ObjectOutputStream gravarArq = new ObjectOutputStream(new FileOutputStream(file));
            gravarArq.writeInt(nrTurnos);
            gravarArq.writeInt(nrCasas);
            gravarArq.writeInt(turnoAtual);
            gravarArq.writeObject(programadores);
            gravarArq.writeObject(ferramentas);
            gravarArq.writeObject(abismos);
            gravarArq.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean loadGame(File file) {
        try {
            ObjectInputStream objIs = new ObjectInputStream(new FileInputStream(file));
            nrTurnos = objIs.readInt();
            nrCasas = objIs.readInt();
            turnoAtual = objIs.readInt();
            programadores = (ArrayList<Programmer>) objIs.readObject();
            ferramentas = (ArrayList<Ferramenta>) objIs.readObject();
            abismos = (ArrayList<Abismo>) objIs.readObject();

            objIs.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Abismo criarAbismo(String info, int posicao) {
        return switch (info) {
            case "0" -> new ErroDeSintaxe(0, posicao);
            case "1" -> new ErroDeLogica(1, posicao);
            case "2" -> new Exceptions(2, posicao);
            case "3" -> new FileNotFoundException(3, posicao);
            case "4" -> new Crash(4, posicao);
            case "5" -> new DuplicatedCode(5, posicao);
            case "6" -> new EfeitosSecundarios(6, posicao);
            case "7" -> new BlueScreenOfDeath(7, posicao);
            case "8" -> new CicloInfinito(8, posicao);
            case "9" -> new SegmentationFault(9, posicao);
            case "10" -> new VamosFazerContas(10, posicao);
            default -> null;
        };
    }

    private Ferramenta criarFerramentas(String info, int posicao) {
        return switch (info) {
            case "0" -> new Heranca(0, posicao);
            case "1" -> new ProgramacaoFuncional(1, posicao);
            case "2" -> new Unitarios(2, posicao);
            case "3" -> new TratamentoDeExcepcoes(3, posicao);
            case "4" -> new IDE(4, posicao);
            default -> new AjudaDoProfessor(5, posicao);
        };
    }
}