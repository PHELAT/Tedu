package com.phelat.tedu.contributors.di.module

import androidx.lifecycle.ViewModel
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.viewmodel.ContributorsViewModel
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.lifecycle.ViewModelKey
import com.phelat.tedu.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ContributorsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContributorsViewModel::class)
    @ContributorsScope
    fun bindTodoListViewModel(input: ContributorsViewModel): ViewModel

    companion object {
        @Provides
        @ContributorsScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }
    }
}