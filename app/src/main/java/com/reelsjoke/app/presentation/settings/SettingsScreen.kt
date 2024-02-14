package com.reelsjoke.app.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.reelsjoke.app.R
import com.reelsjoke.app.core.components.CommonTopBar
import com.reelsjoke.app.core.util.IntentUtility.sendMailIntent
import com.reelsjoke.app.core.util.IntentUtility.shareAppIntent
import com.reelsjoke.app.domain.model.SettingsItemData


/**
 * Created by bedirhansaricayir on 10.02.2024.
 */

@Composable
fun SettingsScreen(
    state: SettingsScreenUIState,
    onEvent: (SettingsScreenUIEvent) -> Unit
) {

    SettingsScreenContent(
        state = state,
        onBackPress = { onEvent(SettingsScreenUIEvent.OnBackButtonClicked) },
        onItemClicked = { settingsItem ->
            when (settingsItem) {
                R.string.share_title -> onEvent(
                    SettingsScreenUIEvent.OnShareAppClicked(
                        shareAppIntent()
                    )
                )

                R.string.send_feedback_title -> onEvent(
                    SettingsScreenUIEvent.OnSendFeedbackClicked(
                        sendMailIntent()
                    )
                )
            }
        }
    )
}


@Composable
fun SettingsScreenContent(
    state: SettingsScreenUIState,
    onBackPress: () -> Unit,
    onItemClicked: (title: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CommonTopBar(
            title = R.string.settings_screen_title,
            onBackNavigationIconClicked = onBackPress
        )
        Spacer(modifier = Modifier.height(16.dp))
        SettingsList(
            settings = state.settings,
            onItemClicked = onItemClicked
        )

    }
}

@Composable
fun SettingsList(
    modifier: Modifier = Modifier,
    settings: List<SettingsItemData>?,
    onItemClicked: (title: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(settings.orEmpty()) { data ->
            SettingsItem(
                data = data,
                onClick = onItemClicked
            )
        }
    }
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    data: SettingsItemData,
    onClick: (title: Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(role = Role.Button) { onClick(data.title) }
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = data.leadingIcon),
                contentDescription = stringResource(id = data.leadingIconContentDescription),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = data.title), modifier = Modifier
                    .padding(16.dp),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}