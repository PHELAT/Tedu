package com.phelat.tedu.uiview

import android.os.Bundle

sealed class Navigate {

    data class ToDirection(val directionId: DirectionId, val bundle: Bundle? = null) : Navigate()

    data class ToDeepLink(val deepLink: String) : Navigate()

    object Up : Navigate()

    object Recreate : Navigate()
}