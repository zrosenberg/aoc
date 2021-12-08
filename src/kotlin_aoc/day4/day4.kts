package kotlin_aoc.day4

import java.io.File
import java.lang.RuntimeException
import kotlin.math.floor

// Part 1 algorithm:

// Read the first line, the input guesses
// Create a map of guess -> list of coordinates
// populate the map by going through all maps one time
// Create a counter for each row and each column of each board, of guesses there.
// For each guess, check the list of coordinates of matching entries.
// Then just increment the counter for the rows and columns of each.
// Once we have a row or column with the full number of guesses, we are done.
// Total up the remaining numbers to calculate the score.

// With this solution, we don't need to allocate a bunch of matrices.
// We also don't need to iterate through a bunch of matrices. It should be pretty efficient

// It was also very easy to extend for the second part. Comparing get winning and losing scores,
// the diff is just that we keep going and look until all boards have won. Score calculation is also reusable.

// It's almost like I'm more comfortable with kotlin over python...

main()

fun main() {
    val input = File("day4.txt").readLines()

    // Some of the entries have multiple spaces so we can't just split on that. ' '
    val boardSize = input[2].split("\\s+".toRegex()).size

    // There is a blank line between each board. Discard the input, then count each board as N lines + 1
    val numBoards = (input.size - 1) / (boardSize + 1)

    // Parse input of first line into ints
    val guesses = input[0].split(",").map { Integer.parseInt(it) }
    println("Guesses: $guesses")

    // populate a map of number -> list of coordinate points where that number is located.
    val coordinates: MutableMap<Int, MutableList<Pair<Int, Int>>> = mutableMapOf()
    input.filterIndexed { i, line ->
        // Skip the first line, guesses, and empty lines
        line.isNotBlank() && i != 0
    }.map {
        it.split("\\s+".toRegex()).filter{ it.isNotBlank() }.map { int -> Integer.parseInt(int) }
    }.forEachIndexed { y, line ->
        // Y is the index for the line, which is also our global Y coordinate.
        line.forEachIndexed { x, s ->
            // X is more complicated, since the columns are shared by multiple boards.
            // We will give them an offset.
            val colIndex = x +  (boardSize * getBoard(y, boardSize))
            if (coordinates[s] == null) {
                coordinates[s] = mutableListOf()
            }
            val coord = Pair(colIndex, y)
            coordinates[s]?.add(coord)
        }
    }

    val (winningGuess, winningBoard, guessIndex)= getWinningGuess(boardSize, numBoards, guesses, coordinates)
    println(winningGuess)
    println(winningBoard)

    // Go through the guesses that we didn't get to - those are the numbers that remain.
    val remainingSum = calculateScore(guesses, guessIndex, coordinates, boardSize, winningBoard)

    println(remainingSum)
    println(remainingSum * winningGuess)

    val (lastGuess, losingestBoard, lastGuessIndex) = getLosingGuess(boardSize, numBoards, guesses, coordinates)
    val losingScore = calculateScore(guesses, lastGuessIndex, coordinates, boardSize, losingestBoard)

    println(losingScore)
    println(lastGuess)
    println(losingScore * lastGuess)

}

fun getWinningGuess(boardSize: Int, numBoards: Int, guesses: List<Int>, coordinates: MutableMap<Int, MutableList<Pair<Int, Int>>>): WinningGuess {
    // Create a list of numbers of hits we've seen, for each row and each column.
    val rowHits = MutableList((boardSize * numBoards) + 1) { 0 }
    val colHits = MutableList((boardSize * numBoards) + 1) { 0 }

    // Count the number of hits in each row/column. if we get to the size of the board, thats a bingo.
    guesses.forEachIndexed{ i, guess ->
        coordinates[guess]?.forEach {
            rowHits[it.first]++
            colHits[it.second]++
            if (rowHits[it.first] == boardSize || colHits[it.second] == boardSize) {
                // Either a row or a column has bingo!
                // compute which board it belonged to
                val winningBoard = getBoard(it.first, boardSize)
                return WinningGuess(guess, winningBoard, i)
            }
        }
    }

    throw RuntimeException("you fucked up")
}

fun getLosingGuess(boardSize: Int, numBoards: Int, guesses: List<Int>, coordinates: MutableMap<Int, MutableList<Pair<Int, Int>>>): WinningGuess {
    // Create a list of numbers of hits we've seen, for each row and each column.
    val rowHits = MutableList((boardSize * numBoards) + 1) { 0 }
    val colHits = MutableList((boardSize * numBoards) + 1) { 0 }
    val winningBoards = MutableList(numBoards){false} // keep track of how many boards have won so far AND which. If we are on the last one, then we return.
    var boardWins = 0

    // Count the number of hits in each row/column. if we get to the size of the board, thats a bingo.
    guesses.forEachIndexed{ i, guess ->
        coordinates[guess]?.forEach {
            val currentBoard = getBoard(it.first, boardSize)
            rowHits[it.first]++
            colHits[it.second]++
            // we don't need to keep playing boards that have won
            if (!winningBoards[currentBoard]) {
                if (rowHits[it.first] == boardSize || colHits[it.second] == boardSize) {
                    // Either a row or a column has bingo!
                    winningBoards[currentBoard] = true
                    boardWins++
                    if(boardWins == numBoards){
                        return kotlin_aoc.day4.Day4.WinningGuess(guess, currentBoard, i)
                    }
                }
            }

        }
    }

    throw RuntimeException("you fucked up")
}

// Given an X or Y coordinate, return the board it belongs to.
// Because we are offsetting the X coordinates, the math is the same
fun getBoard(coord: Int, boardSize: Int): Int = floor(coord.toDouble() / boardSize).toInt()

// DC for returning a triple of guess, index, and board. We need all those to calculate score.
data class WinningGuess(val winningGuess: Int, val winningBoard: Int, val guessIndex: Int)

fun calculateScore(guesses: List<Int>, guessIndex: Int, coordinates: MutableMap<Int, MutableList<Pair<Int, Int>>>, boardSize: Int, winningBoard: Int) =
        guesses.slice(guessIndex + 1 until guesses.size)
                .filter {
                    // sum up only the numbers present in the winning board
                    coordinates[it]?.any { coordPair -> floor(coordPair.first.toDouble() / boardSize).toInt() == winningBoard }
                            ?: false
                }
                .sum()