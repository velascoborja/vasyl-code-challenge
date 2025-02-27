package com.vasyl.code.vasylchallenge.presentation.procedures.details

import androidx.lifecycle.viewModelScope
import com.vasyl.code.vasylchallenge.domain.model.ProcedureDetails
import com.vasyl.code.vasylchallenge.domain.repository.procedure.ProcedureRepository
import com.vasyl.code.vasylchallenge.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProcedureDetailsViewModel @Inject constructor(
    private val proceduresRepository: ProcedureRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private fun fetchData(procedureUuid: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }
        viewModelScope.launch {
            try {
                val procedureDetails = proceduresRepository.getProcedureDetails(procedureUuid)
                _uiState.update {
                    it.copy(isLoading = false, procedureDetails = procedureDetails)
                }
            } catch (t: Throwable) {
                // TODO: Print log
                _uiState.update {
                    it.copy(isLoading = false, error = t.message)
                }
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.FetchData -> {
                fetchData(event.procedureUuid)
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val procedureDetails: ProcedureDetails? = null
    )

    sealed interface Event {
        data class FetchData(val procedureUuid: String) : Event
    }
}