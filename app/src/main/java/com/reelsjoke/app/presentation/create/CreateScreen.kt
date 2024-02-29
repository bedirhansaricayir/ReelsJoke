package com.reelsjoke.app.presentation.create


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.reelsjoke.app.R
import com.reelsjoke.app.core.CustomInputField
import com.reelsjoke.app.core.ExportButton
import com.reelsjoke.app.core.RoundedCornerBox
import com.reelsjoke.app.core.extensions.getUriForFile
import com.reelsjoke.app.core.extensions.noRippleClickable
import com.reelsjoke.app.core.extensions.toBitmap
import com.reelsjoke.app.domain.model.QuestionType

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
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TopBar(
                navigateUp = { onEvent(CreateScreenUIEvent.OnBackButtonClicked) },
                onButtonClick = {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    onEvent(CreateScreenUIEvent.OnExportClicked)
                },
                infiniteRepeatable = state.infiniteRepeatable
            )

            BackgroundImage(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onImageChanged = { onEvent(CreateScreenUIEvent.OnBackgroundImageChanged(it)) }
            )
            ReelsDetail(
                likesCount = state.likesCount,
                commentCount = state.commentCount,
                sendCount = state.sendCount,
                isLiked = state.isLiked,
                isLikesCountHidden = state.isLikesCountHidden,
                isTaggedPeople = state.isTaggedPeople,
                peopleTagged = state.peopleTagged,
                isLocationExist = state.isLocationExist,
                location = state.location,
                isLikedChanged = { onEvent(CreateScreenUIEvent.OnIsLikedChanged(it)) },
                onLikesCountChanged = { onEvent(CreateScreenUIEvent.OnLikesCountChanged(it)) },
                onCommentCountChanged = { onEvent(CreateScreenUIEvent.OnCommentCountChanged(it)) },
                onSendCountChanged = { onEvent(CreateScreenUIEvent.OnSendCountChanged(it)) },
                onLikesCountHiddenChanged = { onEvent(CreateScreenUIEvent.OnIsLikesCountHiddenChanged(it)) },
                onIsTaggedPeopleChanged = { onEvent(CreateScreenUIEvent.OnIsTaggedPeopleChanged(it)) },
                onPeopleTaggedChanged = { onEvent(CreateScreenUIEvent.OnPeopleTaggedChanged(it)) },
                onIsLocationExistChanged = { onEvent(CreateScreenUIEvent.OnIsLocationExistChanged(it)) },
                onLocationChanged = { onEvent(CreateScreenUIEvent.OnLocationChanged(it)) }
            )
            UserDetail(
                onImageChanged = { onEvent(CreateScreenUIEvent.OnUserImageChanged(it)) },
                username = state.username,
                description = state.description,
                isFollowed = state.isFollowed,
                onUsernameChanged = { onEvent(CreateScreenUIEvent.OnUsernameChanged(it)) },
                onDescriptionChanged = { onEvent(CreateScreenUIEvent.OnDescriptionChanged(it)) },
                onIsFollowedChanged = { onEvent(CreateScreenUIEvent.OnIsFollowedChanged(it)) }
            )
        }

        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState,
        )

    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    onButtonClick: () -> Unit,
    infiniteRepeatable: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.noRippleClickable { navigateUp() },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Button",
            tint = MaterialTheme.colorScheme.onBackground
        )
        ExportButton(onClick = onButtonClick, infiniteRepeatable = infiniteRepeatable)
    }
}

