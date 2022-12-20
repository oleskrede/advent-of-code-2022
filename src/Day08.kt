fun main() {

    val maxTreeHeight = 9

    data class Tree(
        val height: Int,
        var visible: Boolean = false,
        val viewingDistances: MutableList<Int> = mutableListOf(),
    )

    class Forest(input: List<String>) {
        val trees = input.map { line ->
            line.map { height ->
                Tree(height.digitToInt())
            }
        }

        init {
            for (horizontalLine in trees) {
                updateTreeValuesForTreeLine(horizontalLine)
                updateTreeValuesForTreeLine(horizontalLine.reversed())
            }

            val transposedTrees = trees.flatten()
                .withIndex()
                .groupBy { it.index % trees.size }
                .map { indexed -> indexed.value.map { it.value } }

            for (verticalLine in transposedTrees) {
                updateTreeValuesForTreeLine(verticalLine)
                updateTreeValuesForTreeLine(verticalLine.reversed())
            }
        }

        // Stupid function name for a function that does the grunt work of solving part 1 and 2
        fun updateTreeValuesForTreeLine(trees: List<Tree>) {
            var highest = -1
            val viewingDistances = MutableList(10) { 0 } // pos = height, value = position
            for ((position, tree) in trees.withIndex()) {
                if (tree.height > highest) {
                    highest = tree.height
                    tree.visible = true
                }
                val positionsOfHigherTrees = viewingDistances.takeLast(maxTreeHeight - tree.height + 1)
                val viewingDistance = position - positionsOfHigherTrees.max()
                tree.viewingDistances.add(viewingDistance)
                viewingDistances[tree.height] = position
            }
        }
    }


    fun part1(input: List<String>): Int {
        val forest = Forest(input)
        return forest.trees.flatten().count { it.visible }
    }

    fun part2(input: List<String>): Int {
        val forest = Forest(input)
        return forest.trees.flatten().map { tree ->
            tree.viewingDistances.fold(1) { t, r ->
                t * r
            }
        }.max()
    }

    val testInput = readInput("Day08_test")
    test(part1(testInput), 21)
    test(part2(testInput), 8)

    val input = readInput("Day08")
    timedRun { part1(input) }
    timedRun { part2(input) }
}
