package com.example.catslearningcompose.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catslearningcompose.model.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
): ViewModel() {

    private val _stateFlow = MutableStateFlow(ScreenState())
    val stateFlow: StateFlow<ScreenState> = _stateFlow

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    fun add(title: String) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isAddInProgress = true) }
            itemsRepository.add(title)
            goBack()
        }
    }

    private suspend fun goBack() {
        _exitChannel.send(Unit)
    }

    data class ScreenState(
        val isAddInProgress: Boolean = false
    )
}
