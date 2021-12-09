import java.io.File
import kotlin.math.abs


val locations = File("day7.txt").readLines()[0].split(',').map { it.toInt() }

// bad kotlin programmer bad
val max = locations.maxOrNull()!!
val min = locations.minOrNull()!!

val fuelCost = MutableList(max + 1){0}

// This is quadratic time
// I wonder if a binary search works for this
for(i in min .. max) {
    fuelCost[i] = locations.sumOf { abs(i - it) }
}

println(fuelCost.minOrNull())