package com.nik.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.ui.main.MainFragment
import com.nik.pokemon.utils.OnPokemonClickListener

class MainActivity : AppCompatActivity(), OnPokemonClickListener {

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
    }
}