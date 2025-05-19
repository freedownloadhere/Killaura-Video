package com.github.freedownloadhere.killauravideo.ui.utils

sealed class UISemanticSize(val applyOrder: ApplyOrder) {
    enum class ApplyOrder { BEFORE, AFTER }

    class Pixels(val amount: Int): UISemanticSize(ApplyOrder.BEFORE)

    class PercentOfParent(val percent: Double): UISemanticSize(ApplyOrder.BEFORE)

    data object ChildrenSum: UISemanticSize(ApplyOrder.AFTER)
}