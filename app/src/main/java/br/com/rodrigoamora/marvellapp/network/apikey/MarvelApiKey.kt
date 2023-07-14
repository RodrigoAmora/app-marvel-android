package br.com.rodrigoamora.marvellapp.network.apikey

import br.com.rodrigoamora.marvellapp.BuildConfig

class MarvelApiKey {
    companion object {
        const val API_KEY = BuildConfig.MARVEL_API_KEY
        const val MD5_HASH = BuildConfig.MARVEL_MD5_HASH
    }
}