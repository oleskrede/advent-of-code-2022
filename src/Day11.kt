fun main() {

    class Monkey(
        val id: Int,
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val handleWorryLevel: (Long) -> Long,
        val test: (Long) -> Int,
        val monkies: List<Monkey>,
    ) {
        var activityCounter = 0L

        fun takeTurn() {
            while (items.isNotEmpty()) {
                activityCounter++
                var worryLevel = items.removeAt(0)
                worryLevel = operation(worryLevel)
                worryLevel = handleWorryLevel(worryLevel)
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

    fun handleMonkeyBusiness(input: List<String>, numRounds: Int, handleWorryLevel: (Long) -> Long): Long {
        val monkies = mutableListOf<Monkey>()
        var commonFactor = 1L
        input.filter { it.isNotBlank() }.windowed(size = 6, step = 6).forEach { monkeyInfo ->
            val id = monkeyInfo[0].removeSuffix(":").split(" ").last().toInt()
            val startingItems = monkeyInfo[1].split(": ").last().split(", ").map { it.toLong() }
            val operationTokens = monkeyInfo[2].split(" ").takeLast(2)
            val operation = toOperation(operationTokens[0], operationTokens[1])
            val testValue = monkeyInfo[3].split(" ").last().toLong()
            commonFactor *= testValue
            val testTrueMonkey = monkeyInfo[4].split(" ").last().toInt()
            val testFalseMonkey = monkeyInfo[5].split(" ").last().toInt()
            val test =
                { worryLevel: Long -> if (worryLevel % testValue == 0L) testTrueMonkey else testFalseMonkey }
            val monkey = Monkey(id, startingItems.toMutableList(), operation, handleWorryLevel, test, monkies)
            monkies.add(monkey)
        }

        repeat(numRounds) {
            monkies.forEach { monkey -> monkey.takeTurn() }
        }

        val sortedActivityCounters = monkies.map { it.activityCounter }.sortedDescending()

        return sortedActivityCounters[0] * sortedActivityCounters[1]
    }

    fun part1(input: List<String>): Long {
        return handleMonkeyBusiness(
            input = input,
            numRounds = 20,
            handleWorryLevel = { wl: Long -> wl / 3 }
        )
    }

    fun part2(input: List<String>): Long {

        val commonFactor = input
            .filter { it.contains("divisible by") }
            .map { it.split(" ").last().toLong() }
            .reduce { acc, testValue -> acc * testValue }

        return handleMonkeyBusiness(
            input = input,
            numRounds = 10_000,
            handleWorryLevel = { wl: Long -> wl % commonFactor }
        )
    }

    val testInput = readInput("Day11_test")
    test(part1(testInput), 10605L)
    test(part2(testInput), 2713310158L)

    val input = readInput("Day11")
    timedRun { part1(input) }
    timedRun { part2(input) }
}
