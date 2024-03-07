package com.reelsjoke.app.data.repository

import com.reelsjoke.app.R
import com.reelsjoke.app.domain.model.SettingsItemData
import com.reelsjoke.app.domain.repository.SettingsRepository
import com.reelsjoke.app.domain.usecase.Response
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */
class SettingsRepositoryImpl @Inject constructor(
) : SettingsRepository {

    private val settings by lazy {
        listOf(
            SettingsItemData(
                leadingIcon = R.drawable.premium,
                leadingIconContentDescription = R.string.premium_icon_content_description,
                title = R.string.go_premium_title
            ),
            SettingsItemData(
                leadingIcon = R.drawable.ic_email,
                leadingIconContentDescription = R.string.email_icon_content_description,
                title = R.string.send_feedback_title
            ),
            SettingsItemData(
                leadingIcon = R.drawable.ic_share,
                leadingIconContentDescription = R.string.share_icon_content_description,
                title = R.string.share_title
            ),
            SettingsItemData(
                leadingIcon = R.drawable.ic_privacy,
                leadingIconContentDescription = R.string.privacy_icon_content_description,
                title = R.string.privacy_policy_title
            ),
            SettingsItemData(
                leadingIcon = R.drawable.ic_terms,
                leadingIconContentDescription = R.string.terms_icon_content_description,
                title = R.string.terms_of_use_title
            )
        )
    }

    override suspend fun getSettings(): Response<List<SettingsItemData>> = Response.Success(settings)
}