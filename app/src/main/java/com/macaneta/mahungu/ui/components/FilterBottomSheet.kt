package com.macaneta.mahungu.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.macaneta.mahungu.data.model.FeedQuery
import com.macaneta.mahungu.data.model.api.Category
import com.macaneta.mahungu.data.model.api.Country
import com.macaneta.mahungu.data.model.api.SortBy
import java.util.Locale.getDefault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    currentFilters: FeedQuery,
    onFiltersChanged: (FeedQuery) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState
) {
    var selectedCategory by remember { mutableStateOf(currentFilters.category) }
    var selectedCountry by remember { mutableStateOf(currentFilters.country) }
    var selectedLanguage by remember { mutableStateOf(currentFilters.language) }
    var selectedSortBy by remember { mutableStateOf(currentFilters.sortBy) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding()
        ) {
            Text("Filters", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            FilterSection(title = "Sort By") {
                SortBy.entries.forEach { sort ->
                    FilterChip(
                        selected = selectedSortBy == sort,
                        onClick = { selectedSortBy = sort },
                        label = { Text(sort.order.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                getDefault()
                            ) else it.toString()
                        }) },
                        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                    )
                }
            }

            FilterSection(title = "Category") {
                Category.entries.forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                        label = { Text(cat.category.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                getDefault()
                            ) else it.toString()
                        }) },
                        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                    )
                }
            }

            FilterSection(title = "Country") {
                Country.entries.take(15).forEach { country ->
                    FilterChip(
                        selected = selectedCountry == country,
                        onClick = { selectedCountry = country },
                        label = { Text(country.code.uppercase()) },
                        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = {
                    onFiltersChanged(
                        currentFilters.copy(
                            category = selectedCategory,
                            country = selectedCountry,
                            language = selectedLanguage,
                            sortBy = selectedSortBy
                        )
                    )
                }) {
                    Text("Apply Filters")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FilterSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow {
            content()
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}