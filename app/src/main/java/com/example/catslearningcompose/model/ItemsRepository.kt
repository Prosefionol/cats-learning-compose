package com.example.catslearningcompose.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ItemsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val itemsFlow =
        MutableStateFlow(List(size = START_LIST_SIZE) { "Item ${it + 1}" })

    suspend fun add(title: String) {
        delay(1000)
        itemsFlow.update { it + title }
    }

    fun getItems(): Flow<List<String>> = itemsFlow.onStart { delay(1500) }

    companion object {
        const val START_LIST_SIZE: Int = 5
    }
}
