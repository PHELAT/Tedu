package com.phelat.tedu.settings.state

import com.phelat.tedu.sdkextensions.CounterBoolean

private const val NUMBER_OF_INPUT_FIELDS = 3

data class WebDavViewState(
    val isSaveButtonEnabled: CounterBoolean = CounterBoolean(
        requiredCountToBeTrue = NUMBER_OF_INPUT_FIELDS,
        counter = 0
    )
)