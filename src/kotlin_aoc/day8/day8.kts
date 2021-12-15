import java.io.File
import java.util.*

val lines: List<Pair<List<String>, List<String>>> = File("day8_sample.txt").readLines().map { line ->
    val leftRight = line.split(" | ")
    Pair(
        leftRight[0].split(' ').filter { it.isNotBlank() },
        leftRight[1].split(' ').filter { it.isNotBlank() }
    )
}

val total = lines.map{ line ->
    val key = createLineKey(line.first.map { it.toList().sorted() })
    line.second
        .map { it.toList().sorted().joinToString() }
        .map { key[it] }
        .joinToString(separator = "")
        .toInt()
}.sum()

println(total)

//this is some bullshit
// is there an obvious way to solve this one that i'm missing???
fun createLineKey(input: List<List<Char>>): Map<String?, Int> {
    val one = input.first { it.size == 2 }
    val seven = input.first { it.size == 3 }
    val four = input.first { it.size == 4 }
    val eight = input.first { it.size == 7 }
    // 3 is identifiable as sharing all 3 chars as 7
    val three =
        input.first { it.size == 5 && it.contains(seven[0]) && it.contains(seven[1]) && it.contains(seven[2]) }

    val a = seven.first { !one.contains(it) }
    // on a plane with no wifi... i need to get better at regex huh
    val g = three.first { !seven.contains(it) && !four.contains(it) }
    val b = four.first { !three.contains(it) }

    val five = input.first { it.size == 5 && it.contains(a) && it.contains(b) && it.contains(g) }

    val d = five.first { !three.contains(it) }
    val c = five.first { one.contains(it) }
    val two = input.first { it.size == 5 && it != three && it != five }
    val e = two.first{ !three.contains(it) && !five.contains(it)}

    val zero = input.firstOrNull(){it.size == 6 && !it.contains(d)}
    val nine = input.first{it.size == 6 && it.contains(c)}
    val six = input.firstOrNull(){it.size == 6 && !it.contains(c)}

    // This is now our key.
    return mapOf(
        zero?.joinToString() to 0,
        one.joinToString() to 1,
        two.joinToString() to 2,
        three.joinToString() to 3,
        four.joinToString() to 3,
        five.joinToString() to 5,
        six?.joinToString() to 6,
        seven.joinToString() to 7,
        eight.joinToString() to 8,
        nine.joinToString() to 9
    )
}
// Part 2 algorithm
//
//    a
//   b  c
//     d
//   e  f
//     g
//

// 2 digits is 1   cf
// 3 digits is 7   acf
// 4 digits is 4   bdcf
// 7 digits is 8   abcdefg
// 5 digits is 2, 3, 5      acdeg   acdfg  abdfg
// 6 digits is 0, 6, 9      abcefg  abdefg abcdfg

// 7 and 1 shows A
// 4 and 3 shows D
// 4, 3, 1 shows B
// 3 is a 5 digit with A,D NOT B
// 5 is a 5 digit with A, B,D
// 2 is the other 5 digit
// E is in 2, but not 3,5
// 0 is 6 digit but no d
// c is not in 5 but is in 1
// 9 is 6 digit with c
// 6 is 6 digit without c