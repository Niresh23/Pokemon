package com.nik.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.checkbox.MaterialCheckBox
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.ui.details.DetailsFragment
import com.nik.pokemon.ui.main.MainFragment
import com.nik.pokemon.ui.main.MainViewModel
import com.nik.pokemon.utils.OnPokemonClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnPokemonClickListener {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onClick(pokemon: PokemonView) {
        val key = "PokemonKey"
        val bundle = Bundle()
        bundle.putParcelable(key, pokemon)
        val fragment = DetailsFragment.newInstance()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    fun onCheckboxClicked(view: View) {
        if (view is MaterialCheckBox) {
            var attack = false
            var defence = false
            var hp = false

            view.findViewById<MaterialCheckBox>(R.id.attack_checkbox)?.let {
                attack = it.isChecked
            }
            view.findViewById<MaterialCheckBox>(R.id.defence_checkbox)?.let {
                defence = it.isChecked
            }
            view.findViewById<MaterialCheckBox>(R.id.hp_checkbox)?.let {
                hp = it.isChecked
            }
            viewModel.sortArrayBy(attack, defence, hp)
        }
    }

}