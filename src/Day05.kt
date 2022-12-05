fun main() {
    /*
    I didn't bother with parsing the stacks as they were specified in the initial input.
    So I rotated them clockwise.
    E.g. the example input became:
        ZN
        MCD
        P

        move 1 from 2 to 1
        ...
     */

    fun parseInput(input: List<String>): Pair<MutableList<MutableList<Char>>, List<List<Int>>> {
        val stackEndIndex = input.indexOfFirst { it.isBlank() }
        val stacks = input.take(stackEndIndex).map { it.toMutableList() }.toMutableList()
        val instructions = input.drop(stackEndIndex + 1)
            .map {
                it.replace("move ", "")
                    .replace("from ", "")
                    .replace("to ", "")
                    .split(" ").map { value -> value.toInt() }
            }
        return Pair(stacks, instructions)
    }

    fun part1(input: List<String>): String {
        val (stacks, instructions) = parseInput(input)
        instructions.forEach {
            val numCrates = it[0]
            val from = it[1] - 1
            val to = it[2] - 1
            for (i in 0 until numCrates) {
                val crate = stacks[from].removeLast()
                stacks[to].add(crate)
            }
        }
        return stacks.map { it.last() }.joinToString(separator = "")
    }

    fun part2(input: List<String>): String {
        val (stacks, instructions) = parseInput(input)
        instructions.forEach {
            val numCrates = it[0]
            val from = it[1] - 1
            val to = it[2] - 1

            val movingCrates = stacks[from].takeLast(numCrates)
            val remainingCrates = stacks[from].dropLast(numCrates)
            stacks[from] = remainingCrates.toMutableList()
            stacks[to].addAll(movingCrates)
        }
        return stacks.map { it.last() }.joinToString(separator = "")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    test(part1(testInput), "CMZ")
    test(part2(testInput), "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
