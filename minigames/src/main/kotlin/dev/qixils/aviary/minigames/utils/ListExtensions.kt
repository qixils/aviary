package dev.qixils.aviary.minigames.utils

data class SplitList<T>(val first: List<T>, val second: List<T>)

fun <T> Iterable<T>.split(sizeOfFirst: Int): SplitList<T> {
	val first = mutableListOf<T>()
	val second = mutableListOf<T>()
	for ((i, item) in withIndex()) {
		if (i < sizeOfFirst) {
			first.add(item)
		} else {
			second.add(item)
		}
	}
	return SplitList(first, second)
}

fun <T> Iterable<T>.segment(size: Int): Iterable<List<T>> {
	// TODO: generator
	var segment = mutableListOf<T>()
	val segments = mutableListOf<List<T>>(segment)
	for ((i, item) in withIndex()) {
		if (i % size == 0) {
			segment = mutableListOf()
			segments.add(segment)
		}
		segment.add(item)
	}
	return segments
}

fun <T> MutableList<T>.removeFirst(amount: Int): List<T> {
	val removed = mutableListOf<T>()
	for (i in 0 until amount) {
		removed.add(removeFirst())
	}
	return removed
}
