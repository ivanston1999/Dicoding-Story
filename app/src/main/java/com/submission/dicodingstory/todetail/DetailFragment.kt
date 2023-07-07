package com.submission.dicodingstory.todetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.submission.dicodingstory.databinding.FragmentDetailBinding
import com.submission.dicodingstory.util.setImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(){
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {_binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root}



    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener{
            findNavController().navigateUp()}



        with(binding) {
            ivDetailPhoto.setImage(args.story.photoUrl)
            tvDetailName.text = args.story.name
            tvDetailDescription.text = args.story.description}}





}

