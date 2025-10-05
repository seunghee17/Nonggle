package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.data.model.remote_model.SubRegion
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.LocalDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveRegionToLocalDataBaseUseCase @Inject constructor(
    private val localDataRepository: LocalDataRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(subRegions: List<SubRegion>) = withContext(ioDispatcher) {
        localDataRepository.saveRegionToLocalDB(subRegions)
    }
}