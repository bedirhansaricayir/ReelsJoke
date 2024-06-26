package com.reelsjoke.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

@Entity(tableName = "ScreenInfo")
data class ScreenInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    val isLiked: Boolean,
    val likes: String,
    val commentCount: String,
    val send: String,
    val taggedPeople: String? = null,
    val location: String? = null,
    val backgroundImage: String,
    val soundImage: String,
    val userImage: String,
    val userTitle: String,
    val isUserFollowed: Boolean,
    val isVerified: Boolean,
    val voiceName: String
)
