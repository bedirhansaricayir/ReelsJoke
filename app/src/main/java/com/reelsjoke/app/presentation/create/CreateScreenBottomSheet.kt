package com.reelsjoke.app.presentation.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.reelsjoke.app.core.extensions.toBitmap
import com.reelsjoke.app.domain.model.BottomSheetType
import com.reelsjoke.app.presentation.create.components.BottomSheetTextFieldComponent
import com.reelsjoke.app.presentation.create.components.EndIconGoForward
import com.reelsjoke.app.presentation.create.components.EndIconSwitch
import com.reelsjoke.app.presentation.create.components.EndIconVerified
import com.reelsjoke.app.presentation.create.components.ForwardIconWithImage
import com.reelsjoke.app.presentation.create.components.TopBarIconButton
import com.reelsjoke.app.presentation.create.components.UserInformationCategory
import com.reelsjoke.app.presentation.create.components.UserInformationComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreenBottomSheet(
    state: CreateScreenUIState,
    onEvent: (CreateScreenUIEvent) -> Unit
) {
    if (state.bottomSheetType != BottomSheetType.NONE) {
        val isUsernameBottomSheet = state.bottomSheetType == BottomSheetType.USERNAME
        val bottomSheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { onEvent(CreateScreenUIEvent.BottomSheetDismissClicked()) },
            sheetState = bottomSheetState,
            dragHandle = {
                CreateScreenBottomSheetTopBar(
                    state = state,
                    onEvent = onEvent,
                    onBackClick = {
                        onEvent(
                            CreateScreenUIEvent.BottomSheetDismissClicked(
                                bottomSheetType = if (isUsernameBottomSheet) BottomSheetType.USER_INFORMATION
                                else BottomSheetType.NONE
                            )
                        )
                    }
                )
            },
            content = {
                CreateScreenBottomSheetContent(
                    state = state,
                    onEvent = onEvent
                )
            }
        )
    }
}

@Composable
fun CreateScreenBottomSheetTopBar(
    state: CreateScreenUIState,
    onEvent: (CreateScreenUIEvent) -> Unit,
    onBackClick: () -> Unit
) {
    val isUsernameBottomSheet = state.bottomSheetType == BottomSheetType.USERNAME

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TopBarIconButton(
                icon = if (isUsernameBottomSheet) Icons.Default.ArrowBack else Icons.Outlined.Close,
                title = "Cancel",
                enabled = true,
                customTint = Color.Gray,
                onClick = onBackClick
            )
            AnimatedContent(
                targetState = state.bottomSheetType.title,
                label = ""
            ) {
                Text(text = it)
            }
            TopBarIconButton(
                icon = Icons.Outlined.Done,
                title = "Create",
                enabled = true,
                onClick = { onEvent(CreateScreenUIEvent.BottomSheetDoneClicked) }
            )
        }
        HorizontalDivider(thickness = 0.5.dp)
    }
}

@Composable
fun CreateScreenBottomSheetContent(
    state: CreateScreenUIState,
    onEvent: (CreateScreenUIEvent) -> Unit
) {
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { imageUri ->
            imageUri?.toBitmap(context).also { createdBitmap ->
                createdBitmap?.let { bitmap ->
                    onEvent(CreateScreenUIEvent.OnUserImageChanged(bitmap))
                }
            }
            onEvent(CreateScreenUIEvent.OnGalleryLauncherStateChanged)
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        when (state.bottomSheetType) {
            BottomSheetType.CHANGE_VOICE -> {
                BottomSheetTextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    name = state.voiceName,
                    hintText = "Give the audio content a name",
                    titleText = "Voice name",
                    onNameChanged = { onEvent(CreateScreenUIEvent.OnVoiceNameChanged(it)) }
                )
            }

            BottomSheetType.NONE -> {}
            BottomSheetType.FEEDBACK -> {
                Column {
                    BottomSheetTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        name = state.likesCount,
                        hintText = "Likes",
                        titleText = "Likes",
                        onNameChanged = { onEvent(CreateScreenUIEvent.OnLikesCountChanged(it)) },
                        shouldFocusRequest = false,
                        imeAction = ImeAction.Next
                    )
                    BottomSheetTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        name = state.commentCount,
                        hintText = "Comments",
                        titleText = "Comments",
                        onNameChanged = { onEvent(CreateScreenUIEvent.OnCommentCountChanged(it)) },
                        shouldFocusRequest = false,
                        imeAction = ImeAction.Next
                    )
                    BottomSheetTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        name = state.sendCount,
                        hintText = "Sends",
                        titleText = "Sends",
                        onNameChanged = { onEvent(CreateScreenUIEvent.OnSendCountChanged(it)) },
                        shouldFocusRequest = false
                    )
                }

            }

            BottomSheetType.TAG_PEOPLE -> {
                BottomSheetTextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    name = state.peopleTagged,
                    hintText = "Add person to tag",
                    titleText = "Tag people",
                    onNameChanged = { onEvent(CreateScreenUIEvent.OnPeopleTaggedChanged(it)) }
                )
            }

            BottomSheetType.ADD_LOCATION -> {
                BottomSheetTextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    name = state.location,
                    hintText = "Add a location to appear in the reels video",
                    titleText = "Location name",
                    onNameChanged = { onEvent(CreateScreenUIEvent.OnLocationChanged(it)) }
                )
            }

            BottomSheetType.USER_INFORMATION -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    UserInformationComponent(
                        endIcon = {
                            ForwardIconWithImage(
                                image = state.userImage
                            )
                        },
                        category = UserInformationCategory.IMAGE,
                        onClick = {
                            if (!state.launcherIsOpen) {
                                galleryLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                                onEvent(CreateScreenUIEvent.OnGalleryLauncherStateChanged)
                            }
                        }
                    )
                    UserInformationComponent(
                        endIcon = { EndIconGoForward() },
                        category = UserInformationCategory.USERNAME,
                        onClick = {
                            onEvent(CreateScreenUIEvent.BottomSheetUsernameClicked(bottomSheetType = BottomSheetType.USERNAME))
                        }
                    )
                    UserInformationComponent(
                        category = UserInformationCategory.VERIFIED,
                        endIcon = {
                            EndIconVerified(
                                checked = state.isVerified,
                                onCheckedChange = {
                                    onEvent(CreateScreenUIEvent.OnIsVerifiedChanged(!state.isVerified))
                                }
                            )
                        },
                        onClick = {
                            onEvent(CreateScreenUIEvent.OnIsVerifiedChanged(!state.isVerified))
                        }
                    )
                    UserInformationComponent(
                        category = UserInformationCategory.FOLLOWED,
                        endIcon = {
                            EndIconSwitch(
                                checked = state.isFollowed,
                                onCheckedChange = {
                                    onEvent(CreateScreenUIEvent.OnIsFollowedChanged(it))
                                }
                            )
                        },
                        onClick = {
                            onEvent(CreateScreenUIEvent.OnIsFollowedChanged(!state.isFollowed))
                        }
                    )
                }
            }

            BottomSheetType.USERNAME -> {
                BottomSheetTextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    name = state.username,
                    hintText = "Add username",
                    titleText = "Username",
                    onNameChanged = { onEvent(CreateScreenUIEvent.OnUsernameChanged(it)) }
                )
            }
        }
    }
}