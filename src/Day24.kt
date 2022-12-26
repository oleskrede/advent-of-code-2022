fun main() {

    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day10_test")
    test(part1(testInput), 1)
    test(part2(testInput), 1)

    val input = readInput("Day09")
    timedRun { part1(input) }
    timedRun { part2(input) }
}
