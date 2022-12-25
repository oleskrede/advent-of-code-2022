import kotlin.math.abs
import kotlin.math.max

fun main() {

    val toDirection = mapOf(
        "R" to Direction.RIGHT,
        "U" to Direction.UP,
        "L" to Direction.LEFT,
        "D" to Direction.DOWN,
    )

    fun isTouching(a: Position, b: Position): Boolean {
        val dx = abs(a.x - b.x)
        val dy = abs(a.y - b.y)
        return max(dx, dy) <= 1
    }

    fun simulateRope(ropeSize: Int, input: List<String>): Int {
        val knots = MutableList(ropeSize) { Position(0, 0) }
        val tailPositions = mutableSetOf(knots.last())
        for (headMotion in input) {
            val tokens = headMotion.trim().split(" ")
            val headDir = toDirection[tokens.first()]!!
            val headDist = tokens[1].toInt()

            for (headStep in 0 until headDist) {
                knots[0] = Position(knots[0].x + headDir.dx, knots[0].y + headDir.dy)
                for (i in 1 until knots.size) {
                    val prevKnot = knots[i - 1]
                    val knot = knots[i]
                    if (!isTouching(prevKnot, knot)) {
                        // Find and normalize direction from knot to prevKnot
                        var dx = prevKnot.x - knot.x
                        var dy = prevKnot.y - knot.y
                        dx /= max(1, abs(dx))
                        dy /= max(1, abs(dy))
                        knots[i] = Position(knot.x + dx, knot.y + dy)
                    }
                }
                tailPositions.add(knots.last())
            }
        }
        return tailPositions.size
    }

    fun part1(input: List<String>): Int {
        return simulateRope(2, input)
    }

    fun part2(input: List<String>): Int {
        return simulateRope(10, input)
    }

    val testInput = readInput("Day09_test")
    val testInput2 = readInput("Day09_test2")
    test(part1(testInput), 13)
    test(part2(testInput2), 36)

    val input = readInput("Day09")
    timedRun { part1(input) }
    timedRun { part2(input) }
}

private enum class Direction(val dx: Int, val dy: Int) {
    UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0)
}

private data class Position(val x: Int, val y: Int)