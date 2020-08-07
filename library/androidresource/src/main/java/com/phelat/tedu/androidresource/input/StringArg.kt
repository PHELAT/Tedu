package com.phelat.tedu.androidresource.input

import androidx.annotation.StringRes
import com.phelat.tedu.androidresource.Input

class StringArg(
    @StringRes val stringId: Int,
    vararg val stringArgs: Any
) : Input