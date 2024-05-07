package com.reelsjoke.app.domain.model


enum class BottomSheetType(val title: String) {
    NONE(""),
    FEEDBACK("Likes,comments,sends"),
    TAG_PEOPLE("Tag people"),
    ADD_LOCATION("Add location"),
    CHANGE_VOICE("Change voice name"),
    USER_INFORMATION("User information"),
    USERNAME("Username")
}