package com.reelsjoke.app.domain.model


/**
 * Created by bedirhansaricayir on 25.01.2024.
 */


enum class QuestionType(val question: String) {
    IS_LIKED(question = "Is Liked?"),
    LIKES_COUNT_HIDDEN(question = "Likes Count Hidden?"),
    LIKES_COUNT(question = "Likes Count"),
    COMMENT_COUNT(question = "Comment Count"),
    SEND_COUNT(question = "Send Count"),
    IS_FOLLOWED(question = "Is Followed"),
    USERNAME(question = "Username"),
    DESCRIPTION(question = "Description"),
    IS_TAGGED_PEOPLE(question = "Is Tagged People?"),
    PEOPLE_TAGGED(question = "People Tagged"),
    IS_LOCATION_EXIST(question = "Location Exist?"),
    LOCATION(question = "Location")

}