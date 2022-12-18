fun main() {

fun messageStartIndex(input: String, markerSize: Int): Int {
    return input.windowed(markerSize)
        .indexOfFirst { it.toSet().size == markerSize } + markerSize
}


    fun part1(input: String): Int {
        return messageStartIndex(input, 4)
    }

    fun part2(input: String): Int {
        return messageStartIndex(input, 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test").first()
    test(part1(testInput), 10)
    test(part2(testInput), 29)

    val input = readInput("Day06").first()
    println(part1(input))
    println(part2(input))
}
