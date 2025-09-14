package com.example.catslearningcompose.model

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ItemsRepository @Inject constructor() {
    private val itemsFlow =
        MutableStateFlow(List(size = START_LIST_SIZE) { "Item ${it + 1}" })

    suspend fun add(title: String) {
        delay(1000)
        itemsFlow.update { it + title }
    }

    fun getItems(): Flow<List<String>> = itemsFlow.onStart { delay(1500) }

    suspend fun getByIndex(index: Int): String {
        delay(500)
        return itemsFlow.value[index]
    }

    suspend fun update(index: Int, newTitle: String) {
        delay(1000)
        itemsFlow.update { oldList->
            oldList.toMutableList().apply { set(index, newTitle) }
        }
    }

    companion object {
        const val START_LIST_SIZE: Int = 5
    }
}
