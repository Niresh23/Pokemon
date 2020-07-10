package com.nik.pokemon.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nik.pokemon.R
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.utils.*
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.RuntimeException

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var listener: OnPokemonClickListener
    private val recyclerViewAdapter: RecyclerViewAdapter<PokemonView, OnPokemonClickListener> by lazy {
        RecyclerViewAdapter<PokemonView, OnPokemonClickListener> (
            clickListener = listener,
            layout = R.layout.item_cardview,
            comparator = RecyclerViewAdapter.COMPARATOR
        )
    }

    private val progressAdapter: ProgressAdapter by lazy {
        ProgressAdapter { viewModel.getMoreData() }
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
        initUI()
        startObserve()
        viewModel.getData()
    }

    private fun initUI () {
        with(pokemonList) {
            val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = manager
            adapter = MergeAdapter(recyclerViewAdapter, progressAdapter)
            addOnScrollListenerPagination(manager) { viewModel.getMoreData() }
        }
    }

    private fun startObserve() {
        viewModel.failure.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
        })

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            recyclerViewAdapter.submitList(it)
        })


    }
}