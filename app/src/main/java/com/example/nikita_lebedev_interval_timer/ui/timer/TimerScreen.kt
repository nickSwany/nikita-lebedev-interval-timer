package com.example.nikita_lebedev_interval_timer.ui.timer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.nikita_lebedev_interval_timer.R
import com.example.nikita_lebedev_interval_timer.domain.model.Timer
import com.example.nikita_lebedev_interval_timer.ui.theme.AppTypography
import com.example.nikita_lebedev_interval_timer.ui.theme.Bg
import com.example.nikita_lebedev_interval_timer.ui.theme.BgLight
import com.example.nikita_lebedev_interval_timer.ui.theme.Border
import com.example.nikita_lebedev_interval_timer.ui.theme.Error
import com.example.nikita_lebedev_interval_timer.ui.theme.Orange
import com.example.nikita_lebedev_interval_timer.ui.theme.OrangeGradient
import com.example.nikita_lebedev_interval_timer.ui.theme.OrangeLight
import com.example.nikita_lebedev_interval_timer.ui.theme.Primary
import com.example.nikita_lebedev_interval_timer.ui.theme.PrimaryGradient
import com.example.nikita_lebedev_interval_timer.ui.theme.PrimaryLight
import com.example.nikita_lebedev_interval_timer.ui.theme.Secondary
import com.example.nikita_lebedev_interval_timer.ui.theme.SecondaryGradient
import com.example.nikita_lebedev_interval_timer.ui.theme.SecondaryLight
import com.example.nikita_lebedev_interval_timer.ui.theme.Spacing
import com.example.nikita_lebedev_interval_timer.ui.theme.Surface
import com.example.nikita_lebedev_interval_timer.ui.theme.TextPrimary
import com.example.nikita_lebedev_interval_timer.ui.theme.TextSecondary
import com.example.nikita_lebedev_interval_timer.ui.theme.TextTertiary

@Composable
fun TimerScreen(
    viewModel: TimerViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val uiState by viewModel.uiState.collectAsState()

    TimerScreenContent(
        timer = viewModel.timerData,
        timerState = uiState,
        navController = navController,
        onStart = viewModel::start,
        onPause = viewModel::pause,
        onResume = viewModel::resume,
        onRestart = viewModel::restart,
        onReset = viewModel::reset,
        onNewTraining = { navController.popBackStack() }
    )
}

