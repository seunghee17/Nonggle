package com.example.nongglenonggle.presentation.view.farmer.mypage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentScoreWorkerBinding
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.farmer.home.MainActivity
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerMyPageViewModel

class ScoreWorkerFragment : BaseFragment<FragmentScoreWorkerBinding>(R.layout.fragment_score_worker) {
    private val viewModel: FarmerMyPageViewModel by activityViewModels()

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

        //title
        viewModel.suggestionData.observe(viewLifecycleOwner, Observer { data->
            binding.title.text = "구인자님,\n${data.userName} 일손은 어떠셨나요?"
            binding.name.text = "${data.userName} 일손"
            binding.theother.text = "${data.userGender} ∙ ${data.userYear}세"
        })

        binding.seekbar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val thumb = when(progress){
                    0 -> R.drawable.num1
                    1 -> R.drawable.num1
                    2-> R.drawable.num2
                    3 -> R.drawable.num3
                    4 -> R.drawable.num4
                    else->R.drawable.num5
                }
                seekBar?.thumb = ContextCompat.getDrawable(requireContext(), thumb)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        binding.seekbar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val thumb = when(progress){
                    0 -> R.drawable.num1
                    1 -> R.drawable.num1
                    2-> R.drawable.num2
                    3 -> R.drawable.num3
                    4 -> R.drawable.num4
                    else->R.drawable.num5
                }
                seekBar?.thumb = ContextCompat.getDrawable(requireContext(), thumb)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        binding.seekbar3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val thumb = when(progress){
                    0 -> R.drawable.num1
                    1 -> R.drawable.num1
                    2-> R.drawable.num2
                    3 -> R.drawable.num3
                    4 -> R.drawable.num4
                    else->R.drawable.num5
                }
                seekBar?.thumb = ContextCompat.getDrawable(requireContext(), thumb)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        binding.seekbar4.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val thumb = when(progress){
                    0 -> R.drawable.num1
                    1 -> R.drawable.num1
                    2-> R.drawable.num2
                    3 -> R.drawable.num3
                    4 -> R.drawable.num4
                    else->R.drawable.num5
                }
                seekBar?.thumb = ContextCompat.getDrawable(requireContext(), thumb)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        binding.edit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val color = ContextCompat.getColor(requireContext(),R.color.m1)
                binding.complete.setBackgroundColor(color)

        }
    })

        binding.complete.setOnClickListener{
            Toast.makeText(context, "일손평가를 완료했습니다!",Toast.LENGTH_LONG).show()
            val destination = FarmerMypageFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer,destination)
            transaction.addToBackStack(null)
            transaction.commit()
        }

}

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.showBottomNavigation()
    }
}