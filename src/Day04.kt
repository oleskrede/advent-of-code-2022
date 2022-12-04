fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.split(",") }
            .count { (a, b) ->
                val (aStart, aEnd) = a.split("-").map { it.toInt() }
                val (bStart, bEnd) = b.split("-").map { it.toInt() }
                val aInB = aStart >= bStart && aEnd <= bEnd
                val bInA = bStart >= aStart && bEnd <= aEnd
                aInB || bInA
            }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(",") }
            .count { (a, b) ->
                val (aStart, aEnd) = a.split("-").map { it.toInt() }
                val (bStart, bEnd) = b.split("-").map { it.toInt() }
                aStart in bStart..bEnd || bStart in aStart..aEnd
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    test(part1(testInput), 2)
    test(part2(testInput), 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
