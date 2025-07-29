package com.behqo.turquoisenews.feature.articles.domain.intractor

import android.util.Log
import com.behqo.turquoisenews.core.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "RoundRobinUseCase"

/**
 * Interleaves multiple buckets in round‑robin order and drops duplicates by a key.
 */
class RoundRobinUseCase @Inject constructor(@DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher) {

    /**
     * @param T the element type
     * @param K the key type used for deduplication
     * @param buckets a List of MutableLists to interleave in order (first list yields first element, then second, etc.)
     * @param keySelector returns a key for each T to use for deduplication (only first occurrence kept)
     * @return a single List<T> interleaved in rounds, with duplicates removed
     */
    suspend operator fun <T, K> invoke(
        buckets: MutableList<List<T>>,
        keySelector: (T) -> K
    ): List<T> = withContext(defaultDispatcher) {
        buckets.removeIf { list -> list.isEmpty() }

        if (buckets.isEmpty()) {
            Log.d(TAG, "All buckets were empty")
            return@withContext emptyList()
        }

        val indices = IntArray(buckets.size)
        val maxIndex = buckets.maxOf { it.lastIndex }
        val seen = mutableSetOf<K>()
        val result = mutableListOf<T>()

        while (indices.max() < maxIndex) {
            for ((i, list) in buckets.withIndex()) {
                val size = list.size

                // skip bucket as it is already completely included
                if (indices[i] >= size) continue

                // skip already seen items
                while (indices[i] < size && !seen.add(keySelector(list[indices[i]]))) indices[i]++

                if (indices[i] < size) {
                    result += list[indices[i]]
                    indices[i]++
                }
            }
        }

        return@withContext result
    }
}