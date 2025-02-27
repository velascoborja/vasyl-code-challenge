package com.vasyl.code.vasylchallenge.presentation.procedures.favourite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vasyl.code.vasylchallenge.R
import com.vasyl.code.vasylchallenge.presentation.procedures.components.ProcedureListContentLayout
import com.vasyl.code.vasylchallenge.presentation.procedures.details.ProcedureDetailsBottomSheet
import com.vasyl.code.vasylchallenge.presentation.theme.VasylChallengeTheme
import kotlinx.coroutines.launch


@Composable
fun FavouriteProceduresRoute(
    navController: NavHostController,
    viewModel: FavouriteProcedureListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FavouriteProceduresScreen(
        uiState = uiState,
        sendEvent = viewModel::onEvent,
        goBack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouriteProceduresScreen(
    uiState: FavouriteProcedureListViewModel.UiState,
    sendEvent: (FavouriteProcedureListViewModel.Event) -> Unit,
    goBack: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val removedFromFavourites = stringResource(R.string.removed_from_favourites)

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(stringResource(R.string.favourite_procedures_title))
        }, navigationIcon = {
            IconButton(onClick = goBack) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "back"
                )
            }
        })
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            ProcedureListContentLayout(
                modifier = Modifier.padding(horizontal = 24.dp),
                procedures = uiState.procedures,
                onItemClicked = {
                    sendEvent(FavouriteProcedureListViewModel.Event.OpenProcedureDetails(it.uuid))
                },
                onFavouriteClicked = {
                    sendEvent(FavouriteProcedureListViewModel.Event.RemoveFavouriteProcedure(it.uuid))
                    scope.launch {
                        snackbarHostState.showSnackbar(removedFromFavourites)
                    }
                }
            )
        }
    }

    if (uiState.selectedProcedure != null) {
        ProcedureDetailsBottomSheet(
            procedureUuid = uiState.selectedProcedure,
            onDismissRequest = {
                sendEvent(FavouriteProcedureListViewModel.Event.ClearSelectedProcedure)
            },
            onFavouriteClicked = { uuid, _ ->
                sendEvent(FavouriteProcedureListViewModel.Event.RemoveFavouriteProcedure(uuid))
                scope.launch {
                    snackbarHostState.showSnackbar(removedFromFavourites)
                }
            }
        )
    }
}


@Preview
@Composable
fun FavouriteProceduresScreenPreview() {
    VasylChallengeTheme {
        FavouriteProceduresScreen(
            uiState = FavouriteProcedureListViewModel.UiState(),
            {}, {}
        )
    }
}