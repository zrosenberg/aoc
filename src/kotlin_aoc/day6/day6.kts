import java.io.File



val firstGenAges: List<Long> = File("day6.txt").readLines()[0].split(',').map { it.toLong() }

var ageCounts = MutableList(9){0L}

firstGenAges.forEach {
    ageCounts[it.toInt()] ++
}

for (i in 1 .. 256) {
    ageCounts = runGenerationSim(ageCounts)
}

println(ageCounts.sum())




fun runGenerationSim(ageCounts: MutableList<Long>): MutableList<Long> {
    val nextGen = MutableList<Long>(9){0L}
    ageCounts.forEachIndexed { age, count ->
        if (age == 0) {
            nextGen[6] += count
            nextGen[8] += count
        } else {
            nextGen[age - 1] += count
        }
    }
    return nextGen
}
