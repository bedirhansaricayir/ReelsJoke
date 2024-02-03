package com.reelsjoke.app.domain.usecase

import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.domain.repository.ReelsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

class GetCreatedReelsUseCase @Inject constructor(
    private val repository: ReelsRepository
) {
    suspend operator fun invoke(): Flow<Response<List<ScreenInfo>>> = flow {
        try {
            emit(Response.Loading)
            repository.getCreatedReels().collect { reels ->
                emit(Response.Success(data = reels ?: emptyList()))
            }
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = "A problem occurred while fetching data."))
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: "UNKNOWN"))
        }
    }
}