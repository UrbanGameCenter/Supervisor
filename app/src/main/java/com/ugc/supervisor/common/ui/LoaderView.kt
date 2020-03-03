package com.ugc.supervisor.common.ui

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.ugc.supervisor.R
import kotlinx.android.synthetic.main.splashscreen_activity.*

class LoaderView : AppCompatImageView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        this.setImageResource(R.drawable.ic_progress_wheel)
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent))
        )
    }

    public fun show(){
        this.visibility = View.VISIBLE

        val rotation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        rotation.repeatCount = Animation.INFINITE
        this.startAnimation(rotation)

    }

    public fun hide(){
        this.clearAnimation()
        this.visibility = View.GONE
    }

}