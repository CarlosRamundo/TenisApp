package com.example.tenisapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.tenisapp.R
import com.example.tenisapp.databinding.FragmentSelectPlayersBinding


class SelectPlayersFragment : Fragment(R.layout.fragment_select_players) {

    private lateinit var binding: FragmentSelectPlayersBinding
    private var numPlayers = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectPlayersBinding.bind(view)
        binding.btnGrandSlam.setOnClickListener {
            numPlayers = 128
            findNavController().navigate(R.id.action_selectPlayersFragment_to_torneoFragment)
        }
        binding.btnMaster.setOnClickListener {
            numPlayers = 96
            findNavController().navigate(R.id.action_selectPlayersFragment_to_torneoFragment)

        }
        binding.btnAtp.setOnClickListener {
            numPlayers = 32
            findNavController().navigate(R.id.action_selectPlayersFragment_to_torneoFragment)

        }
    }
}