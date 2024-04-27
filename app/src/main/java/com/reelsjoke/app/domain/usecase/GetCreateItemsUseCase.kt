package com.reelsjoke.app.domain.usecase

import com.reelsjoke.app.domain.model.CreateScreenItemData
import com.reelsjoke.app.domain.repository.CreateScreenRepository
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 26.04.2024.
 */

class GetCreateItemsUseCase @Inject constructor(
    private val createsScreenRepository: CreateScreenRepository
) {
    suspend operator fun invoke(): Response<List<CreateScreenItemData>> = createsScreenRepository.getItems()
}