package pt.ulusofona.lp2.deisiGreatGame

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

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
    return when (lista) {
        "[]", null -> "Inexistent player"
        else -> lista.substring(1, lista.length - 1)
    }

}

fun getPlayersByLanguage(manager: GameManager, args: List<String>): String? {
    return manager.programadores.filter { it.linguagens.contains(args[1]) }.sortedBy { it.languages.count() }
        .joinToString(",") { it.getName() }
}

fun getPolyglots(manager: GameManager, args: List<String>): String? {
    val lista: String =
        manager.programadores.filter { it.getLinguagens().count() > 1 }.sortedBy { it.getLinguagens().count() }
            .joinToString("\n") { it.getName() + ":" + it.getLinguagens().count() }

    return when (lista) {
        "" -> "Inexistent player"
        else -> lista

    }
}

fun getMostUsedPositions(manager: GameManager, args: List<String>): String? {
    val numeroPosicoes = ArrayList<Int>()
    manager.programadores.map { it.posicoes }.forEach { it.forEach { numeroPosicoes.add(it) } }

    return numeroPosicoes.filter { it != 1 }
        .sortedWith { p1, p2 -> Collections.frequency(numeroPosicoes, p1) - Collections.frequency(numeroPosicoes, p2) }
        .reversed().distinct()
        .take(args[1].toInt()).joinToString("\n") { it.toString() + ":" + Collections.frequency(numeroPosicoes, it) }
}

fun getMostUsedAbysses(manager: GameManager, args: List<String>): String? {
    val listaAbismos = HashSet<String>()
    manager.abismos.forEach { listaAbismos.add(it.titulo) }
    val abismosUsados = ArrayList<String>()
    manager.programadores.map { it.historicoAbismos }.forEach { it.forEach { abismosUsados.add(it) } }

    return listaAbismos.sortedWith { a1, a2 ->
        Collections.frequency(abismosUsados, a1) - Collections.frequency(
            abismosUsados,
            a2
        )
    }.distinct().reversed()
        .take(Integer.parseInt(args[1])).joinToString("\n") { it + ":" + Collections.frequency(abismosUsados, it) }

}

fun postMove(manager: GameManager, args: List<String>): String? {
    manager.moveCurrentPlayer(Integer.parseInt(args[1]))
    val aviso = manager.reactToAbyssOrTool()
    return when (aviso) {
        null -> "OK"
        else -> aviso
    }
}

fun postAbyss(manager: GameManager, args: List<String>): String? {
    val abismosIgual = manager.abismos.map { it.posicao }.filter { it == Integer.parseInt(args[2]) }
    val ferramentaIgual = manager.ferramentas.map { it.posicao }.filter { it == Integer.parseInt(args[2]) }
    return when {
        abismosIgual.isNotEmpty() || ferramentaIgual.isNotEmpty() -> "Position is occupied"
        else -> {
            manager.abismos.add(manager.criarAbismo(args[1], Integer.parseInt(args[2])))
            "OK"
        }
    }
}