package com.example.catslearningcompose.ui.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catslearningcompose.model.ItemsRepository
import com.example.catslearningcompose.model.LoadResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = EditItemViewModel.Factory::class)
class EditItemViewModel @AssistedInject constructor(
    @Assisted private val index: Int,
    private val itemsRepository: ItemsRepository
): ViewModel() {

    private val _stateFlow = MutableStateFlow<LoadResult<ScreenState>>(LoadResult.Loading)
    val stateFlow: StateFlow<LoadResult<ScreenState>> = _stateFlow

    private val _eventStateFlow = MutableStateFlow(EventState())
    val eventStateFlow: StateFlow<EventState> = _eventStateFlow

    init {
        loadItem()
    }

    fun loadItem() {
        viewModelScope.launch {
            _stateFlow.value = LoadResult.Loading
            _stateFlow.value = try {
                LoadResult.Success(ScreenState(itemsRepository.getByIndex(index)))
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    fun update(newTitle: String) {
        val loadResult = _stateFlow.value
        if (loadResult !is LoadResult.Success) return
        viewModelScope.launch {
            try {
                showProgress(loadResult)
                itemsRepository.update(index, newTitle)
                goBack()
            } catch (e: Exception) {
                hideProgress(loadResult)
                _eventStateFlow.update { oldState ->
                    oldState.copy(error = e)
                }
            }
        }
    }

    fun onExitHandled() {
        _eventStateFlow.update { oldState ->
            oldState.copy(exit = null)
        }
    }

    fun onErrorHandled() {
        _eventStateFlow.update { oldState ->
            oldState.copy(error = null)
        }
    }

    private fun showProgress(
        loadResult: LoadResult.Success<ScreenState>
    ) {
        val currentScreenState = loadResult.data
        val updatedScreenState = currentScreenState.copy(isEditInProgress = true)
        _stateFlow.value = LoadResult.Success(updatedScreenState)
    }

    private fun hideProgress(
        loadResult: LoadResult.Success<ScreenState>
    ) {
        val currentScreenState = loadResult.data
        val updatedScreenState = currentScreenState.copy(isEditInProgress = false)
        _stateFlow.value = LoadResult.Success(updatedScreenState)
    }

    private fun goBack() {
        _eventStateFlow.update { oldState ->
            oldState.copy(exit = Unit)
        }
    }

    data class EventState(
        val exit: Unit? = null,
        val error: Exception? = null
    )

    data class ScreenState(
        val loadedItem: String,
        val isEditInProgress: Boolean = false
    )

    @AssistedFactory
    interface Factory {
        fun create(index: Int): EditItemViewModel
    }
}
