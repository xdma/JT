package com.shostak.jt.ui.screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.ChangeBounds
import com.shostak.jt.R
import com.shostak.jt.model.ViewModel
import com.shostak.jt.ui.JobListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_jobs_list.*
import kotlinx.android.synthetic.main.job_item_row.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class JobsListFragment() : Fragment(),
    CoroutineScope {

    private val viewModel: ViewModel by viewModels()
    private val jobsAdapter = JobListAdapter()

    override val coroutineContext: CoroutineContext
        get() = viewLifecycleOwner.lifecycleScope.coroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        return inflater.inflate(R.layout.fragment_jobs_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            whenCreated {
                configRecyclerview()
                observeLiveData()
                initSearchView()
            }
        }
    }

    private suspend fun observeLiveData() {
        viewModel.loadJobs().observe(viewLifecycleOwner, Observer {
            jobsAdapter.submitList(it)

            /** to get formatted json array
             * use the:  viewModel.getFormattedJson()
             */
        })
    }

    private fun initSearchView() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.runSearch(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        }

        searchTv.addTextChangedListener(textWatcher)
    }

    override fun onResume() {
        super.onResume()
    }


    private fun configRecyclerview() {
        val llm = GridLayoutManager(context, 2)

        recyclerView.layoutManager = llm

        jobsAdapter.onItemClicked = {

            val action = JobsListFragmentDirections.actionJobsListFragmentToDetailsFragment(it)
            findNavController()
                .navigate(
                    action,
                    FragmentNavigatorExtras(
                        role to "role$it"
                    )
                )
        }

//        jobsAdapter.setHasStableIds(false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = jobsAdapter
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            JobsListFragment()
    }
}