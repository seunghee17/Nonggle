package com.example.nongglenonggle.presentation.view.worker.resume

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentResumeCBinding
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.util.hideClearButton
import com.example.nongglenonggle.presentation.util.showClearButton
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel

class ResumeCFragment : BaseFragment<FragmentResumeCBinding>(R.layout.fragment_resume_c) {
    private val viewModel: ResumeViewModel by activityViewModels()

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

        binding.present.setOnFocusChangeListener{view,isfocus->
            viewModel.setActivePresent(isfocus)
            if(isfocus && binding.present.text?.isNotEmpty()==true){
                binding.present.showClearButton(R.drawable.xcircle)
            }else{
                binding.present.hideClearButton()
            }
        }
        binding.present.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.present.isFocused && s?.isNotEmpty() == true){
                    binding.present.showClearButton(R.drawable.xcircle)
                }
                else{
                    binding.present.hideClearButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.activePresent.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.present.text!=null){
                        binding.present.showClearButton(R.drawable.xcircle)
                    }else{
                        binding.present.hideClearButton()
                    }
                }
                viewModel.presentTxt = s.toString()
            }

        })

        //나의성격
        binding.character.setOnFocusChangeListener{view,isfocus->
            viewModel.setActiveCharacter(isfocus)
            if(isfocus && binding.character.text?.isNotEmpty()==true){
                binding.character.showClearButton(R.drawable.xcircle)
            }else{
                binding.character.hideClearButton()
            }
        }
        binding.character.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.character.isFocused && s?.isNotEmpty() == true){
                    binding.character.showClearButton(R.drawable.xcircle)
                }
                else{
                    binding.character.hideClearButton()
                }
                viewModel._activeCharacterConfirm.postValue(true)
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.activeCharacter.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.character.text!=null){
                        binding.character.showClearButton(R.drawable.xcircle)
                    }else{
                        binding.character.hideClearButton()
                    }
                }
                binding.confirm.setOnClickListener{
                    viewModel.storeCharacter(s.toString())
                    binding.character.text.clear()
                }
            }

        })

        viewModel.characterList.observe(viewLifecycleOwner,{list->
            if(list.isNotEmpty() && list.size==1){
                binding.flexBox.visibility = View.VISIBLE
                binding.characterA.visibility = View.VISIBLE
                binding.characterATxt.text = viewModel.characterList.value?.get(0)
            }
            else if(list.isNotEmpty() && list.size==2){
                binding.characterB.visibility = View.VISIBLE
                binding.characterBTxt.text = viewModel.characterList.value?.get(1)
            }
            else if(list.isNotEmpty() && list.size==3){
                binding.characterC.visibility = View.VISIBLE
                binding.characterCTxt.text = viewModel.characterList.value?.get(2)
            }
            else if(list.isEmpty()){
                binding.flexBox.visibility = View.GONE
            }
        })
        binding.characterAClose.setOnClickListener{
            viewModel.removeCharacter(0)
        }
        binding.characterBClose.setOnClickListener{
            viewModel.removeCharacter(1)
            binding.characterB.visibility = View.GONE
        }
        binding.characterCClose.setOnClickListener{
            viewModel.removeCharacter(2)
            binding.characterC.visibility = View.GONE
        }

        binding.nextBtn.setOnClickListener{
            val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
            val current = viewpager.currentItem
            val next = current+1
            if(next < viewpager.adapter?.itemCount ?: 0){
                viewpager.setCurrentItem(next,true)
            }else{
                Log.e("yet","아직 마지막아님")
            }
        }

    }

}