package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable
import com.capstone.nongglenonggle.databinding.FragmentResumeBBinding
import com.capstone.nongglenonggle.domain.entity.ResumeSummary
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.view.adapter.ResumeAdapter
import com.capstone.nongglenonggle.presentation.view.dialog.CareerAddFragment
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.WorkerResumeComposeViewModel


class ResumeBFragment : BaseFragment<FragmentResumeBBinding>(R.layout.fragment_resume_b) {
    private lateinit var resumeAdapter: ResumeAdapter
    private val viewModel: ResumeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.nextBtn.setOnClickListener {
            val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewpager)
            val current = viewpager.currentItem
            val next = current + 1
            if (next < viewpager.adapter?.itemCount ?: 0) {
                viewpager.setCurrentItem(next, true)
            } else {
            }
        }


        binding.addCareer.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showAddCareer()
                //초기화 코드 추가하기
                viewModel.getClearData()
            }
            false
        }


    }

    override fun onResume() {
        super.onResume()
        resumeAdapter = ResumeAdapter(emptyList())
        binding.recycler.adapter = resumeAdapter
        viewModel.resumeData.observe(viewLifecycleOwner, Observer { newData: List<ResumeSummary> ->
            resumeAdapter.updateList(newData)
            binding.carrerSum.text = viewModel.getCareerTotal()
        })
    }

    private fun showAddCareer() {
        val newFrament = CareerAddFragment()
        newFrament.show(parentFragmentManager, "careerAdd")
    }

}

@Composable
fun ResumeStep2Screen(
    viewModel: WorkerResumeComposeViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val effectFlow = viewModel.effect

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
                text = context.getString(R.string.경력사항),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Medium
                ),
                color = NonggleTheme.colors.g1
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = context.getString(R.string.어플_사용_이전),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Normal
                ),
                color = NonggleTheme.colors.g2
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp, bottom = 16.dp)
                    .border(
                        BorderStroke(1.dp, NonggleTheme.colors.m1),
                        shape = RoundedCornerShape(4.dp)
                    ),

                ) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        textAlign = TextAlign.Center,
                        text = context.getString(R.string.경력_총),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            color = NonggleTheme.colors.m1
                        ))
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 32.dp)
                    .border(
                        BorderStroke(1.dp, NonggleTheme.colors.g_line),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .noRippleClickable {

                    },
            ) {
                Row(
                    //modifier = Modifier.align(alignment = Alignment.Horizontal)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                        textAlign = TextAlign.Center,
                        text = context.getString(R.string.경력_총),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            color = NonggleTheme.colors.m1
                        )
                    )
                    Spacer(modifier = Modifier.weight(weight = 1f))
                    Image(
                        modifier = Modifier.size(width = 24.dp, height = 24.dp),
                        painter = painterResource(R.drawable.right_small),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
//리서치 필요 부분
//1. 화면의 일정부분 차지하는 bottomsheet
@Composable
fun careerAddBottomSheetContent() {

}