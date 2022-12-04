import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.contracts.contract

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun test(actual: Int, expected: Int) {
    if (actual == expected) {
        println("Test OK")
    } else{
        throw IllegalStateException("Expected <$expected>, but got <$actual>")
    }
}