package com.shostak.jt.ui.View

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.shostak.jt.R
import kotlinx.android.synthetic.main.section_view.view.*

class SectionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.section_view, this)
    }

    fun setTitle(title: String) {
        this.title.text = title
    }

    fun addBullet(bullet: String) {
        bulletsParent.addView(
            TextView(context).apply {
                alpha = 0F
                setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                text = bullet
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                    setMargins(0, 10, 0, 10)
                }
                animate().alpha(1F).setStartDelay(100L).start()
            }
        )
    }

}