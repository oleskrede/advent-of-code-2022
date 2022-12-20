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

    fun part1(input: List<String>): Int {
        var head = Position(0, 0)
        var tail = Position(0, 0)
        val tailPositions = mutableSetOf(tail)
        for (headMotion in input) {
            val tokens = headMotion.trim().split(" ")
            val headDir = toDirection[tokens.first()]!!
            val headDist = tokens[1].toInt()
            head = Position(head.x + headDist * headDir.dx, head.y + headDist * headDir.dy)

            while (!isTouching(head, tail)) {
                // Find and normalize direction from tail to head
                var dx = head.x - tail.x
                var dy = head.y - tail.y
                dx /= max(1, abs(dx))
                dy /= max(1, abs(dy))

                tail = Position(tail.x + dx, tail.y + dy)
                tailPositions.add(tail)
            }
        }
        return tailPositions.size
    }

    fun part2(input: List<String>): Int {
        return 1
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