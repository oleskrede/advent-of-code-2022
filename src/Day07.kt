fun main() {

    fun part1(input: List<String>): Long {
        val maxDirSize = 100_000
        val fs = FileSystem()
        fs.recreateFromBrowsingHistory(input)
        val dirsWithSizeBelowMax = fs.findDirs { it.size < maxDirSize }
        return dirsWithSizeBelowMax.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    test(part1(testInput), 95437L)
    test(part2(testInput), 0)

    val input = readInput("Day07")
    timedRun { part1(input) }
    timedRun { part2(input) }
}

private class FileSystem {

    private val root = Directory("/", null)
    private var currentDirectory = root

    fun recreateFromBrowsingHistory(history: List<String>) {
        history.forEach { line ->
            val tokens = line.trim().split(" ")
            when (tokens.first()) {
                "$" -> {
                    if (tokens[1] == "cd") {
                        this.changeDirectory(tokens[2])
                    }
                    // we can ignore $ ls. Either we change directory or we parse the result of $ ls
                }

                "dir" -> this.makeDirectory(tokens[1])
                else -> this.createFile(tokens[1], tokens[0].toInt())
            }
        }
    }

    fun changeDirectory(name: String) {
        currentDirectory = when (name) {
            "/" -> root
            ".." -> currentDirectory.parent!!
            else -> currentDirectory.subDirectories[name]!!
        }
    }

    fun makeDirectory(name: String) {
        require(currentDirectory.subDirectories[name] == null) { "Directory $name already exists." }
        currentDirectory.subDirectories[name] = Directory(name, currentDirectory)
    }

    fun createFile(name: String, size: Int) {
        require(currentDirectory.files[name] == null) { "File $name already exists." }
        currentDirectory.files[name] = size
        currentDirectory.updateSize(size)
    }

    // Returns all directories that fullfill the given predicate, starting from and including root
    fun findDirs(predicate: (Directory) -> Boolean): List<Directory> {
        return findDirs(root, predicate)
    }

    // Returns all directories that fullfill the given predicate, starting from and including the given directory
    fun findDirs(dir: Directory, predicate: (Directory) -> Boolean): List<Directory> {
        val result = mutableListOf<Directory>()
        if (predicate(dir)) {
            result.add(dir)
        }
        dir.subDirectories.forEach {
            result.addAll(findDirs(it.value, predicate))
        }
        return result
    }

    data class Directory(
        val name: String,
        val parent: Directory?,
        val subDirectories: MutableMap<String, Directory> = mutableMapOf(),
        val files: MutableMap<String, Int> = mutableMapOf(),

        ) {
        var size: Long = 0
            private set

        fun updateSize(delta: Int) {
            size += delta
            parent?.updateSize(delta)
        }
    }
}

