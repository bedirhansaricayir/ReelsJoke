package com.reelsjoke.app.domain.repository

import com.reelsjoke.app.domain.model.CreateScreenItemData
import com.reelsjoke.app.domain.usecase.Response

/**
 * Created by bedirhansaricayir on 26.04.2024.
 */

interface CreateScreenRepository {

    suspend fun getItems(): Response<List<CreateScreenItemData>>
}