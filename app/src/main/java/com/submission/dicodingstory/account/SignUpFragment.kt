package com.submission.dicodingstory.account

import androidx.navigation.fragment.findNavController
import com.submission.dicodingstory.databinding.FragmentSignUpBinding
import com.submission.dicodingstory.error.OperationStatus
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.submission.dicodingstory.req.SignupReq
import com.submission.dicodingstory.util.LoadingAnimation
import com.submission.dicodingstory.util.mySnackBar
import com.submission.dicodingstory.util.texTrim
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.submission.dicodingstory.vm.AccountVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.core.widget.doAfterTextChanged
import android.animation.AnimatorSet
import android.content.Context
import androidx.fragment.app.viewModels

@AndroidEntryPoint





class SignUpFragment : Fragment()

{ private var bind: FragmentSignUpBinding? = null
    private val binding get() = bind!!
    @Inject
    lateinit var loadingAnimation: LoadingAnimation
    private val vm: AccountVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View


    {
        bind = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)

    {
        super.onViewCreated(view, savedInstanceState)

        binding.tvToSignIn2.setOnClickListener {
            findNavController().navigateUp()}

        binding.edRegisterEmail.doAfterTextChanged {
            setButtonState()}

        binding.edRegisterName.doAfterTextChanged {
            setButtonState()}

        binding.edRegisterPassword.doAfterTextChanged {
            setButtonState()}

        binding.btnSignUp.setOnClickListener {
            performSignUp()
        }

        goyang()
        setButtonState()
    }

    private fun performSignUp()

    {
        val nama = binding.edRegisterName.texTrim()
        val emailuniqe = binding.edRegisterEmail.texTrim()
        val passwordunique = binding.edRegisterPassword.texTrim()

        val signupRequest = SignupReq(nama, emailuniqe, passwordunique)
        observeViewModel(signupRequest)
    }

    private fun observeViewModel(request: SignupReq)
    {
        vm.signUp(request).observe(viewLifecycleOwner)

        { result ->
            when (result.status)

            {
                OperationStatus.LOADING -> performLoadingAnimation(requireContext())
                OperationStatus.ERROR ->

                {
                    stopLoadingAnimation()
                    view?.mySnackBar(result.message.toString())
                }
                OperationStatus.SUCCESS ->

                {
                    stopLoadingAnimation()
                    view?.mySnackBar(result.data?.message.toString())
                    navigateUp()}}}
    }

    private fun performLoadingAnimation(context: Context)
    {
        loadingAnimation.start(context)
    }

    private fun stopLoadingAnimation()
    {
        loadingAnimation.stop()
    }

    private fun navigateUp()
    {
        findNavController().navigateUp()
    }


    private fun goyang()
    {
        with(binding)
        {
            val logoAnimator = ObjectAnimator.ofFloat(ivLogoDicoding, View.ALPHA, 1f).setDuration(500)
            val titleAnimator = ObjectAnimator.ofFloat(tvSignUp, View.ALPHA, 1f).setDuration(500)
            val inputNameAnimator = ObjectAnimator.ofFloat(llName, View.ALPHA, 1f).setDuration(500)
            val inputEmailAnimator = ObjectAnimator.ofFloat(llEmail, View.ALPHA, 1f).setDuration(500)
            val inputPasswordAnimator = ObjectAnimator.ofFloat(linearPassword, View.ALPHA, 1f).setDuration(500)
            val toSignInAnimator = ObjectAnimator.ofFloat(llSignIn, View.ALPHA, 1f).setDuration(500)
            val signUpAnimator = ObjectAnimator.ofFloat(btnSignUp, View.ALPHA, 1f).setDuration(500)

            AnimatorSet().apply{
                playSequentially(
                    logoAnimator,
                    titleAnimator,
                    inputNameAnimator,
                    inputEmailAnimator,
                    inputPasswordAnimator,
                    toSignInAnimator,
                    signUpAnimator
                )
                start()}}
    }

    private fun setButtonState() {
        val nameInputError = binding.edRegisterName.error
        val emailInputError = binding.edRegisterEmail.error
        val passwordInputError = binding.edRegisterPassword.error

        val nameValue = binding.edRegisterName.texTrim()
        val emailValue = binding.edRegisterEmail.texTrim()
        val passwordValue = binding.edRegisterPassword.texTrim()

        val isNameValid = nameValue.isNotEmpty() && nameInputError == null
        val isEmailValid = emailValue.isNotEmpty() && emailInputError == null
        val isPasswordValid = passwordValue.isNotEmpty() && passwordInputError == null

        binding.btnSignUp.isEnabled = isNameValid && isEmailValid && isPasswordValid
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        bind = null
    }
}
