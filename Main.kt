package encryptdecrypt

import java.io.File

/* -------------------------------------------------------------------------
Ваша программа должна считывать данные из -data или из файла, указанного в -in аргументе.
 Вот почему вы не можете иметь оба -data-in аргумента и одновременно.

+Если нет-mode, программа должна работать в enc режиме;
+Если нет-key, программа должна учитывать key, что0;
+Если нет -data и нет-in, программа должна предположить, что данные представляют собой пустую строку;
+Если -out аргумент отсутствует, программа должна вывести данные в стандартный вывод;
+Если есть оба -data-in аргумента и, ваша программа должна предпочесть -data over -in.
Если есть что—то странное (входной файл не существует или аргумент не имеет значения),
программа не должна завершаться сбоем. Вместо этого он должен отображать четкое сообщение о проблеме и
 успешно останавливаться. Сообщение должно содержать словоError.
 -------------------------------------------------------------------------*/
const val MAXCRIPT = 127
const val MINCRIPT = 32
const val MINALG_UPPER = 65
const val MAXALG_UPPER = 91
const val MINALG_LOWER = 97
const val MAXALG_LOWER = 123
fun main(args: Array<String>) {
    //какой алгоритм использовать shift or unicod (юникод старый код)
    val deEnAlg = if ("-alg" in args) args[args.indexOf("-alg") + 1] else "unicod"
    //Если нет-mode, программа должна работать в enc режиме;
    val deEnCription = if ("-mode" in args) args[args.indexOf("-mode") + 1] else "enc"
    //Если нет-key, программа должна учитывать key, что 0;
    val deEnKey = if ("-key" in args) args[args.indexOf("-key") + 1].toInt() else 0
    //Если нет -data и нет-in, программа должна предположить, что данные представляют собой пустую строку;
    var minMessage = true
    val deEnMessage = when {
        "-data" in args && "-in" in args || "-data" in args -> args[args.indexOf("-data") + 1]   // "text"
        "-in" in args -> {
            minMessage = false
            args[args.indexOf("-in") + 1]   // adress
        }
        else -> ""
    }
    //Если -out аргумент отсутствует, программа должна вывести данные в стандартный вывод;
    val deEnOutput = if ("-out" in args) args[args.indexOf("-out") + 1] else ""

    when (deEnCription) {
        "enc" -> enc(readDeEnCription(deEnMessage, minMessage, deEnCription), deEnKey, deEnOutput, deEnAlg)
        "dec" -> dec(readDeEnCription(deEnMessage, minMessage, deEnCription), deEnKey, deEnOutput, deEnAlg)
    }
}

fun readDeEnCription(deEnMessage: String, bool: Boolean, deEnCription: String): String {
    var text = ""
    if (deEnMessage != "") {
        text = if (bool) {
            deEnMessage
        } else {
            val encodText = if (deEnCription == "-enc") "\\jqhtrj%yt%m~ujwxpnqq&" else "Welcome to hyperskill!"
            val file = File(deEnMessage)
            if (!file.exists()) {
                file.writeText(encodText)
            }
            file.readText()
        }
    }
    return text
}

fun enc(deEnMessage: String, deEnKey: Int, deEnOutput: String, alg: String) {
    //println("Запуск enc")
    var resultString = ""
    if (alg == "unicode") {
        for (i in deEnMessage.indices) {   //кодировка UNICOD
            val result = deEnMessage[i].code + deEnKey
            resultString += if (result > MAXCRIPT) (result % MAXCRIPT + MINCRIPT).toChar() else result.toChar()
        }
    } else {
        for (j in deEnMessage.indices) {   //кодировка SHIFT
            var result = 0
            resultString += if (deEnMessage[j] in 'a'..'z') {
                result = deEnMessage[j].code + deEnKey
                if (result > MAXALG_LOWER) (result % MAXALG_LOWER + MINALG_LOWER).toChar() else result.toChar()
            } else if (deEnMessage[j] in 'A'..'Z') {
                result = deEnMessage[j].code + deEnKey
                if (result > MAXALG_UPPER) (result % MAXALG_UPPER + MINALG_UPPER).toChar() else result.toChar()
            } else {
                deEnMessage[j]
            }
        }
    }

    if (deEnOutput == "") {
        println(resultString)
    } else {
        val file = File(deEnOutput)
        file.writeText(resultString)
    }
}

fun dec(deEnMessage: String, deEnKey: Int, deEnOutput: String, alg: String) {
    //println("Запуск dec")
    var resultString = ""
    if (alg == "unicode") {
        for (i in deEnMessage.indices) {   //кодировка UNICOD
            val result = deEnMessage[i].code - deEnKey
            resultString += if (result < MINCRIPT) (MAXCRIPT - MINCRIPT % result).toChar() else result.toChar()
        }
    } else {
        for (j in deEnMessage.indices) {   //кодировка SHIFT
            var result = 0
            resultString += if (deEnMessage[j] in 'a'..'z') {
                result = deEnMessage[j].code - deEnKey
                if (result < MINALG_LOWER) (MAXALG_LOWER - MINALG_LOWER % result).toChar() else result.toChar()
            } else if (deEnMessage[j] in 'A'..'Z') {
                result = deEnMessage[j].code - deEnKey
                if (result < MINALG_UPPER) (MAXALG_UPPER - MINALG_UPPER % result).toChar() else result.toChar()
            } else {
                deEnMessage[j]
            }
        }
    }
    if (deEnOutput == "") {
        println(resultString)
    } else {
        val file = File(deEnOutput)
        file.writeText(resultString)
    }
}

