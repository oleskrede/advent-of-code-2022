fun main() {

    class Tree(
        val height: Int,
        var visible: Boolean = false
    )


    fun updateVisibilityForTreeLine(trees: List<Tree>) {
        var highest = -1
        for (tree in trees) {
            if (tree.height > highest) {
                highest = tree.height
                tree.visible = true
            }
        }
    }


    fun part1(input: List<String>): Int {
        val trees = input.map { line ->
            line.map { height ->
                Tree(height.digitToInt())
            }
        }

        for (horizontalLine in trees) {
            updateVisibilityForTreeLine(horizontalLine)
            updateVisibilityForTreeLine(horizontalLine.reversed())
        }

        val transposedTrees = trees.flatten()
            .withIndex()
            .groupBy { it.index % trees.size }
            .map { indexed -> indexed.value.map { it.value } }

        for (verticalLine in transposedTrees) {
            updateVisibilityForTreeLine(verticalLine)
            updateVisibilityForTreeLine(verticalLine.reversed())
        }

        return trees.flatten().count { it.visible }
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day08_test")
    test(part1(testInput), 21)
    test(part2(testInput), 1)

    val input = readInput("Day08")
    timedRun { part1(input) }
    timedRun { part2(input) }
}

private enum class Direction(val dx: Int, val dy: Int) {
    UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0)
}