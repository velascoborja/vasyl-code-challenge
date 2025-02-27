package com.vasyl.code.vasylchallenge.presentation.procedures.list

import androidx.lifecycle.viewModelScope
import com.vasyl.code.vasylchallenge.domain.model.Procedure
import com.vasyl.code.vasylchallenge.domain.repository.procedure.ProcedureRepository
import com.vasyl.code.vasylchallenge.presentation.Screen
import com.vasyl.code.vasylchallenge.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProcedureListViewModel @Inject constructor(
    private val proceduresRepository: ProcedureRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
        .onStart { fetchData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiState()
        )

    fun fetchData() {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }
        viewModelScope.launch {
            try {
                val procedures = proceduresRepository.getProcedures()
                _uiState.update {
                    it.copy(isLoading = false, procedures = procedures)
                }
            } catch (t: Throwable) {
                // TODO: Print log
                _uiState.update {
                    it.copy(isLoading = false, error = t.message)
                }
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val procedures: List<Procedure> = listOf(),
        val selectedProcedure: String? = null
    )

    fun onEvent(event: Event) {
        when (event) {
            Event.OpenFavouritesPage -> {
                navigate(Screen.FavouriteProcedures)
            }

            Event.TryFetchDataAgain -> {
                fetchData()
            }

            is Event.OpenProcedureDetails -> {
                _uiState.update {
                    it.copy(selectedProcedure = event.procedureUuid)
                }
            }

            is Event.ToggleFavouriteProcedure -> {
                toggleFavouriteProcedure(event.procedureUuid, event.isFavorite)
            }

            is Event.ClearSelectedProcedure -> {
                _uiState.update {
                    it.copy(selectedProcedure = null)
                }
            }
        }
    }

    private fun toggleFavouriteProcedure(procedureUuid: String, favorite: Boolean) {
        viewModelScope.launch {
            proceduresRepository.toggleProcedureFavoriteStatus(procedureUuid, favorite)
            _uiState.update {
                it.copy(procedures = it.procedures.map { procedure ->
                    if (procedure.uuid == procedureUuid) procedure.copy(isFavorite = favorite) else procedure
                })
            }
        }
    }

    sealed interface Event {
        data class OpenProcedureDetails(val procedureUuid: String) : Event
        data object TryFetchDataAgain : Event
        data object OpenFavouritesPage : Event
        data class ToggleFavouriteProcedure(val procedureUuid: String, val isFavorite: Boolean) : Event
        data object ClearSelectedProcedure : Event
    }
}