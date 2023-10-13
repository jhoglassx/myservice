package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhoglas.mysalon.domain.ScheduleDayDomainEntity
import com.jhoglas.mysalon.domain.ScheduleHourDomainEntity
import com.jhoglas.mysalon.ui.theme.Primary
import com.jhoglas.mysalon.utils.extensions.getDayFromDate
import com.jhoglas.mysalon.utils.extensions.getMonthFromDate
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDayComponent(
    days: List<ScheduleDayDomainEntity>,
    selectedDay: (Date) -> Unit,
) {
    CategoryTitleTextComponent("Schedule Day")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        Alignment.Center
    ) {
        LazyRow(
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(days) {
                Card(
                    onClick = { selectedDay.invoke(it.date) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Primary, shape = RoundedCornerShape(15)),
                        horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = it.date.getDayFromDate().toString(),
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp, 4.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = it.date.getMonthFromDate(),
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp, 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleHourComponent(
    hours: List<ScheduleHourDomainEntity>,
) {
    CategoryTitleTextComponent("Schedule Hours")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(hours.size) { index ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Primary, shape = RoundedCornerShape(15)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = hours[index].start.toString(),
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp, 4.dp)
                        )
                    }
                }
            }
        )
    }
}
