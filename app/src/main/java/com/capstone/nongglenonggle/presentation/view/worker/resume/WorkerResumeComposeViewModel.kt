package com.capstone.nongglenonggle.presentation.view.worker.resume

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkerResumeComposeViewModel @Inject constructor(

): BaseViewModel<WorkerResumeContract.Event, WorkerResumeContract.State, WorkerResumeContract.Effect>(initialState = WorkerResumeContract.State()) {
    override fun reduceState(event: WorkerResumeContract.Event) {

    }

}