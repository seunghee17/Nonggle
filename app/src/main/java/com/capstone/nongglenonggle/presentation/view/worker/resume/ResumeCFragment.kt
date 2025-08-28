package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.NonggleIconButton
import com.capstone.nongglenonggle.core.common.textfield.NonggleTextField
import com.capstone.nongglenonggle.core.common.textfield.TextFieldType
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.databinding.FragmentResumeCBinding
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.util.hideClearButton
import com.capstone.nongglenonggle.presentation.util.showClearButton

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

        binding.additionalPresent.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.additional_present = s.toString()
            }

        })

        binding.nextBtn.setOnClickListener{
            val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewpager)
            val current = viewpager.currentItem
            val next = current+1
            if(next < viewpager.adapter?.itemCount ?: 0){
                viewpager.setCurrentItem(next,true)
            }else{
            }
        }

    }

}

@Composable
fun ResumeStep3Screen(
    viewModel: WorkerResumeComposeViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val effectFlow = viewModel.effect
    val focusManager = LocalFocusManager.current

    var isPersonalityFieldFocused by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                else -> {}
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = context.getString(R.string.자기소개),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Medium
                ),
                color = NonggleTheme.colors.g1
            )

        }
    }
}

@Composable
fun expressMyPersonality(
    context: Context,
) {
    NonggleTextField(
        modifier = Modifier
            .padding(bottom = 14.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .onFocusChanged {  },
        textFieldType = TextFieldType.Standard,
        value = "",
        onValueChange = {

        },
        textStyle = NonggleTheme.typography.b1_main,
        textColor = Color.Black,
        trailingIcon = {

        },

        placeholder = {
            Text(
                text = context.getString(R.string.본인을_한문장으로),
                style = NonggleTheme.typography.b1_main,
                color = NonggleTheme.colors.g3,
            )
        },
    )
}