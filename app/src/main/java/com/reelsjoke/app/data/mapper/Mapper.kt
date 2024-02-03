package com.reelsjoke.app.data.mapper

import com.reelsjoke.app.data.entity.ScreenInfoEntity
import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

interface Mapper {

    fun ScreenInfoEntity.cacheToDomain(): ScreenInfo

    fun ScreenInfo.domainToCache(): ScreenInfoEntity
}