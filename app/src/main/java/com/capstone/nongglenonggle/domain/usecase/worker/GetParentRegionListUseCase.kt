package com.capstone.nongglenonggle.domain.usecase.worker

import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.RegionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetParentRegionListUseCase @Inject constructor(
    private val regionRepository: RegionRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<String> = withContext(ioDispatcher) {
        regionRepository.getRegionList()
    }
}