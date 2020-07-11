package com.nik.pokemon.data.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(pokemon: List<PokemonEntity>): Completable

    @Update
    fun update(valuta: List<PokemonEntity>): Completable

    @Query("SELECT * FROM PokemonEntity WHERE id = :id")
    fun getById(id: Int): Single<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity")
    fun getList(): Single<List<PokemonEntity>>

    @Query("DELETE FROM PokemonEntity")
    fun deleteAll(): Completable

    @Query("SELECT * FROM PokemonEntity LIMIT:pageSize OFFSET:pageIndex")
    fun getPokemonOffset(pageSize: Int, pageIndex: Int): Single<List<PokemonEntity>>

}