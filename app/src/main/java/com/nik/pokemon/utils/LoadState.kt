package com.nik.pokemon.utils

/**
 * LoadState of a list load
 */
sealed class LoadState {
    /**
     * Loading is in progress.
     */
    object Loading : LoadState()

    /**
     * Loading is complete.
     */
    object Done : LoadState()

    /**
     * Loading hit an error.
     *
     * @param error [Throwable] that caused the load operation to generate this error state.
     *
     */
    data class Error(val error: Throwable) : LoadState() {
        override fun toString() = "Error: $error"
    }
}