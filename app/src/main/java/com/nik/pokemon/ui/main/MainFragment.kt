package com.nik.pokemon.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
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
import com.google.android.material.checkbox.MaterialCheckBox
import com.nik.pokemon.R
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.utils.*
import kotlinx.android.synthetic.main.landscape_progress_item.*
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.RuntimeException

class MainFragment : Fragment(), View.OnClickListener {

    private val viewModel: MainViewModel by viewModel()

    private var isConnected: Boolean = false

    lateinit var attackCheckBox: MaterialCheckBox
    lateinit var defenceCheckBox: MaterialCheckBox
    lateinit var hpCheckBox: MaterialCheckBox

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
        ProgressAdapter { viewModel.getMoreData(isConnected) }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        isConnected = getIsConnected(context)
        if(context is OnPokemonClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnPokemonClickListener")
        }
    }

    fun onCheckboxClicked(view: View) {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attackCheckBox = view.findViewById<MaterialCheckBox>(R.id.attack_checkbox)
        defenceCheckBox = view.findViewById<MaterialCheckBox>(R.id.defence_checkbox)
        hpCheckBox = view.findViewById<MaterialCheckBox>(R.id.hp_checkbox)

        attack_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.sortArrayBy(attackCheckBox.isChecked, defenceCheckBox.isChecked, hpCheckBox.isChecked)
        }
        defenceCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.sortArrayBy(attackCheckBox.isChecked, defenceCheckBox.isChecked, hpCheckBox.isChecked)
        }
        hpCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.sortArrayBy(attackCheckBox.isChecked, defenceCheckBox.isChecked, hpCheckBox.isChecked)
        }
        btn_random.setOnClickListener {
            viewModel.getRandom(isConnected)
            setProgressBarVisibility(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        startObserve()
        viewModel.getData(isConnected)
    }

    private fun initUI () {
        setProgressBarVisibility(true)
        with(pokemonList) {
            val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = manager
            adapter = MergeAdapter(recyclerViewAdapter, progressAdapter)
            addOnScrollListenerPagination(manager) { viewModel.getMoreData(isConnected) }
        }

    }

    private fun startObserve() {
        viewModel.failure.observe(viewLifecycleOwner, Observer { error ->
            setProgressBarVisibility(false)
            Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
        })

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            setProgressBarVisibility(false)
            recyclerViewAdapter.submitList(it)
        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            progressAdapter.loadState = it
        })
    }

    private fun setProgressBarVisibility(isShown: Boolean) {
        if(isShown) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

    private fun getIsConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    override fun onClick(view: View?) {
        viewModel.sortArrayBy(attackCheckBox.isChecked, defenceCheckBox.isChecked, hpCheckBox.isChecked)
    }
}