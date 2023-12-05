package com.capstone.nongglenonggle.presentation.view.worker.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentAlarmBinding
import com.capstone.nongglenonggle.domain.entity.AlarmDataModel
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.view.adapter.AlarmAdapter
import com.capstone.nongglenonggle.presentation.view.dialog.SuggestDialogFragment
import com.capstone.nongglenonggle.presentation.viewModel.worker.WorkerHomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.AndroidEntryPoint

//구직자의 알람페이지
@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>(R.layout.fragment_alarm) {
    private lateinit var adapter : AlarmAdapter
    private val viewModel:WorkerHomeViewModel by viewModels()
    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var listenerRegistration : ListenerRegistration


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AlarmAdapter(emptyList(), object : AlarmAdapter.onItemClickListener{
            override fun onItemClick(item: AlarmDataModel) {
                showDialog()
            }
        })

        binding.recycler.adapter= adapter
        val uid = firebaseAuth.currentUser?.uid
        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }
        //suggest1과 currentTime
        val collectionRef = firestore.collection("Worker").document(uid!!)

        listenerRegistration = collectionRef.addSnapshotListener{snapshot, e->
            if(e != null){
                Log.e("AlarmFragment", "$e")
                return@addSnapshotListener
            }
            if(snapshot != null && snapshot.exists()){
                val offererName = snapshot.getString("offererName")
                if(offererName != null){
                    val dataModel = snapshot.toObject(AlarmDataModel::class.java)
                    dataModel?.let{
                        binding.recycler.visibility = View.VISIBLE
                        adapter.updateList(listOf(it))
                    }
                }
            }
            else{
                adapter.updateList(emptyList())
                binding.recycler.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerRegistration.remove()
    }

    private fun showDialog(){
        val newDialog = SuggestDialogFragment()
        newDialog.show(parentFragmentManager,"suggest")
    }

}