@Composable
fun TimerScreenContent(
    timer: Timer,
    timerState: TimerUiState,
    navController: NavController,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onRestart: () -> Unit,
    onReset: () -> Unit,
    onNewTraining: () -> Unit,
) {

    val currentIntervalTitle = when (timerState) {
        TimerUiState.Completed -> stringResource(R.string.great_job)
        TimerUiState.Idle -> timer.intervals.firstOrNull()?.title ?: ""
        is TimerUiState.Paused -> timer.intervals.getOrNull(timerState.activeIntervalIndex)?.title
            ?: ""

        is TimerUiState.Running -> timer.intervals.getOrNull(timerState.activeIntervalIndex)?.title
            ?: ""
    }

    val remainingTime = when (timerState) {
        TimerUiState.Completed -> stringResource(R.string.zero_time)
        TimerUiState.Idle -> formateTime(timer.totalTime)
        is TimerUiState.Paused -> formateTime(timerState.timeToEndInterval)
        is TimerUiState.Running -> formateTime(timerState.timeToEndInterval)
    }

    val elapsedTime = when (timerState) {
        TimerUiState.Completed -> formateTime(timer.totalTime)
        TimerUiState.Idle -> ""
        is TimerUiState.Paused -> formateTime(timerState.spentTime)
        is TimerUiState.Running -> formateTime(timerState.spentTime)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .systemBarsPadding()
            .padding(Spacing.xxl)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Surface, shape = RoundedCornerShape(100.dp))
                        .border(1.dp, Border, shape = RoundedCornerShape(100.dp)),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(R.string.back_icon),
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = timer.title,
                    style = AppTypography.title,
                    color = TextPrimary
                )

                when (timerState) {
                    TimerUiState.Completed -> {
                        Text(
                            text = stringResource(R.string.completed),
                            style = AppTypography.caption,
                            color = Secondary
                        )
                    }

                    TimerUiState.Idle -> {
                        Text(
                            text = formateTime(timer.totalTime),
                            style = AppTypography.caption,
                            color = TextSecondary
                        )
                    }

                    is TimerUiState.Paused -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_pause),
                                contentDescription = null,
                                tint = Orange,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(Spacing.xs))

                            Text(
                                text = stringResource(R.string.pause),
                                style = AppTypography.caption,
                                color = Orange
                            )
                        }
                    }

                    is TimerUiState.Running -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Primary, shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(Spacing.xs))

                            Text(
                                text = formateTime(timerState.spentTime),
                                style = AppTypography.caption,
                                color = Primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xl))

            TimeCard(
                timerState = timerState,
                totalTime = timer.totalTime,
                intervalTitle = currentIntervalTitle,
                remainingTime = remainingTime,
                elapsedTime = elapsedTime,
            )

            if (timerState is TimerUiState.Completed) {
                Spacer(modifier = Modifier.height(Spacing.l))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    CompleteStatusCard(
                        value = formateTime(timer.totalTime),
                        title = stringResource(R.string.total_time)
                    )

                    Spacer(modifier = Modifier.width(Spacing.m))

                    CompleteStatusCard(
                        value = timer.intervals.size.toString(),
                        title = "Интервалов"
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.l))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.Intervals),
                    style = AppTypography.caption,
                    color = TextSecondary
                )

                when (timerState) {
                    TimerUiState.Completed -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${timer.intervals.size} из ${timer.intervals.size}",
                                style = AppTypography.caption,
                                color = TextTertiary
                            )
                            Spacer(modifier = Modifier.width(Spacing.xs))
                            Icon(
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = null,
                                tint = TextTertiary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    TimerUiState.Idle -> {
                        Text(
                            text = "${timer.intervals.size} интервалов",
                            style = AppTypography.caption,
                            color = TextTertiary
                        )
                    }

                    is TimerUiState.Paused -> {
                        Text(
                            text = "${timerState.activeIntervalIndex + 1} из ${timer.intervals.size}",
                            style = AppTypography.caption,
                            color = TextTertiary
                        )
                    }

                    is TimerUiState.Running -> {
                        Text(
                            text = "${timerState.activeIntervalIndex + 1} из ${timer.intervals.size}",
                            style = AppTypography.caption,
                            color = TextTertiary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.s))
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s)
                ) {
                    itemsIndexed(timer.intervals) { index, interval ->
                        val itemState = getIntervalItemState(index, timerState)
                        val displayTime = when {
                            itemState == IntervalItemState.Active ||
                                    itemState == IntervalItemState.ActivePaused -> {
                                val remainingTime = when (timerState) {
                                    is TimerUiState.Paused -> timerState.timeToEndInterval
                                    is TimerUiState.Running -> timerState.timeToEndInterval
                                    else -> interval.time
                                }
                                formateTime(remainingTime)
                            }

                            else -> formateTime(interval.time)
                        }
                        IntervalItem(
                            index = index,
                            title = interval.title,
                            time = displayTime,
                            state = getIntervalItemState(index, timerState),
                            progress = getIntervalProgress(index, timerState, interval.time)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Bg
                                )
                            )
                        )
                )
            }

            TimerButton(
                timerState = timerState,
                onStart = onStart,
                onPause = onPause,
                onResume = onResume,
                onRestart = onRestart,
                onReset = onReset,
                onNewTraining = onNewTraining
            )
        }
    }
}

