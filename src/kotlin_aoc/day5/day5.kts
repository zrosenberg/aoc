import java.io.File
import kotlin.math.max
import kotlin.math.min

main()

fun main() {
    val input = File("day5.txt").readLines()

    // Maps a coordinate to the number of vents.
    // This way we don't have to allocate a huge matrix of empty space, just the hits.
    val ventLocations = mutableMapOf<Pair<Int, Int>, Int>()

    // Transform our input into a list of pairs of pairs
    val ventList = input.map {
        // split the from -> to in half
        val startEnd = it.split(" -> ")
                .map { coord ->
                    val xy = coord.split(",")
                    Pair(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]))
                }
        Pair(startEnd[0], startEnd[1])
    }

    // only horizontal or vertical for now
    ventList.filter { (start, end) ->
        start.first == end.first || start.second == end.second
    }.forEach { (start, end) ->
        for(x in min(start.first, end.first) .. max(start.first, end.first)) {
            for(y in min(start.second, end.second) .. max(start.second, end.second)) {
                val count = ventLocations.getOrDefault(Pair(x, y), 0)
                ventLocations[Pair(x,y)] = count + 1
            }
        }
    }

    addDiags(ventList, ventLocations)

    val count = ventLocations.filter { (_, v) ->
        v >= 2
    }.count()

    println(count)
}

fun incrementLocation(ventLocations: MutableMap<Pair<Int, Int>, Int>, x: Int, y: Int) {
    val count = ventLocations.getOrDefault(Pair(x, y), 0)
    ventLocations[Pair(x, y)] = count + 1
}

fun addDiags(ventList: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>, ventLocations: MutableMap<Pair<Int, Int>, Int>) {
    // If the X and Y of both ends are different, we got a diagonal.
    ventList.filter { (start, end) ->
        start.first != end.first && start.second != end.second
    }.forEach { (start, end) ->
        val xStart = min(start.first, end.first)
        val xEnd = max(start.first, end.first)

        // The Y start will be the one farthest to the left. Rely on the X to find it
        val yStart: Int;
        val yEnd: Int
        if(start.first < end.first) {
            yStart = start.second
            yEnd = end.second
        } else {
            yStart = end.second
            yEnd = start.second
        }

        // Then determine if we are going up or down diagonally
        val yOffset = if (yStart < yEnd) 1 else -1

        for(x in xStart ..  xEnd) {
            // the yOffset tells us up or down, use that to move
            val offset = x - xStart
            incrementLocation(ventLocations, x, yStart + (offset * yOffset))
        }
    }
}

