import java.io.File
import kotlin.math.abs


val locations = File("day7.txt").readLines()[0].split(',').map { it.toInt() }

// bad kotlin programmer bad
val max = locations.maxOrNull()!!
val min = locations.minOrNull()!!

val fuelCost = MutableList(max + 1){0}

// This is n^2 time
// I wonder if a binary search works for this
// or we can keep track of the min moves so far and bail if we go over that
for(i in min .. max) {
    fuelCost[i] = locations.sumOf { (0 .. abs(i - it)).sum() }
}

println(fuelCost.minOrNull())