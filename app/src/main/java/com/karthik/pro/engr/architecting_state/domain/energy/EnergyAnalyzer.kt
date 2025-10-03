package com.karthik.pro.engr.architecting_state.domain.energy

object EnergyAnalyzer {
    fun findLongestStretch(houseTypes: List<String>): StretchResult {
        val result = IntArray(2)
        val map = HashMap<Int, Int>()
        map[0] = -1
        var prefixSum = 0
        var maxLen = 0
        houseTypes.forEachIndexed { index, element ->
            prefixSum += when (element.lowercase()) {
                "producer" -> 1

                "consumer" -> -1

                else -> 0
            }
            if (map.containsKey(prefixSum)) {
                val len = index - map[prefixSum]!!
                if (len > maxLen) {
                    result[0] = map[prefixSum]!!
                    result[1] = index
                    maxLen = len
                }
            } else {
                map[prefixSum] = index
            }

            println(
                "index -> $index  prefixSum-> $prefixSum map-> ${
                    map.entries.joinToString(prefix = "{", postfix = "}") {
                        "[${it.key} -> ${it.value}]"
                    }
                } result-> ${result.joinToString(", ")}")
        }

//        "The Longest Stretch Houses Starts from ${result[0] + 1} to ${result[1] + 1}"
        return StretchResult(result[0] + 1, result[1], maxLen)
    }
}

data class StretchResult(val startIndex: Int, val endIndex: Int, val length: Int)