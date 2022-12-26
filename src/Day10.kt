fun main() {

    val part2Solution = """
        ##..##..##..##..##..##..##..##..##..##..
        ###...###...###...###...###...###...###.
        ####....####....####....####....####....
        #####.....#####.....#####.....#####.....
        ######......######......######......####
        #######.......#######.......#######.....
    """.trimIndent()

    class CPU {
        var registerX = 1
            private set
        private var cycle = 1

        private val cycleValues = mutableListOf(0)

        fun runProgram(input: List<String>): List<Int> {
            for (line in input) {
                val tokens = line.trim().split(" ")
                val operation = tokens.first()
                when (operation) {
                    "noop" -> completeCycle()
                    "addx" -> {
                        val v = tokens[1].toInt()
                        repeat(2) {
                            completeCycle()
                        }
                        registerX += v
                    }
                }
            }
            return cycleValues
        }

        private fun completeCycle() {
            cycleValues.add(registerX)
            cycle++
        }
    }

    class CRT(val width: Int = 40, val height: Int = 6) {
        fun draw(cycleValues: List<Int>): List<String> {
            return cycleValues.take(width * height)
                .mapIndexed { i, x ->
                    val pixelPos = i % width
                    if (x in pixelPos - 1..pixelPos + 1) {
                        '#'
                    } else {
                        '.'
                    }
                }.windowed(size = width, step = width)
                .map { it.joinToString(separator = " ") }
        }
    }

    fun part1(input: List<String>): Int {
        val cycleValues = CPU().runProgram(input)
        var signalStrengtSum = 0
        for (cycle in 20..220 step 40) {
            signalStrengtSum += cycleValues[cycle] * cycle
        }
        return signalStrengtSum
    }

    fun part2(input: List<String>) {
        val cycleValues = CPU().runProgram(input).drop(1) // Drop cycle 0
        val image = CRT().draw(cycleValues)
        println(image.joinToString(separator = "\n"))
    }

    val testInput = readInput("Day10_test")
    test(part1(testInput), 13140)

    println("Part 2 testsolution:")
    println(part2Solution)
    println()
    println("Part 2 testresult:")
    println(part2(testInput))

    val input = readInput("Day10")
    timedRun { part1(input) }
    println()
    println("Part 2 result:")
    timedRun { part2(input) }
}
