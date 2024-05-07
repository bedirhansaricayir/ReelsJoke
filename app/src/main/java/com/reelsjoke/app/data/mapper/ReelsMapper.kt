package com.reelsjoke.app.data.mapper

import com.reelsjoke.app.core.extensions.toBase64
import com.reelsjoke.app.core.extensions.toBitmap
import com.reelsjoke.app.data.entity.ScreenInfoEntity
import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */
object ReelsMapper : Mapper {
    override fun ScreenInfoEntity.cacheToDomain(): ScreenInfo = with(this) {
        ScreenInfo(
            description = description,
            isLiked = isLiked,
            likes = likes,
            commentCount = commentCount,
            send = send,
            taggedPeople = taggedPeople,
            location = location,
            backgroundImage = backgroundImage.toBitmap()!!,
            soundImage = soundImage.toBitmap()!!,
            userImage = userImage.toBitmap()!!,
            userTitle = userTitle,
            isUserFollowed = isUserFollowed,
            isVerified = isVerified,
            voiceName = voiceName
        )
    }

    override fun ScreenInfo.domainToCache(): ScreenInfoEntity = with(this) {
        ScreenInfoEntity(
            description = description,
            isLiked = isLiked,
            likes = likes,
            commentCount = commentCount,
            send = send,
            taggedPeople = taggedPeople,
            location = location,
            backgroundImage = backgroundImage!!.toBase64(),
            soundImage = soundImage!!.toBase64(),
            userImage = userImage!!.toBase64(),
            userTitle = userTitle,
            isUserFollowed = isUserFollowed,
            isVerified = isVerified,
            voiceName = voiceName
        )
    }
}