package com.blacklane.shared.utils.strings

import android.content.Context

sealed class StringProvider {

    fun text(context: Context?): CharSequence = generateText(context)

    protected open fun generateText(context: Context?): CharSequence = ""

    private data class ResourceText(val resId: Int) : StringProvider() {
        override fun generateText(context: Context?): CharSequence = context?.getText(resId) ?: ""
    }

    companion object {
        fun of(resId: Int): StringProvider = ResourceText(resId)
    }
}