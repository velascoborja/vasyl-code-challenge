package com.vasyl.code.vasylchallenge.presentation.procedures.favourite

import androidx.lifecycle.viewModelScope
import com.vasyl.code.vasylchallenge.domain.model.Procedure
import com.vasyl.code.vasylchallenge.domain.repository.procedure.ProcedureRepository
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
class FavouriteProcedureListViewModel @Inject constructor(
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

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(procedures = proceduresRepository.getFavouriteProcedures())
            }
        }
    }

    data class UiState(
        val procedures: List<Procedure> = listOf(),
        val selectedProcedure: String? = null
    )

    fun onEvent(event: Event) {
        when (event) {
            is Event.RemoveFavouriteProcedure -> {
                removeFavouriteProcedure(event.procedureUuid)
            }

            is Event.OpenProcedureDetails -> {
                _uiState.update {
                    it.copy(selectedProcedure = event.procedureUuid)
                }
            }

            is Event.ClearSelectedProcedure -> {
                _uiState.update {
                    it.copy(selectedProcedure = null)
                }
            }
        }
    }

    private fun removeFavouriteProcedure(procedureUuid: String) {
        viewModelScope.launch {
            proceduresRepository.toggleProcedureFavoriteStatus(procedureUuid, false)
            _uiState.update {
                it.copy(
                    procedures = it.procedures.filter { procedure ->
                        procedure.uuid != procedureUuid
                    },
                    selectedProcedure = null
                )
            }
        }
    }

    sealed interface Event {
        data class RemoveFavouriteProcedure(val procedureUuid: String) : Event
        data class OpenProcedureDetails(val procedureUuid: String) : Event
        data object ClearSelectedProcedure : Event
    }
}