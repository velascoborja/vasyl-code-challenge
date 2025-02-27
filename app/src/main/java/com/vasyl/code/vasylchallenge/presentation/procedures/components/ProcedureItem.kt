package com.vasyl.code.vasylchallenge.presentation.procedures.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vasyl.code.vasylchallenge.R
import com.vasyl.code.vasylchallenge.domain.model.Procedure

@Composable
fun ProcedureItem(
    procedure: Procedure,
    onItemClicked: (Procedure) -> Unit,
    onFavouriteClicked: (Procedure) -> Unit,
) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .border(color = MaterialTheme.colorScheme.primary, width = 1.dp, shape = MaterialTheme.shapes.medium)
            .clickable { onItemClicked(procedure) }
            .padding(12.dp)
    ) {

        Box(Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                model = ImageRequest.Builder(LocalContext.current).data(procedure.iconUrl).build(),
                contentDescription = "feed image",
                contentScale = ContentScale.Crop
            )
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = {
                    onFavouriteClicked(procedure)
                }) {
                Icon(
                    painter = painterResource(
                        if (procedure.isFavorite) R.drawable.ic_favourite else R.drawable.ic_favorite_border
                    ),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "favourite"
                )
            }
        }

        Text(
            text = procedure.name ?: ""
        )

        procedure.phases?.let {
            Text(
                text = stringResource(R.string.procedures_list_phase_count, procedure.phases.size)
            )
        }

    }
}