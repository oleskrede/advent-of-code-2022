fun main() {

    class Monkey(
        val id: Int,
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val test: (Long) -> Int,
        val monkies: List<Monkey>,
    ) {
        var activityCounter = 0

        fun takeTurn() {
            while (items.isNotEmpty()) {
                activityCounter++
                var worryLevel = items.removeAt(0)
                worryLevel = operation(worryLevel)
                worryLevel /= 3
                val receivingMonkeyId = test(worryLevel)
                monkies[receivingMonkeyId].items.add(worryLevel)
            }
        }
    }

    fun toOperation(operatorToken: String, operandToken: String): (Long) -> Long {
        val operator = if (operatorToken == "+") { a: Long, b: Long -> a + b } else { a: Long, b: Long -> a * b }
        if (operandToken == "old") {
            return { old: Long -> operator(old, old) }
        } else {
            val constant = operandToken.toLong()
            return { old: Long -> operator(old, constant) }
        }
    }

    fun part1(input: List<String>): Int {
        val monkies = mutableListOf<Monkey>()
        input.filter { !it.isBlank() }.windowed(size = 6, step = 6).forEach { monkeyInfo ->
            val id = monkeyInfo[0].removeSuffix(":").split(" ").last().toInt()
            val startingItems = monkeyInfo[1].split(": ").last().split(", ").map { it.toLong() }
            val operationTokens = monkeyInfo[2].split(" ").takeLast(2)
            val operation = toOperation(operationTokens[0], operationTokens[1])
            val testValue = monkeyInfo[3].split(" ").last().toLong()
            val testTrueMonkey = monkeyInfo[4].split(" ").last().toInt()
            val testFalseMonkey = monkeyInfo[5].split(" ").last().toInt()
            val test =
                { worryLevel: Long -> if (worryLevel % testValue == 0L) testTrueMonkey else testFalseMonkey }
            val monkey = Monkey(id, startingItems.toMutableList(), operation, test, monkies)
            monkies.add(monkey)
        }

        repeat(20) {
            monkies.forEach { monkey -> monkey.takeTurn() }
        }

        val sortedActivityCounters = monkies.map { it.activityCounter }.sortedDescending()
        val monkeyBusinesss = sortedActivityCounters[0] * sortedActivityCounters[1]

        return monkeyBusinesss
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day11_test")
    test(part1(testInput), 10605)
    test(part2(testInput), 1)

    val input = readInput("Day11")
    timedRun { part1(input) }
    timedRun { part2(input) }
}
