package com.submission.dicodingstory.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicodingstory.databinding.FragmentDashboardBinding
import com.submission.dicodingstory.main.SPageAdapter
import com.submission.dicodingstory.main.StoryLoadingAdapter
import com.submission.dicodingstory.util.AccountPref

import com.submission.dicodingstory.vm.StoryVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val vm: StoryVM by viewModels()
    private lateinit var adapt: SPageAdapter
    @Inject
    lateinit var pref: AccountPref



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {super.onViewCreated(view, savedInstanceState)

        adapt = SPageAdapter(requireContext())
        binding.rvStory.apply {
            adapter = adapt.withLoadStateHeaderAndFooter(
                footer = StoryLoadingAdapter { adapt.retry()},
                header = StoryLoadingAdapter { adapt.retry()}
            )
            layoutManager = LinearLayoutManager(requireContext())}

        adapt.setOnItemClick {
            val action = DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        observableViewModel()
        loadState()}

    private fun observableViewModel()
    {
        vm.getMoreStory().observe(viewLifecycleOwner) { res
            ->
            adapt.submitData(lifecycle, res)}
    }

    private fun loadState()
    {
        adapt.addLoadStateListener { loadState ->
            binding.apply {
                rvStory.isVisible = loadState.source.refresh is LoadState.NotLoading
                loading.isVisible = loadState.source.refresh is LoadState.Loading
                rvStory.isVisible =
                    !(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapt.itemCount < 1) } } }
}