@Composable
fun TimeCard(
    timerState: TimerUiState,
    intervalTitle: String,
    remainingTime: String,
    elapsedTime: String,
    totalTime: Long,
) {

    val borderColor = when (timerState) {
        TimerUiState.Completed -> SecondaryLight
        TimerUiState.Idle -> Border
        is TimerUiState.Paused -> OrangeLight
        is TimerUiState.Running -> PrimaryLight
    }

    val stateText = when (timerState) {
        TimerUiState.Completed -> stringResource(R.string.training_completed)
        TimerUiState.Idle -> stringResource(R.string.ready_to_start)
        is TimerUiState.Paused -> stringResource(R.string.onpause)
        is TimerUiState.Running -> stringResource(R.string.running)
    }

    val stateColor = when (timerState) {
        TimerUiState.Completed -> Secondary
        TimerUiState.Idle -> TextTertiary
        is TimerUiState.Paused -> Orange
        is TimerUiState.Running -> Primary
    }

    val timeColor = when (timerState) {
        TimerUiState.Completed -> Secondary
        TimerUiState.Idle -> TextPrimary
        is TimerUiState.Paused -> Orange
        is TimerUiState.Running -> Primary
    }

    val progressColor = when (timerState) {
        TimerUiState.Completed -> Secondary
        TimerUiState.Idle -> Primary
        is TimerUiState.Paused -> Orange
        is TimerUiState.Running -> Primary
    }

    val gradientBrush = when (timerState) {
        TimerUiState.Completed -> Brush.verticalGradient(
            colors = listOf(SecondaryGradient, Color.Transparent)
        )

        TimerUiState.Idle -> null

        is TimerUiState.Paused -> Brush.verticalGradient(
            colors = listOf(OrangeGradient, Color.Transparent)
        )

        is TimerUiState.Running -> Brush.verticalGradient(
            colors = listOf(PrimaryGradient, Color.Transparent)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface, shape = RoundedCornerShape(16.dp))
            .border(1.5.dp, borderColor, shape = RoundedCornerShape(16.dp))
    ) {
        if (gradientBrush != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .matchParentSize()
                    .background(gradientBrush, shape = RoundedCornerShape(16.dp)),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.xxl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stateText,
                style = AppTypography.state,
                color = stateColor
            )

            Spacer(modifier = Modifier.height(Spacing.xs))

            Text(
                text = intervalTitle,
                style = AppTypography.label,
                color = if (timerState == TimerUiState.Completed) Secondary else TextSecondary
            )

            Text(
                text = remainingTime,
                style = AppTypography.timerDisplay,
                color = timeColor
            )

            when (timerState) {
                TimerUiState.Completed -> {
                    Text(
                        text = "$totalTime из $totalTime",
                        style = AppTypography.caption,
                        color = TextTertiary
                    )
                }

                TimerUiState.Idle -> {
                    Text(
                        text = stringResource(R.string.total_time),
                        style = AppTypography.caption,
                        color = TextTertiary
                    )
                }

                is TimerUiState.Paused, is TimerUiState.Running -> {
                    Text(
                        text = "Прошло $elapsedTime из ${formateTime(totalTime)}",
                        style = AppTypography.caption,
                        color = TextTertiary
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.m))

            LinearProgressIndicator(
                progress = {
                    when (timerState) {
                        TimerUiState.Completed -> 1f
                        TimerUiState.Idle -> 0f
                        is TimerUiState.Paused -> timerState.spentTime.toFloat() / totalTime.toFloat()
                        is TimerUiState.Running -> timerState.spentTime.toFloat() / totalTime.toFloat()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = progressColor,
                trackColor = Bg,
                drawStopIndicator = {}
            )
        }
    }
}

@Composable
fun IntervalItem(
    index: Int,
    title: String,
    time: String,
    state: IntervalItemState,
    progress: Float = 0f
) {
    when (state) {
        IntervalItemState.Active, IntervalItemState.ActivePaused -> {
            val borderColor = when (state) {
                IntervalItemState.Active -> PrimaryLight
                IntervalItemState.ActivePaused -> OrangeLight
                else -> Border
            }

            val progressColor = when (state) {
                IntervalItemState.Active -> PrimaryLight
                IntervalItemState.ActivePaused -> OrangeLight
                else -> Surface
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(Surface, shape = RoundedCornerShape(12.dp))
                    .border(1.5.dp, borderColor, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = progress)
                        .fillMaxHeight()
                        .background(progressColor)
                )

                IntervalItemContent(
                    index = index,
                    title = title,
                    time = time,
                    state = state
                )
            }
        }

        IntervalItemState.Completed -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        BgLight,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .alpha(0.45f)
            ) {
                IntervalItemContent(
                    index = index,
                    title = title,
                    time = time,
                    state = state
                )
            }
        }


        IntervalItemState.Idle -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface, shape = RoundedCornerShape(12.dp))
            ) {
                IntervalItemContent(
                    index = index,
                    title = title,
                    time = time,
                    state = state
                )

            }
        }
    }
}


