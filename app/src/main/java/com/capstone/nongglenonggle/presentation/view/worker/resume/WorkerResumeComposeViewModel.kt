package com.capstone.nongglenonggle.presentation.view.worker.resume

import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.domain.repository.AuthenticationRepository
import com.capstone.nongglenonggle.domain.usecase.AddByAgeUseCase
import com.capstone.nongglenonggle.domain.usecase.AddCategoryUseCase
import com.capstone.nongglenonggle.domain.usecase.AddGenderUseCase
import com.capstone.nongglenonggle.domain.usecase.AddRefToAddressUseCase
import com.capstone.nongglenonggle.domain.usecase.AddResumeRefToUserUseCase
import com.capstone.nongglenonggle.domain.usecase.AddResumeUseCase
import com.capstone.nongglenonggle.domain.usecase.AddTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkerResumeComposeViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val addResumeUseCase: AddResumeUseCase,
    private val addResumeRefToUserUseCase: AddResumeRefToUserUseCase,
    private val addRefToAddressUseCase: AddRefToAddressUseCase,
    private val addResumeByAgeUseCase: AddByAgeUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val addGenderUseCase: AddGenderUseCase,
    private val addTypeUseCase: AddTypeUseCase
): BaseViewModel<WorkerResumeContract.Event, WorkerResumeContract.State, WorkerResumeContract.Effect>(initialState = WorkerResumeContract.State()) {
    override fun reduceState(event: WorkerResumeContract.Event) {

    }

}