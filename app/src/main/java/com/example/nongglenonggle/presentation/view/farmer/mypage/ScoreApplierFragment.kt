package com.example.nongglenonggle.presentation.view.farmer.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentScoreApplierBinding
import com.example.nongglenonggle.domain.entity.ScoreDataModel
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.adapter.ScoreForFarmerAdapter
import com.example.nongglenonggle.presentation.view.farmer.home.MainActivity
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerMyPageViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ScoreApplierFragment : BaseFragment<FragmentScoreApplierBinding>(R.layout.fragment_score_applier) {
    private lateinit var adapter : ScoreForFarmerAdapter
    private val viewModel: FarmerMyPageViewModel by activityViewModels()
    private val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore:FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        (activity as MainActivity)?.hideBottomNavi()

        adapter = ScoreForFarmerAdapter(emptyList(), object : ScoreForFarmerAdapter.onItemClickListener{
            override fun onItemClick(item: ScoreDataModel) {
                val destination = ScoreWorkerFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer,destination)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        })

        binding.recycler.adapter = adapter


        viewModel.workuid.observe(viewLifecycleOwner) { workuid ->
            if (workuid.isNotBlank()) {
                firestore.collection("Resume").document("public")
                    .collection("publicResume").document(workuid).get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            val scoreData = document.toObject(ScoreDataModel::class.java)
                            scoreData?.let {
                                viewModel._suggestionData.value = scoreData
                                Log.d("ScoreApplierFragment","${viewModel._suggestionData.value}")
                                adapter.updateList(listOf(it))
                            }
                        }
                    }
            }
        }


    }

}