@Composable
fun BackgroundImage(
    modifier: Modifier = Modifier,
    onImageChanged: (Bitmap) -> Unit
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    LaunchedEffect(key1 = bitmap) {
        if (bitmap != null) {
            onImageChanged(bitmap!!)
        }
    }
    var showChooseDialog by remember { mutableStateOf(false) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            bitmap = uri?.toBitmap(context)
        }
    )
    val uri = context.getUriForFile()
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            bitmap = uri.toBitmap(context)
        }
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (it) {
                Toast.makeText(context, "Permission Granted.", Toast.LENGTH_SHORT)
                    .show()
                cameraLauncher.launch(uri)
            }
        }
    )

    if (showChooseDialog) {
        ImageChooseDialog(
            onDismiss = { showChooseDialog = false },
            onCameraClick = {
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) cameraLauncher.launch(
                    uri
                )
                else permissionLauncher.launch(Manifest.permission.CAMERA)
            },
            onGalleryClick = {
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
        )
    }
    Box(
        modifier = modifier
            .height(200.dp)
            .width(150.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable { showChooseDialog = true },
        contentAlignment = Alignment.Center
    ) {
        if (bitmap == null) {
            Icon(
                modifier = Modifier.size(width = 75.dp, height = 100.dp),
                painter = painterResource(id = R.drawable.ic_gallery),
                contentDescription = "Select Image",
                tint = Color.Gray
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                text = "Background Image",
                fontSize = 12.sp,
                color = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray
            )
        } else
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                model = bitmap,
                contentDescription = "Selected Image",
                contentScale = ContentScale.Crop,
            )


    }
}

