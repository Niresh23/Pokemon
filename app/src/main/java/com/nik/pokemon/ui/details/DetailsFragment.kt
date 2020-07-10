package com.nik.pokemon.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nik.pokemon.R
import com.nik.pokemon.databinding.DetailsFragmentBinding
import com.nik.pokemon.model.PokemonView

class DetailsFragment: Fragment() {
    companion object{
        fun newInstance() = DetailsFragment()
    }

    private var pokemonView: PokemonView? = null
    lateinit var binding: DetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonView = arguments?.getParcelable<PokemonView>("PokemonKey")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonView?.let {
            binding.pokemonView = it
        }
    }
}