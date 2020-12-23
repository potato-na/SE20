// 2200104004 Amiya Tomoaki
// Output decorating the perfect Christmas tree of the input height.
// reference (https://www.sheffield.ac.uk/news/nr/debenhams-christmas-tree-formula-1.227810)

import kotlin.math.*

fun perfect_Christmas_tree(h: Int) {
    print("Number of baubles: ")
    println((sqrt(17.0) / 20 * h).roundToInt())
    print("Height of star or fairy (cm): ")
    println((h / 10))
    print("Length of tinsel (cm): ")
    println((13 * PI / 8 * h).roundToInt())
    print("Length of lights (cm): ")
    println((PI * h).roundToInt())
}

fun main() {
    print("Input the height of your Christmas tree (cm): ")
    val input = readLine()
    if(!input.isNullOrBlank()){
        perfect_Christmas_tree(input.toInt())
    }else{
        println("ERROR")
    }
}
