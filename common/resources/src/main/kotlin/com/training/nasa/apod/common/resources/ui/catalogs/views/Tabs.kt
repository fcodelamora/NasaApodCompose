package com.training.nasa.apod.common.resources.ui.catalogs.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView
import kotlinx.coroutines.launch
import java.time.Month

// Tabs

@Composable
fun DateMonthTabs(
    modifier: Modifier = Modifier,
    selectedMonth: Month? = null,
    disabledMonths: List<Month> = listOf(),
    onMonthSelected: (Month) -> Unit = {}
) {
    val months = remember { Month.values() }

    val selectedIndex =
        if (selectedMonth != null)
            months.indexOfFirst { indexedMonth -> indexedMonth.value == selectedMonth.value }
        else
            0

    val listState = rememberLazyListState(selectedIndex)
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        state = listState,
        modifier = modifier
    ) {
        itemsIndexed(months) { index, month ->

            val selected = selectedMonth != null && index == selectedIndex

            TabButton(
                text = month.name,
                selected = selected,
                isEnabled = month !in disabledMonths,
                onClick = {
                    onMonthSelected.invoke(month)
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = index)
                    }
                },
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
fun ScrollableTabRow(
    modifier: Modifier = Modifier,
    tabItems: List<String>,
    selectedIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {}
) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.secondary,
        divider = {}, /* Disable the built-in divider */
        edgePadding = 0.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        tabItems.forEachIndexed { index, tabItem ->
            val selected = index == selectedIndex
            Tab(
                selected = selected,
                onClick = { onTabSelected(index) }
            ) {
                Text(
                    text = tabItem,
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = FontWeight.Bold,
                        color = when {
                            selected -> MaterialTheme.colors.secondary
                            else -> MaterialTheme.colors.secondary.copy(alpha = 0.7F)
                        }
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TabsPreview() {
    CatalogView {
        ScrollableTabRow(tabItems = listOf("ITEM 1", "ITEM 2"))
        Spacer(Modifier.size(4.dp))
        DateMonthTabs()
    }
}
