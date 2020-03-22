package com.phelat.tedu.uiview

import android.os.Bundle

sealed class Navigate {

    data class ToDirection(val directionId: DirectionId, val bundle: Bundle? = null) : Navigate()

    object Up : Navigate()

    object Recreate : Navigate()
}