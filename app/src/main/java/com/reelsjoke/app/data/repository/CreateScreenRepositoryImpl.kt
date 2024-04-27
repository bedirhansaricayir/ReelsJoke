package com.reelsjoke.app.data.repository

import com.reelsjoke.app.R
import com.reelsjoke.app.domain.model.CreateScreenItemData
import com.reelsjoke.app.domain.repository.CreateScreenRepository
import com.reelsjoke.app.domain.usecase.Response
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 26.04.2024.
 */

class CreateScreenRepositoryImpl @Inject constructor(
) : CreateScreenRepository {

    private val items by lazy {
        listOf(
            CreateScreenItemData(
                leadingIcon = R.drawable.heart_favorite,
                title = R.string.feedback_title
            ),
            CreateScreenItemData(
                leadingIcon = R.drawable.tag_people,
                title = R.string.tag_people_title
            ),
            CreateScreenItemData(
                leadingIcon = R.drawable.ic_location,
                title = R.string.add_location_title
            ),
            CreateScreenItemData(
                leadingIcon = R.drawable.sound,
                title = R.string.change_voice_title
            ),
            CreateScreenItemData(
                leadingIcon = R.drawable.user_information,
                title = R.string.user_information_title
            )
        )
    }
    override suspend fun getItems(): Response<List<CreateScreenItemData>> = Response.Success(items)
}