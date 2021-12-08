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

    val count = ventLocations.filter { (_, v) ->
        v >= 2
    }.count()

    println(count)
}