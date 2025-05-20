package com.github.freedownloadhere.killauravideo.ui.utils

import com.github.freedownloadhere.killauravideo.ui.UI

sealed class UISemanticSize(val applyOrder: ApplyOrder) {
    enum class ApplyOrder { BEFORE, AFTER }

    abstract fun UI.applyProperty(enumProperty: UI.EnumProperty)

    class Pixels(val amount: Double): UISemanticSize(ApplyOrder.BEFORE) {
        override fun UI.applyProperty(enumProperty: UI.EnumProperty) {
            val property = getProperty(enumProperty)
            property.number = amount
        }
    }

    class PercentOfParent(val percent: Double): UISemanticSize(ApplyOrder.BEFORE) {
        override fun UI.applyProperty(enumProperty: UI.EnumProperty) {
            val property = getProperty(enumProperty)
            val parentLength = parent?.getProperty(enumProperty)?.number
            assert(parentLength != null)
            property.number = parentLength!! * 0.01 * percent
        }
    }

    data object ChildrenSum: UISemanticSize(ApplyOrder.AFTER) {
        override fun UI.applyProperty(enumProperty: UI.EnumProperty) {
            TODO("Not yet implemented")
        }
    }
}