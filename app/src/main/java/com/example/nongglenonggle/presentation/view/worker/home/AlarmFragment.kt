package com.example.nongglenonggle.presentation.view.worker.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentAlarmBinding
import com.example.nongglenonggle.domain.entity.AlarmDataModel
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.adapter.AlarmAdapter
import com.example.nongglenonggle.presentation.view.dialog.SuggestDialogFragment
import com.example.nongglenonggle.presentation.viewModel.worker.WorkerHomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
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