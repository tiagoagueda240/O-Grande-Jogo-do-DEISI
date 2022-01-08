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
        return message.contains("abismo");
    }

    public boolean isInvalidTool() {
        return message.contains("ferramenta");
    }

    public String getTypeId(){
        String[] info = getMessage().split("/");
        return info.length > 1 ? info[0] : null;
    }

}
