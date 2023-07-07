package com.submission.dicodingstory.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.submission.dicodingstory.account.AccountActivity
import com.submission.dicodingstory.databinding.FragmentOptionBinding
import com.submission.dicodingstory.util.AccountPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OptionFragment : Fragment() {
    private var _binding: FragmentOptionBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var pref: AccountPref



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentOptionBinding.inflate(inflater, container, false)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.actionLogout.setOnClickListener{
            signOut()}

   }

    private fun signOut()
    {
        pref.signOut()

        pref.signOut()

        val intent = Intent(activity, AccountActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

        activity?.finish()}



}