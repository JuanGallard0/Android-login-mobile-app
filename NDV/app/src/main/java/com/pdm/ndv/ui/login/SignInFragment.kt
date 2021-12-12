package com.pdm.ndv.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.pdm.ndv.MainActivity
import com.pdm.ndv.NDVApplication
import com.pdm.ndv.databinding.FragmentSignInBinding
import com.pdm.ndv.viewModels.LoginViewModel
import com.pdm.ndv.viewModels.LoginViewModelFactory

class SignInFragment : Fragment() {
    private val loginViewModelFactory by lazy {
        val app = requireActivity().application as NDVApplication
        LoginViewModelFactory(app.userRepository)
    }

    private val loginViewModel: LoginViewModel by viewModels {
        loginViewModelFactory
    }

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = loginViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        loginViewModel.responseSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                activity?.finish()
            }
            else {
                Toast.makeText(context, "Usuario o contraseña no son válidos",
                    Toast.LENGTH_LONG).show()
            }
        }

        loginViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error == null) {
                binding.usuarioInputLayout.error = null
            } else {
                binding.usuarioInputLayout.error = getString(error)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SignInFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}