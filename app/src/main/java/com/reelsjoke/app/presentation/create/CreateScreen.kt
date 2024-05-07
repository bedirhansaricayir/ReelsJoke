package com.reelsjoke.app.presentation.create


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reelsjoke.app.R
import com.reelsjoke.app.core.extensions.noRippleClickable
import com.reelsjoke.app.core.extensions.toBitmap
import com.reelsjoke.app.domain.model.BottomSheetType
import com.reelsjoke.app.domain.model.CreateScreenItemData
import com.reelsjoke.app.presentation.components.dashedBorder
import com.reelsjoke.app.presentation.create.components.CreateScreenTopBar
import com.reelsjoke.app.presentation.create.components.CustomTextField


@Composable
fun CreateScreen(
    state: CreateScreenUIState,
    snackbarHostState: SnackbarHostState,
    onEvent: (CreateScreenUIEvent) -> Unit
) {
    CreateScreenContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onEvent = onEvent
    )
}

@Composable
fun CreateScreenContent(
    state: CreateScreenUIState,
    snackbarHostState: SnackbarHostState,
    onEvent: (CreateScreenUIEvent) -> Unit
) {
    CreateScreenBottomSheet(
        state = state,
        onEvent = onEvent
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CreateScreenTopBar(
                modifier = Modifier.padding(8.dp),
                navigateUp = { onEvent(CreateScreenUIEvent.OnBackButtonClicked) },
                onButtonClick = {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    onEvent(CreateScreenUIEvent.OnExportClicked)
                },
                infiniteRepeatable = state.infiniteRepeatable
            )
            ReelsImageContainer(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                state = state,
                onEvent = onEvent
            )
            CustomTextField(
                modifier = Modifier.padding(horizontal = 8.dp),
                value = state.description,
                onValueChanged = {
                    onEvent(CreateScreenUIEvent.OnDescriptionChanged(it))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default)
            )
            HorizontalDivider(thickness = 0.5.dp)
            ItemsList(
                items = state.items,
                onItemClicked = { menuItem ->
                    when (menuItem) {
                        R.string.feedback_title -> onEvent(
                            CreateScreenUIEvent.FeedbackClicked(
                                bottomSheetType = BottomSheetType.FEEDBACK
                            )
                        )

                        R.string.tag_people_title -> onEvent(
                            CreateScreenUIEvent.TagPeopleClicked(
                                bottomSheetType = BottomSheetType.TAG_PEOPLE
                            )
                        )

                        R.string.add_location_title -> onEvent(
                            CreateScreenUIEvent.AddLocationClicked(
                                bottomSheetType = BottomSheetType.ADD_LOCATION
                            )
                        )

                        R.string.change_voice_title -> onEvent(
                            CreateScreenUIEvent.ChangeVoiceClicked(
                                bottomSheetType = BottomSheetType.CHANGE_VOICE
                            )
                        )

                        R.string.user_information_title -> onEvent(
                            CreateScreenUIEvent.UserInformationClicked(
                                bottomSheetType = BottomSheetType.USER_INFORMATION
                            )
                        )
                    }
                }
            )
        }

        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState,
        )

    }
}


@Composable
fun ReelsImageContainer(
    modifier: Modifier = Modifier,
    state: CreateScreenUIState,
    onEvent: (CreateScreenUIEvent) -> Unit
) {
    val context = LocalContext.current
    val rotationState by animateFloatAsState(
        targetValue = if (state.launcherIsOpen) 45f else 0f, label = ""
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { imageUri ->
            imageUri?.toBitmap(context).also { createdBitmap ->
                createdBitmap?.let { bitmap ->
                    onEvent(CreateScreenUIEvent.OnBackgroundImageChanged(bitmap))
                }
            }
            onEvent(CreateScreenUIEvent.OnGalleryLauncherStateChanged)
        }
    )

    Box(
        modifier = modifier
            .height(250.dp)
            .width(150.dp)
            .dashedBorder(
                color = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.7f) else Color.Black.copy(
                    alpha = 0.5f
                ),
                shape = MaterialTheme.shapes.small
            )
            .noRippleClickable {
                if (!state.launcherIsOpen) {
                    galleryLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                    onEvent(CreateScreenUIEvent.OnGalleryLauncherStateChanged)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        state.backgroundImage?.let { image ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                model = image,
                contentDescription = "Selected Image",
                contentScale = ContentScale.Crop,
            )
        } ?: run {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.rotate(rotationState),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Select Image",
                    tint = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.9f) else Color.Black.copy(
                        alpha = 0.7f
                    )
                )
                Text(
                    text = "Add reels image",
                    fontSize = 10.sp,
                    color = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.8f) else Color.Black.copy(
                        alpha = 0.6f
                    )
                )
            }
        }
    }
}

@Composable
fun ItemsList(
    modifier: Modifier = Modifier,
    items: List<CreateScreenItemData>?,
    onItemClicked: (title: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items.orEmpty()) { data ->
            CreateScreenItem(
                data = data,
                onClicked = onItemClicked
            )
        }
    }
}

@Composable
fun CreateScreenItem(
    modifier: Modifier = Modifier,
    data: CreateScreenItemData,
    onClicked: (title: Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) { onClicked(data.title) }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = data.leadingIcon),
                contentDescription = "Button Icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = data.title),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),

                )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Go Forward",
                tint = Color.Gray
            )
        }
    }
}