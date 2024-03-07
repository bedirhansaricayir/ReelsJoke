package com.reelsjoke.app.presentation.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.reelsjoke.app.R
import com.reelsjoke.app.billing.BillingClientWrapper
import com.reelsjoke.app.core.extensions.findActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillingBottomSheet(
    enabled: Boolean,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
) {

    if (enabled) {
        val context = LocalContext.current
        val bottomSheetState = rememberModalBottomSheetState()
        val billingClientWrapper = BillingClientWrapper(
            context = context.findActivity() ?: context as Activity,
            onSuccess = onSuccess,
        )


        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = bottomSheetState,
            content = {
                BillingBottomSheetContent(
                    onPurchaseClicked = {
                        billingClientWrapper.initBilling()
                    }
                )
            }
        )
    }

}

@Composable
fun BillingBottomSheetContent(
    onPurchaseClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.premium),
                contentDescription = stringResource(id = R.string.premium_icon_content_description),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.purchase_title),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(id = R.string.purchase_subtitle),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onPurchaseClicked() }
        ) {
            Text(text = stringResource(id = R.string.purchase_button))
        }
    }
}