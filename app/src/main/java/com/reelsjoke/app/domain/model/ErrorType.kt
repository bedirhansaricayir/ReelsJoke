package com.reelsjoke.app.domain.model


/**
 * Created by bedirhansaricayir on 1.02.2024.
 */
enum class ErrorType(val message: String) {
    BACKGROUND_IMAGE("Fill in the background image field"),
    COMMENT_COUNT("Fill in the comment count field"),
    SEND_COUNT("Fill in the send field"),
    USER_IMAGE("Fill in the user image field"),
    USER_TITLE("Fill in the user title field"),
    DESCRIPTION("Fill in the description field"),
}