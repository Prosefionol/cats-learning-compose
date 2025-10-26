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
        verifyNoDuplicates(title)
        itemsFlow.update { it + title }
    }

    fun getItems(): Flow<List<String>> = itemsFlow.onStart { delay(1500) }

    suspend fun getByIndex(index: Int): String {
        delay(500)
        if (index == 0) throw LoadDataException()
        return itemsFlow.value[index]
    }

    suspend fun update(index: Int, newTitle: String) {
        delay(1000)
        verifyNoDuplicates(newTitle, index)
        itemsFlow.update { oldList->
            oldList.toMutableList().apply { set(index, newTitle) }
        }
    }

    private fun verifyNoDuplicates(title: String, index: Int = -1) {
        val duplicatedItemIndex = itemsFlow.value.indexOf(title)
        if (duplicatedItemIndex != -1 && duplicatedItemIndex != index) {
            throw DuplicateException(title)
        }
    }

    companion object {
        const val START_LIST_SIZE: Int = 5
    }
}