@Composable
fun IntervalItemContent(
    index: Int,
    title: String,
    time: String,
    state: IntervalItemState,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (state) {
            IntervalItemState.Active, IntervalItemState.ActivePaused, IntervalItemState.Idle -> {

                val circleColor = when (state) {
                    IntervalItemState.Active -> Primary
                    IntervalItemState.ActivePaused -> Orange
                    IntervalItemState.Idle -> Bg
                    else -> Bg
                }
                val textColor = if (state == IntervalItemState.Idle) TextSecondary else Surface

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            circleColor, shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "${index + 1}",
                        style = AppTypography.caption,
                        color = textColor
                    )
                }
            }

            IntervalItemState.Completed -> {

                Box(
                    modifier = Modifier.size(28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = "Done",
                        tint = Secondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(Spacing.m))

        Text(
            text = title,
            style = AppTypography.caption,
            color = if (state == IntervalItemState.Completed) TextTertiary else TextPrimary,
            textDecoration = if (state == IntervalItemState.Completed) TextDecoration.LineThrough else null,
            modifier = Modifier.weight(1f) //Возможно это лишнее
        )

        Text(
            text = time,
            style = AppTypography.mono,
            color = when (state) {
                IntervalItemState.Active -> Primary
                IntervalItemState.ActivePaused -> Orange
                IntervalItemState.Completed -> TextTertiary
                IntervalItemState.Idle -> TextSecondary
            }
        )
    }
}

@Composable
fun TimerButton(
    timerState: TimerUiState,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onRestart: () -> Unit,
    onReset: () -> Unit,
    onNewTraining: () -> Unit,
) {

    val eventOnClick = when (timerState) {
        TimerUiState.Completed -> onRestart
        TimerUiState.Idle -> onStart
        is TimerUiState.Paused -> onResume
        is TimerUiState.Running -> onPause
    }

    val backColor = when (timerState) {
        TimerUiState.Completed -> Secondary
        TimerUiState.Idle -> Primary
        is TimerUiState.Paused -> Primary
        is TimerUiState.Running -> Orange
    }

    val textButton = when (timerState) {
        TimerUiState.Completed -> stringResource(R.string.restart)
        TimerUiState.Idle -> stringResource(R.string.start)
        is TimerUiState.Paused -> stringResource(R.string.resume)
        is TimerUiState.Running -> stringResource(R.string.pause)
    }

    val iconButton = when (timerState) {
        TimerUiState.Completed -> painterResource(R.drawable.ic_restart)
        TimerUiState.Idle -> painterResource(R.drawable.ic_start)
        is TimerUiState.Paused -> painterResource(R.drawable.ic_start)
        is TimerUiState.Running -> painterResource(R.drawable.ic_pause)
    }
    Column(modifier = Modifier.fillMaxWidth()) {

        Button(
            onClick = eventOnClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backColor
            )
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = iconButton,
                    contentDescription = null,
                    tint = Surface,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(Spacing.s))

                Text(
                    text = textButton,
                    color = Surface,
                    style = AppTypography.button,
                )
            }
        }

        when (timerState) {
            TimerUiState.Completed -> {
                Spacer(modifier = Modifier.height(Spacing.s))
                OutlinedButton(
                    onClick = onNewTraining,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, Border),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Surface
                    )
                ) {

                    Text(
                        text = "Новая тренировка",
                        style = AppTypography.button,
                        color = TextSecondary
                    )
                }
            }

            is TimerUiState.Paused, is TimerUiState.Running -> {
                Spacer(modifier = Modifier.height(Spacing.s))

                OutlinedButton(
                    onClick = onReset,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, Error),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Surface
                    )
                ) {

                    Text(
                        text = "Сбросить тренировку",
                        style = AppTypography.button,
                        color = Error
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun RowScope.CompleteStatusCard(
    value: String,
    title: String
) {

    Box(
        modifier = Modifier
            .weight(1f)
            .background(Surface, shape = RoundedCornerShape(12.dp))
            .border(1.5.dp, Border, shape = RoundedCornerShape(12.dp))
            .padding(vertical = Spacing.l),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                style = AppTypography.title,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(Spacing.xs))

            Text(
                text = title,
                style = AppTypography.caption,
                color = TextTertiary
            )
        }
    }
}