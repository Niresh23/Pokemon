package com.nik.pokemon.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nik.pokemon.R
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.utils.DiffUtilsCallback
import com.nik.pokemon.utils.OnPokemonClickListener
import com.nik.pokemon.utils.RecyclerViewAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.RuntimeException

class MainFragment : Fragment() {

    private val viewModelD: MainViewModel by viewModel()

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var listener: OnPokemonClickListener
    private val recyclerViewAdapter: RecyclerViewAdapter<PokemonView, OnPokemonClickListener> by lazy {
        RecyclerViewAdapter<PokemonView, OnPokemonClickListener> (
            clickListener = listener,
            layout = R.layout.item_cardview
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnPokemonClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnPokemonClickListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelD.liveData.observe(viewLifecycleOwner, Observer {
            val diffUtilCallback = DiffUtilsCallback(recyclerViewAdapter.data, it)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            recyclerViewAdapter.update(it)
            diffResult.dispatchUpdatesTo(recyclerViewAdapter)
            pokemonList.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = recyclerViewAdapter
            }
        })
        viewModelD.getData()
    }
}