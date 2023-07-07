package com.submission.dicodingstory.account

import androidx.navigation.fragment.findNavController
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.submission.dicodingstory.databinding.FragmentSignInBinding
import com.submission.dicodingstory.error.OperationStatus
import com.submission.dicodingstory.main.MainActivity
import com.submission.dicodingstory.model.Account
import com.submission.dicodingstory.req.AccountReq
import com.submission.dicodingstory.util.AccountPref
import com.submission.dicodingstory.util.LoadingAnimation
import com.submission.dicodingstory.util.mySnackBar
import com.submission.dicodingstory.util.texTrim
import com.submission.dicodingstory.vm.AccountVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment()
{
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val vm: AccountVM by viewModels()
    @Inject
    lateinit var prefs: AccountPref
    @Inject
    lateinit var loading: LoadingAnimation


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvSignUp.setOnClickListener{
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)}


        binding.btnSignIn.setOnClickListener {
            sendRequest() }

        binding.edLoginEmail.doAfterTextChanged {
            setMyButton()
        }

        binding.edLoginPassword.doAfterTextChanged {
            setMyButton()
        }
        playAnimation()
        setMyButton()
    }

    private fun setMyButton()
    {
        val emailError = binding.edLoginEmail.error
        val passwordError = binding.edLoginPassword.error
        val email = binding.edLoginEmail.texTrim()
        val password = binding.edLoginPassword.texTrim()

        binding.btnSignIn.isEnabled = (email.isNotEmpty() && password.isNotEmpty())
                && (emailError == null && passwordError == null)}

    private fun sendRequest() {
        val email = binding.edLoginEmail.texTrim()
        val password = binding.edLoginPassword.texTrim()

        val request = AccountReq(email, password)
        observableViewModel(request)
    }

    private fun observableViewModel(request: AccountReq)
    { vm.signIn(request).observe(viewLifecycleOwner) { res ->
            when (res.status) {
                OperationStatus.LOADING -> loading.start(requireContext())
                OperationStatus.ERROR -> {
                    loading.stop()
                    view?.mySnackBar(res.message.toString())
                }
                OperationStatus.SUCCESS -> {
                    loading.stop()

                    val user = Account(
                        userId = res.data?.loginResult?.userId.toString(),
                        name = res.data?.loginResult?.name.toString(),
                        email = binding.edLoginEmail.texTrim(),
                        token = res.data?.loginResult?.token.toString()
                    )
                    prefs.setAcc(user)
                    prefs.setSignIn(true)

                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }


    private fun playAnimation() {
        with(binding) {
            val logoDicoding = ObjectAnimator.ofFloat(ivDicoding, View.ALPHA, 1f).setDuration(500)


            val titleSignIn = ObjectAnimator.ofFloat(tvSignIn, View.ALPHA, 1f).setDuration(500)


            val inputEmail = ObjectAnimator.ofFloat(llEmail, View.ALPHA, 1f).setDuration(500)


            val inputPassword = ObjectAnimator.ofFloat(llPassword, View.ALPHA, 1f).setDuration(500)


            val toSignUp = ObjectAnimator.ofFloat(llSignUp, View.ALPHA, 1f).setDuration(500)


            val signIn = ObjectAnimator.ofFloat(btnSignIn, View.ALPHA, 1f).setDuration(500)


            AnimatorSet().apply {
                playSequentially(logoDicoding, titleSignIn, inputEmail, inputPassword, toSignUp, signIn)
                start()
            }
        }
    }








}

