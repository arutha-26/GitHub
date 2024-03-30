package com.example.github.util

import androidx.annotation.StringRes
import com.example.github.R

object Constanta {
    const val TIME_SPLASH = 2000L

    const val EXTRA_USER = "EXTRA_USER"

    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.followers,
        R.string.following
    )

    const val GITHUB_TOKEN = "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"

    const val BASE_URL = "https://api.github.com"
}