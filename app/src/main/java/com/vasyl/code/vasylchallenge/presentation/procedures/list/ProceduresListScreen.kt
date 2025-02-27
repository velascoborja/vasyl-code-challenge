package com.vasyl.code.vasylchallenge.presentation.procedures.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vasyl.code.vasylchallenge.R
import com.vasyl.code.vasylchallenge.domain.model.Procedure
import com.vasyl.code.vasylchallenge.presentation.components.ProgressBarDialog
import com.vasyl.code.vasylchallenge.presentation.procedures.components.ProcedureListContentLayout
import com.vasyl.code.vasylchallenge.presentation.procedures.details.ProcedureDetailsBottomSheet
import kotlinx.coroutines.launch


@Composable
fun ProceduresListRoute(
    navController: NavHostController,
    viewModel: ProcedureListViewModel = hiltViewModel<ProcedureListViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigateFlow.collect {
            navController.navigate(it)
        }
    }

    ProceduresListScreen(
        uiState = uiState, sendEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProceduresListScreen(
    uiState: ProcedureListViewModel.UiState,
    sendEvent: (ProcedureListViewModel.Event) -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val addedToFavourites = stringResource(R.string.added_to_favourites)
    val removedFromFavourites = stringResource(R.string.removed_from_favourites)

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.procedures_list_title))
            },
                actions = {
                    IconButton(onClick = {
                        sendEvent(ProcedureListViewModel.Event.OpenFavouritesPage)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_favourite),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "favourite_page"
                        )
                    }
                })
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            if (uiState.error != null) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp), text = uiState.error
                    )
                    Button(modifier = Modifier.padding(top = 24.dp), content = {
                        Text(stringResource(R.string.procedure_list_try_again_action))
                    }, onClick = { sendEvent(ProcedureListViewModel.Event.TryFetchDataAgain) })
                }

            }
            ProcedureListContentLayout(
                modifier = Modifier.padding(horizontal = 24.dp),
                procedures = uiState.procedures,
                onItemClicked = {
                    sendEvent(ProcedureListViewModel.Event.OpenProcedureDetails(it.uuid))
                },
                onFavouriteClicked = {
                    sendEvent(ProcedureListViewModel.Event.ToggleFavouriteProcedure(it.uuid, !it.isFavorite))
                    scope.launch {
                        snackbarHostState.showSnackbar(if (it.isFavorite) removedFromFavourites else addedToFavourites)
                    }
                }
            )

            if (uiState.isLoading) {
                ProgressBarDialog()
            }
        }
    }

    if (uiState.selectedProcedure != null) {
        ProcedureDetailsBottomSheet(
            procedureUuid = uiState.selectedProcedure,
            onDismissRequest = {
                sendEvent(ProcedureListViewModel.Event.ClearSelectedProcedure)
            },
            onFavouriteClicked = { uuid, isFavourite ->
                sendEvent(ProcedureListViewModel.Event.ToggleFavouriteProcedure(uuid, !isFavourite))
                scope.launch {
                    snackbarHostState.showSnackbar(if (isFavourite) removedFromFavourites else addedToFavourites)
                }
            }
        )
    }
}

@Preview
@Composable
fun ProceduresListScreenPreview() {
    MaterialTheme {
        ProceduresListScreen(uiState = ProcedureListViewModel.UiState(
            procedures = listOf(
                Procedure(
                    uuid = "test", iconUrl = null, name = "test", phases = listOf("test"), isFavorite = false
                )
            )
        ), sendEvent = {})
    }
}