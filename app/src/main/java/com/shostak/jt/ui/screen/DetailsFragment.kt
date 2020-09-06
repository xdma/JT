package com.shostak.jt.ui.screen

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.ChangeBounds
import com.google.android.material.transition.MaterialSharedAxis
import com.shostak.jt.R
import com.shostak.jt.model.ParsedJobModel
import com.shostak.jt.model.ViewModel
import com.shostak.jt.ui.View.SectionView
import com.shostak.jt.util.myFromHtml
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

//val args: DetailsFragmentArgs by navArgs

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: ViewModel by viewModels()
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            position = DetailsFragmentArgs.fromBundle(requireArguments()).position
        }

        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 600
            interpolator = DecelerateInterpolator()
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 600
            interpolator = DecelerateInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = viewModel.getItemByPosition(position)
        ViewCompat.setTransitionName(roleName, "role${position}")

        initUi(item)
    }

    private fun initUi(item: ParsedJobModel) {
        roleName.text = item.roleName
        mainText.apply {
            alpha = 0F
            text = myFromHtml(item.description.mainText)
            animate().alpha(1F).start()
        }

        item.description.sections?.forEachIndexed { index, section ->
            sectionView.addView(
                SectionView(requireContext()).apply {
                    alpha = 0F
                    setTitle(section.title)
                    section.bullets?.forEach {
                        addBullet(it)
                    }
                    animate().alpha(1F).setStartDelay(index * 300L).start()
                }
            )
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment()

    }
}
