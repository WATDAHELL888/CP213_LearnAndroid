package com.example.a522lablearnandroid.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DataAndPokemonModelTest {

    @Test
    fun dataset_values_matchExpectedTypesAndValues() {
        assertEquals("1", dataset.a)
        assertEquals(2, dataset.b)
    }

    @Test
    fun pokemonSpecies_storesNameAndUrl() {
        val species = PokemonSpecies(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon-species/1/")
        assertEquals("bulbasaur", species.name)
        assertTrue(species.url.contains("pokemon-species"))
    }

    @Test
    fun pokemonEntry_storesNumberAndSpecies() {
        val species = PokemonSpecies(name = "ivysaur", url = "u")
        val entry = PokemonEntry(entry_number = 2, pokemon_species = species)

        assertEquals(2, entry.entry_number)
        assertEquals("ivysaur", entry.pokemon_species.name)
    }

    @Test
    fun pokedexResponse_holdsListOfEntries() {
        val entries = listOf(
            PokemonEntry(1, PokemonSpecies("bulbasaur", "u1")),
            PokemonEntry(4, PokemonSpecies("charmander", "u4"))
        )
        val response = PokedexResponse(entries)

        assertEquals(2, response.pokemon_entries.size)
        assertEquals("charmander", response.pokemon_entries[1].pokemon_species.name)
    }
}

