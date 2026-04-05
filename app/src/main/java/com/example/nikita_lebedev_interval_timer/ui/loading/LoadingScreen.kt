package com.example.nikita_lebedev_interval_timer.ui.loading

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.example.nikita_lebedev_interval_timer.R
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nikita_lebedev_interval_timer.domain.model.Timer
import com.example.nikita_lebedev_interval_timer.ui.theme.AppTypography
import com.example.nikita_lebedev_interval_timer.ui.theme.Border
import com.example.nikita_lebedev_interval_timer.ui.theme.DisabledBg
import com.example.nikita_lebedev_interval_timer.ui.theme.DisabledText
import com.example.nikita_lebedev_interval_timer.ui.theme.Error
import com.example.nikita_lebedev_interval_timer.ui.theme.NikitalebedevintervaltimerTheme
import com.example.nikita_lebedev_interval_timer.ui.theme.Primary
import com.example.nikita_lebedev_interval_timer.ui.theme.PrimaryLight
import com.example.nikita_lebedev_interval_timer.ui.theme.Spacing
import com.example.nikita_lebedev_interval_timer.ui.theme.Surface
import com.example.nikita_lebedev_interval_timer.ui.theme.TextPrimary
import com.example.nikita_lebedev_interval_timer.ui.theme.TextSecondary

@Composable
fun LoadingScreen(
    viewModel: LoadingViewModel = hiltViewModel(),
    onNavigateToTimer: (Timer) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is LoadingEvent.NavigateToTimer -> onNavigateToTimer(event.timer)
            }
        }
    }

    LoadingContent(
        uiState = viewModel.uiState.collectAsState().value,
        changeId = { id -> viewModel.changeTimerId(id) },
        loadInterval = { viewModel.loadInterval() }
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    NikitalebedevintervaltimerTheme {
        LoadingContent(
            uiState = LoadingUiState(),
            changeId = {},
            loadInterval = {}
        )
    }
}

@Composable
fun LoadingContent(
    uiState: LoadingUiState,
    changeId: (String) -> Unit,
    loadInterval: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing.xxl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(80.dp))

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Primary, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_timer),
                    contentDescription = stringResource(R.string.time_icon),
                    tint = Surface,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(Spacing.xxl))

            Text(
                text = stringResource(R.string.title),
                style = AppTypography.h1,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Spacing.l))

            Text(
                text = stringResource(R.string.subtitle),
                style = AppTypography.body,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Spacing.l))

            Text(
                text = stringResource(R.string.id_train),
                style = AppTypography.caption,
                color = TextSecondary,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(Spacing.s))

            OutlinedTextField(
                value = uiState.timerId,
                enabled = !uiState.isLoading,
                onValueChange = changeId,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                isError = uiState.isError,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Border,
                    errorBorderColor = Error,
                    disabledTextColor = DisabledText,
                    disabledContainerColor = DisabledBg
                )
            )
            if (uiState.isError) {
                Spacer(modifier = Modifier.height(Spacing.s))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = stringResource(R.string.error_icon),
                        tint = Error,
                        modifier = Modifier.size(14.dp) // сверить со спецификацией
                    )

                    Spacer(modifier = Modifier.width(Spacing.xs))

                    Text(
                        text = stringResource(R.string.error),
                        style = AppTypography.caption,
                        color = Error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.l))

            if (uiState.isLoading) {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .alpha(0.7f),
                    enabled = false,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, PrimaryLight),
                    colors = ButtonDefaults.outlinedButtonColors(
                        disabledContainerColor = PrimaryLight,
                        disabledContentColor = Primary
                    )
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Primary,
                        strokeWidth = 2.dp
                    )

                    Spacer(modifier = Modifier.width(Spacing.s))

                    Text(
                        text = stringResource(R.string.wait_loading),
                        color = Primary,
                        style = AppTypography.button
                    )

                }
            } else {

                Button(
                    onClick = loadInterval,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary
                    )
                ) {

                    Text(
                        text = if (uiState.isError) {
                            stringResource(R.string.retry)
                        } else {
                            stringResource(R.string.loading)
                        },
                        color = Surface,
                        style = AppTypography.button
                    )
                }

            }
        }
    }
}