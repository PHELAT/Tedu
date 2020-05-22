package com.phelat.tedu.settings.state

import com.phelat.tedu.sdkextensions.CircuitBoolean
import com.phelat.tedu.settings.viewmodel.WebDavViewModel.Companion.PASSWORD_FIELD_SWITCH
import com.phelat.tedu.settings.viewmodel.WebDavViewModel.Companion.URL_FIELD_SWITCH
import com.phelat.tedu.settings.viewmodel.WebDavViewModel.Companion.USERNAME_FIELD_SWITCH

data class WebDavViewState(
    val isSaveButtonEnabled: CircuitBoolean = CircuitBoolean(
        switches = mapOf(
            URL_FIELD_SWITCH to false,
            USERNAME_FIELD_SWITCH to false,
            PASSWORD_FIELD_SWITCH to false
        )
    )
)