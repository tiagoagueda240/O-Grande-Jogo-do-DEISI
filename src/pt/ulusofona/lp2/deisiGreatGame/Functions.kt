package pt.ulusofona.lp2.deisiGreatGame

import java.util.*
import kotlin.collections.ArrayList


enum class CommandType {
    GET,
    POST
}

fun router(): (CommandType) -> (GameManager, List<String>) -> String? {
    return { commandType -> comandos(commandType) }
}

fun comandos(manager: CommandType): (GameManager, List<String>) -> String? {
    when (manager) {
        CommandType.GET -> return { i1, i2 -> getFunctions(i1, i2) }
        CommandType.POST -> return { i1, i2 -> postFunctions(i1, i2) }
    }
}

fun postFunctions(manager: GameManager, args: List<String>): String? {
    when (args.get(0)) {
        "MOVE" -> return ::postMove.invoke(manager, args)
        "ABYSS" -> return ::postAbyss.invoke(manager, args)
        else -> return null
    }
}

fun getFunctions(manager: GameManager, args: List<String>): String? {
    when (args[0]) {
        "PLAYER" -> return ::getPlayer.invoke(manager, args)
        "PLAYERS_BY_LANGUAGE" -> return ::getPlayersByLanguage.invoke(manager, args)
        "POLYGLOTS" -> return ::getPolyglots.invoke(manager, args)
        "MOST_USED_POSITIONS" -> return ::getMostUsedPositions.invoke(manager, args)
        "MOST_USED_ABYSSES" -> return ::getMostUsedAbysses.invoke(manager, args)
        else -> return null
    }
}

fun getPlayer(manager: GameManager, args: List<String>): String? {
    val lista: String? = manager.programadores.filter { it.getName().contains(args[1]) }.toString()
    if (lista == null) {
        return "Inexistent player"
    } else {
        return lista
    }

}

fun getPlayersByLanguage(manager: GameManager, args: List<String>): String? {
    return manager.programadores.filter { it.linguagens.count()>=2}.sortedBy{ it.languages.count()}.joinToString{ it.getName()}
}

fun getPolyglots(manager: GameManager, args: List<String>): String? {
    val lista: String = manager.programadores.filter { it.getLinguagens().count() > 1 }.sortedBy { it.getLinguagens().count() }.joinToString("\n") { it.getName() + ":" + it.getLinguagens().count() }
    if (lista == "") {
        return "Inexistent player"
    } else {
        return lista
    }
}

fun getMostUsedPositions(manager: GameManager, args: List<String>): String? {
        val numeroPosicoes = ArrayList<Int>()
        manager.getProgrammers(true).map{it.getHistoricoPosicoes()}.forEach{it.forEach{numeroPosicoes.add(it)}}
        numeroPosicoes.removeIf{it == 1}
        numeroPosicoes.sortedWith{p1,p2 -> Collections.frequency(numeroPosicoes,p1) - Collections.frequency(numeroPosicoes,p2)}.reversed().distinct()
        return numeroPosicoes.take(Integer.parseInt(args[1])).joinToString("\n"){it.toString() + ":" + Collections.frequency(numeroPosicoes,it)}
}

fun getMostUsedAbysses(manager: GameManager, args: List<String>): String? {
    val listaAbismos = ArrayList<String>()
    manager.abismos.forEach { listaAbismos.add(it.titulo) }
    val abismosUsados = ArrayList<String>()
    manager.getProgrammers(true).map { it.historicoAbismos }.forEach { it.forEach { abismosUsados.add(it) } }

    listaAbismos.sortedWith { s1, s2 -> Collections.frequency(abismosUsados, s1) - Collections.frequency(abismosUsados, s2) }.reversed().distinct()
    return listaAbismos.take(args[1].toInt()).joinToString("\n") { it + ":" + Collections.frequency(abismosUsados, it) }
}

fun postMove(manager: GameManager, args: List<String>): String? {
    manager.moveCurrentPlayer(args[1].toInt())
    val aviso = manager.reactToAbyssOrTool()
    if (aviso == null) {
        return "OK"
    } else {
        return aviso
    }
}

fun postAbyss(manager: GameManager, args: List<String>): String? {
    val abismosIgual = manager.abismos.map { it.posicao }.filter { it == args[2].toInt() }
    val ferramentaIgual = manager.ferramentas.map { it.posicao }.filter { it == args[2].toInt() }
    if (abismosIgual.isNotEmpty() || ferramentaIgual.isNotEmpty()) {
        return "Position is occupied"
    } else {
        val abismoNovo = Abismo(args[1].toInt(), args[2].toInt())
        manager.abismos.add(abismoNovo)
        return "OK"
    }
}