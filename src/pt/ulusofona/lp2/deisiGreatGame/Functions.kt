package pt.ulusofona.lp2.deisiGreatGame


enum class CommandType(var nome: String) {
    GET_PLAYER("getPlayer"), GET_PLAYERS_BY_LANGUAGE("GET PLAYERS_BY_LANGUAGE"),
    GET_POLYGLOTS("GET POLYGLOTS"), GET_MOST_USED_POSITIONS("GET MOST_USED_POSITIONS"),
    GET_MOST_USED_ABYSSES("GET MOST_USED_ABYSSES"), POST_MOVE("POST MOVE"), POST_ABYSS("POST ABYSS");

    @Override
    override fun toString(): String {
        return nome
    }
}

fun router(comando : CommandType, args: List<String>) : (GameManager, List<String>) -> String? {
    return when (comando.toString()) {
        "getPlayer" -> ::getPlayer
        "GET PLAYERS_BY_LANGUAGE" -> ::getPlayersByLanguage
        "GET POLYGLOTS" -> ::getPolyglots
        "GET MOST_USED_POSITIONS" -> ::getMostUsedPositions
        "GET MOST_USED_ABYSSES" -> ::getMostUsedAbysses
        "POST MOVE" -> ::getMove
        "POST ABYSS" -> ::getAbyss
        else -> ::erro
    }
}

fun erro(manager: GameManager, args: List<String>): String?{
    return "o comando indicado não existe"
}

fun getPlayer(manager: GameManager, args: List<String>): String?{
    val lista : String? =  manager.programadores.filter{it.getName().contains(args[1])}.toString()
    if (lista == null){
        return "Inexistent player"
    }else{
        return lista
    }

}

fun getPlayersByLanguage(manager: GameManager, args: List<String>): String?{
    var lista : List<Programmer>? =  manager.programadores.filter{(it.getLinguagens().filter{it == args[1]}[0] == args[0])}

    lista?.forEach { if (it == lista.last()){ print(it.getName() + ", ") }else{ print(it.getName()) } }
    return lista.toString()
}

fun getPolyglots(manager: GameManager, args: List<String>): String?{
    var lista : List<Programmer>? =  manager.programadores.filter{it.getLinguagens().count() > 1}.sortedBy { it.getLinguagens().count() }
    return null
}

fun getMostUsedPositions(manager: GameManager, args: List<String>): String?{
    return null
}

fun getMostUsedAbysses(manager: GameManager, args: List<String>): String?{
    return null
}

fun getMove(manager: GameManager, args: List<String>): String?{
    return null
}

fun getAbyss(manager: GameManager, args: List<String>): String?{
    return null
}