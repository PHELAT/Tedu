package com.phelat.tedu.contributors.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.analytics.di.qualifier.Development
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.entity.ContributorEntity
import com.phelat.tedu.contributors.state.ContributionsViewState
import com.phelat.tedu.contributors.view.ContributorItem
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.lifecycle.update
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributorsScope
class ContributorsViewModel @Inject constructor(
    private val dataSource: Readable.Suspendable<List<ContributorEntity>>,
    dispatcher: Dispatcher,
    @Development private val logger: ExceptionLogger
) : ViewModel() {

    private val _contributorsObservable = MutableLiveData<List<ContributorItem>>()
    val contributorsObservable: LiveData<List<ContributorItem>> = _contributorsObservable

    private val _viewStateObservable = MutableLiveData(ContributionsViewState())
    val viewStateObservable: LiveData<ContributionsViewState> = _viewStateObservable

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        // TODO: handle error
        logger.log(error)
    }

    init {
        viewModelScope.launch {
            _viewStateObservable.update { copy(isProgressVisible = true) }
            withContext(context = dispatcher.iO + exceptionHandler) {
                dataSource.read()
                    .map(::ContributorItem)
            }.also {
                _viewStateObservable.update { copy(isProgressVisible = false) }
            }.also(_contributorsObservable::setValue)
        }
    }
}