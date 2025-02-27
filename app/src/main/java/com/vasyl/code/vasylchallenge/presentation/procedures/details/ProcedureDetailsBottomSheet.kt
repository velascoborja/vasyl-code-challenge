package com.vasyl.code.vasylchallenge.presentation.procedures.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vasyl.code.vasylchallenge.R
import com.vasyl.code.vasylchallenge.domain.model.Phase
import com.vasyl.code.vasylchallenge.domain.model.ProcedureDetails
import com.vasyl.code.vasylchallenge.presentation.theme.VasylChallengeTheme
import com.vasyl.code.vasylchallenge.presentation.utils.DateTimeConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcedureDetailsBottomSheet(
    procedureUuid: String,
    onDismissRequest: () -> Unit,
    viewModel: ProcedureDetailsViewModel = hiltViewModel(),
    onFavouriteClicked: (String, Boolean) -> Unit
) {
    LaunchedEffect(procedureUuid) {
        viewModel.onEvent(ProcedureDetailsViewModel.Event.FetchData(procedureUuid))
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        content = {
            ProcedureDetailsBottomContent(
                procedureUuid = procedureUuid,
                uiState = uiState,
                sendEvent = viewModel::onEvent,
                onFavouriteClicked = onFavouriteClicked
            )
        })
}

@Composable
private fun ProcedureDetailsBottomContent(
    procedureUuid: String,
    uiState: ProcedureDetailsViewModel.UiState,
    sendEvent: (ProcedureDetailsViewModel.Event) -> Unit,
    onFavouriteClicked: (String, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 64.dp)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else if (uiState.error != null) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp), text = uiState.error
            )
            Button(modifier = Modifier.padding(top = 24.dp),
                content = {
                    Text(stringResource(R.string.procedure_list_try_again_action))
                },
                onClick = { sendEvent(ProcedureDetailsViewModel.Event.FetchData(procedureUuid = procedureUuid)) })
        } else {
            uiState.procedureDetails?.let { procedureDetails ->
                ProcedureDetailsContent(
                    procedureDetails = procedureDetails,
                    onFavouriteClicked = onFavouriteClicked
                )
            }
        }
    }
}

@Composable
private fun ProcedureDetailsContent(
    procedureDetails: ProcedureDetails,
    onFavouriteClicked: (String, Boolean) -> Unit
) {

    var isFavourite by remember { mutableStateOf(procedureDetails.isFavourite) }

    Box(Modifier.fillMaxWidth()) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            model = ImageRequest.Builder(LocalContext.current).data(procedureDetails.cardUrl).build(),
            contentDescription = "feed image",
            contentScale = ContentScale.Crop
        )
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = {
                onFavouriteClicked(procedureDetails.uuid, isFavourite)
                isFavourite = !isFavourite
            }) {
            Icon(
                painter = painterResource(
                    if (isFavourite) R.drawable.ic_favourite else R.drawable.ic_favorite_border
                ),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "favourite"
            )
        }
    }

    Text(
        text = procedureDetails.name ?: ""
    )

    procedureDetails.duration?.let {
        Text(
            text = it.toString()
        )
    }

    procedureDetails.datePublished?.let {
        Text(
            text = DateTimeConverter.formatDate(it)
        )
    }

    procedureDetails.phases?.let { phases ->
        LazyRow {
            items(phases) {
                PhaseItem(it)
            }
        }
    }
}

@Composable
fun PhaseItem(phase: Phase) {
    Column(
        modifier = Modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(60.dp),
            model = ImageRequest.Builder(LocalContext.current).data(phase.iconUrl).build(),
            contentDescription = "feed image",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = phase.name ?: "",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}


@Preview
@Composable
private fun ProcedureDetailsPreview() {
    VasylChallengeTheme {
        ProcedureDetailsBottomContent("",
            ProcedureDetailsViewModel.UiState(), {}, { _, _ -> })
    }
}