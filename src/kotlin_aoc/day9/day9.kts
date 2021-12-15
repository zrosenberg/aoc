import java.io.File

val lines = File("day9.txt").readLines()

val data: List<List<Int>> = lines.map { line -> line.toCharArray().map { it.toString().toInt() } }
val remainingPoints = mutableMapOf<point, Int>()

data.forEachIndexed { y, line -> line.forEachIndexed { x, value ->  remainingPoints[point(x, y)] =  value} }

// part 1
//val riskLevels = data.mapIndexed{ y, line ->
//    line.filterIndexed { x, _ ->
//        isLowPoint(x, y, data)
//    }.sumOf { it + 1 }
//}.sum()
//
//println(riskLevels)
val basins = mutableListOf<basin>()

for(y in data.indices) {
    for(x in 0 until data[y].size) {
        val p = point(x, y)
        // This means we've already added this one to a basin, or its a 9 that we hit
        if(remainingPoints[p] == null) {
            continue
        }
        val b = basin(mutableListOf())
        addToBasin(p, b)
        if(b.points.size > 0) {
            basins.add(b)
        }
    }
}

basins.sortByDescending { it.points.size }
println(basins[0].points.size)
println(basins[1].points.size)
println(basins[2].points.size)
println(basins[0].points.size * basins[1].points.size * basins[2].points.size)


fun addToBasin(p: point, b: basin) {
    // If we've been here before, bail. it's already been counted
    if (remainingPoints[p] == null) {
        return
    }
    // check it off our list so we don't double count in the future
    remainingPoints.remove(p)
    // check that we aren't at the boundary of a basin
    if (getValue(p.x, p.y, data) >= 9) {
        return
    }
    // Add this point to our current basin
    b.points.add(p)

    // recurse in all 4 directions, adding points to the current basin.
    // We will stop in that direction if we hit a 9, a boundary, or somewhere we've been; otherwise we will also add their neighbors
    addToBasin(point(p.x+1, p.y), b)
    addToBasin(point(p.x, p.y+1), b)
    addToBasin(point(p.x-1, p.y), b)
    addToBasin(point(p.x, p.y-1), b)
}

fun isLowPoint(x: Int, y: Int, map: List<List<Int>>):Boolean {
    // cheating and using 10 for any edge value, since thats higher than any digit 0-9
    val above = if(y == 0 ) { 10 } else { map[y-1][x] }
    val below = if(y >= map.size -1) { 10 } else { map[y+1][x] }
    val right = if(x >= map[0].size -1) { 10 } else { map[y][x+1] }
    val left = if(x == 0) { 10 } else { map[y][x-1] }
    val point = map[y][x]

    return point < above && point < below && point < right && point < left
}

fun getValue(x: Int, y: Int, map: List<List<Int>>):Int {
    // cheating and returning 9 for edges so they dont get added to basins
    // This means that this function is the only place for part 2 I have to check for edges
    if(y < 0 ) { return 9 }
    if(y >= map.size ) { return 9 }
    if(x >= map[0].size ) { return 9 }
    if(x < 0) { return 9 }
    return map[y][x]
}

data class point(val x: Int, val y: Int)
data class basin(val points: MutableList<point>)