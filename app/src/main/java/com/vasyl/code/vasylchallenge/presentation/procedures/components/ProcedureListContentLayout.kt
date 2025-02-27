package com.vasyl.code.vasylchallenge.presentation.procedures.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vasyl.code.vasylchallenge.domain.model.Procedure
import com.vasyl.code.vasylchallenge.presentation.utils.BuildFlavor
import com.vasyl.code.vasylchallenge.presentation.utils.getBuildFlavor

@Composable
fun ProcedureListContentLayout(
    modifier: Modifier = Modifier,
    buildFlavor: BuildFlavor = getBuildFlavor(),
    procedures: List<Procedure>,
    onItemClicked: (Procedure) -> Unit,
    onFavouriteClicked: (Procedure) -> Unit
) {
    when (buildFlavor) {
        BuildFlavor.CLIENT_1 -> {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(procedures) {
                    ProcedureItem(
                        procedure = it,
                        onItemClicked = onItemClicked,
                        onFavouriteClicked = onFavouriteClicked
                    )
                }
            }
        }

        BuildFlavor.CLIENT_2 -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(procedures) {
                    ProcedureItem(
                        procedure = it,
                        onItemClicked = onItemClicked,
                        onFavouriteClicked = onFavouriteClicked
                    )
                }
            }
        }
    }
}