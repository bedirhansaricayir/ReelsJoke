package com.reelsjoke.app.domain.model


/**
 * Created by bedirhansaricayir on 1.02.2024.
 */
enum class ErrorType(val message: String) {
    BACKGROUND_IMAGE("Choose a background image"),
    COMMENT_COUNT("Fill in the comment count field"),
    SEND_COUNT("Fill in the send count field"),
    USER_IMAGE("Choose a user image"),
    USER_TITLE("Fill in the username field"),
    DESCRIPTION("Fill in the description field")
}