@Composable
fun ReelsDetail(
    modifier: Modifier = Modifier,
    likesCount: String,
    commentCount: String,
    sendCount: String,
    isLiked: Boolean,
    isLikesCountHidden: Boolean,
    isTaggedPeople: Boolean,
    peopleTagged: String,
    isLocationExist: Boolean,
    location: String,
    isLikedChanged: (Boolean) -> Unit,
    onLikesCountChanged: (String) -> Unit,
    onCommentCountChanged: (String) -> Unit,
    onSendCountChanged: (String) -> Unit,
    onLikesCountHiddenChanged: (Boolean) -> Unit,
    onIsTaggedPeopleChanged: (Boolean) -> Unit,
    onPeopleTaggedChanged: (String) -> Unit,
    onIsLocationExistChanged: (Boolean) -> Unit,
    onLocationChanged: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }

    RoundedCornerBox(modifier = modifier) {
        CornerBoxTitleSection(
            modifier = Modifier,
            title = "Reels Detail",
            isExpanded = isExpanded,
            onClick = { isExpanded = it }
        )
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                QuestionWithCheckbox(
                    questionType = QuestionType.IS_LIKED,
                    state = isLiked,
                    onCheckedChange = { isLikedChanged(it) }
                )
                QuestionWithCheckbox(
                    questionType = QuestionType.LIKES_COUNT_HIDDEN,
                    state = isLikesCountHidden,
                    onCheckedChange = { onLikesCountHiddenChanged(it) }
                )
                if (!isLikesCountHidden) {
                    CustomInputField(
                        modifier = Modifier.padding(start = 16.dp),
                        questionType = QuestionType.LIKES_COUNT,
                        value = likesCount,
                        imeAction = ImeAction.Next,
                        onValueChanged = { onLikesCountChanged(it) }
                    )
                }

                Row {
                    CustomInputField(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(start = 16.dp),
                        questionType = QuestionType.COMMENT_COUNT,
                        value = commentCount,
                        imeAction = ImeAction.Next,
                        onValueChanged = { onCommentCountChanged(it) }
                    )
                    CustomInputField(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(start = 16.dp),
                        questionType = QuestionType.SEND_COUNT,
                        value = sendCount,
                        imeAction = ImeAction.Done,
                        onValueChanged = { onSendCountChanged(it) }

                    )
                }

                Row {
                    Column(modifier = Modifier) {
                        QuestionWithCheckbox(
                            questionType = QuestionType.IS_LOCATION_EXIST,
                            state = isLocationExist,
                            onCheckedChange = { onIsLocationExistChanged(it) }
                        )
                        if (isLocationExist) {
                            CustomInputField(
                                modifier = Modifier
                                    .width(150.dp)
                                    .padding(start = 16.dp),
                                questionType = QuestionType.LOCATION,
                                value = location,
                                imeAction = ImeAction.Done,
                                onValueChanged = { onLocationChanged(it) }
                            )
                        }
                    }
                    Column {
                        QuestionWithCheckbox(
                            questionType = QuestionType.IS_TAGGED_PEOPLE,
                            state = isTaggedPeople,
                            onCheckedChange = { onIsTaggedPeopleChanged(it) }
                        )
                        if (isTaggedPeople) {
                            CustomInputField(
                                modifier = Modifier
                                    .width(150.dp)
                                    .padding(start = 16.dp),
                                questionType = QuestionType.PEOPLE_TAGGED,
                                value = peopleTagged,
                                imeAction = ImeAction.Done,
                                onValueChanged = { onPeopleTaggedChanged(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetail(
    modifier: Modifier = Modifier,
    onImageChanged: (Bitmap) -> Unit,
    username: String,
    description: String,
    isFollowed: Boolean,
    onUsernameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onIsFollowedChanged: (Boolean) -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }
    var showChooseDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(key1 = bitmap) {
        if (bitmap != null) {
            onImageChanged(bitmap!!)
        }
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            bitmap = uri?.toBitmap(context)
        }
    )
    val uri = context.getUriForFile()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            bitmap = uri.toBitmap(context)
        }
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (it) {
                Toast.makeText(context, "Permission Granted.", Toast.LENGTH_SHORT).show()
                cameraLauncher.launch(uri)
            }
        }
    )

    if (showChooseDialog) {
        ImageChooseDialog(
            onDismiss = { showChooseDialog = false },
            onCameraClick = {
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) cameraLauncher.launch(
                    uri
                )
                else permissionLauncher.launch(Manifest.permission.CAMERA)
            },
            onGalleryClick = {
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
        )
    }

    RoundedCornerBox(modifier = modifier) {
        CornerBoxTitleSection(
            title = "User Detail",
            isExpanded = isExpanded,
            onClick = { isExpanded = it }
        )
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = modifier
                            .size(100.dp)
                            .border(
                                BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .noRippleClickable { showChooseDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        if (bitmap == null) {
                            Icon(
                                modifier = Modifier.size(width = 35.dp, height = 50.dp),
                                painter = painterResource(id = R.drawable.ic_gallery),
                                contentDescription = "Select Image",
                                tint = Color.Gray
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(8.dp),
                                text = "User Image",
                                fontSize = 12.sp,
                                color = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray
                            )
                        } else
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                model = bitmap,
                                contentDescription = "Selected Image",
                                contentScale = ContentScale.Crop,
                            )
                    }

                    Column {
                        CustomInputField(
                            modifier = Modifier.width(150.dp),
                            questionType = QuestionType.USERNAME,
                            value = username,
                            imeAction = ImeAction.Done,
                            onValueChanged = { onUsernameChanged(it) }
                        )
                        QuestionWithCheckbox(
                            questionType = QuestionType.IS_FOLLOWED,
                            state = isFollowed,
                            onCheckedChange = { onIsFollowedChanged(it) }

                        )
                    }
                }
                CustomInputField(
                    modifier = Modifier.fillMaxWidth(),
                    questionType = QuestionType.DESCRIPTION,
                    value = description,
                    imeAction = ImeAction.Done,
                    onValueChanged = { onDescriptionChanged(it) }

                )
            }
        }
    }
}

@Composable
fun CornerBoxTitleSection(
    modifier: Modifier = Modifier,
    title: String,
    isExpanded: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontWeight = FontWeight.W600
        )
        IconButton(onClick = { onClick(!isExpanded) }) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Expand Button",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun QuestionWithCheckbox(
    modifier: Modifier = Modifier,
    questionType: QuestionType,
    state: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = questionType.question,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                fontWeight = FontWeight.W500
            )
            Checkbox(
                checked = state,
                onCheckedChange = {
                    onCheckedChange(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageChooseDialog(
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp),
        onDismissRequest = onDismiss
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = "Choose",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Start)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .noRippleClickable {
                                onCameraClick.invoke()
                                onDismiss.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "Camera",
                        tint = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray
                    )
                    Text(
                        text = "Camera",
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray
                    )
                }
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .noRippleClickable {
                                onGalleryClick.invoke()
                                onDismiss.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_gallery),
                        contentDescription = "Gallery",
                        tint = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray
                    )
                    Text(
                        text = "Gallery",
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray
                    )
                }
            }
            TextButton(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.End),
                onClick = onDismiss
            ) {
                Text(
                    text = "CANCEL",
                    fontSize = 12.sp,
                    color = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray
                )
            }

        }
    }
}