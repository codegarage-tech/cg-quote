package com.franmontiel.attributionpresenter.view

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.franmontiel.attributionpresenter.view.AnimatedView

/**
 * Created by Chatikyan on 16.02.2017.
 */

class AnimatedTextView : AppCompatTextView, AnimatedView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setAnimatedText(text: CharSequence, startDelay: Long = 0L) {
        changeText(text, startDelay)
    }

    private fun changeText(newText: CharSequence, startDelay: Long) {
        if (text == newText)
            return
        animate(view = this, startDelay = startDelay) {
            text = newText
        }
    }
}
