package pt.ulusofona.lp2.deisiGreatGame;

public class InvalidInitialBoardException extends Exception {
    String message;

    public InvalidInitialBoardException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isInvalidAbyss() {
        if (message.contains("abismo")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInvalidTool() {
        if (message.contains("ferramenta")) {
            return true;
        } else {
            return false;
        }
    }

}
