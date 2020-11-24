package org.gradproj.CardioCheckIn.helper

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationLinear {
    private var MARGIN_WIDTH = 0
    private var amount = 0

    fun ItemDecorationLinear(context: Context, width: Float, itemAmount: Int) {
        var itemAmount = itemAmount
        MARGIN_WIDTH = dpToPx(context, width.toDouble())
        amount = --itemAmount
    }

    fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        super.getItemOffsets(outRect, view, parent, state)
        val position: Int = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.left = 0
            outRect.right = MARGIN_WIDTH
        } else if (position == amount) {
            outRect.left = MARGIN_WIDTH
            outRect.right = 0
        } else {
            outRect.right = MARGIN_WIDTH
            outRect.left = MARGIN_WIDTH
        }
    }

    // dp -> pixel 단위로 변경
    private fun dpToPx(context: Context, dp: Double): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(), context.resources.displayMetrics
        ).toInt()
    }
}