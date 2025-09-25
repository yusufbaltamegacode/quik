package dev.octoshrimpy.quik.feature.login

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dev.octoshrimpy.quik.feature.main.MainViewModel
import dev.octoshrimpy.quik.injection.ViewModelKey
import dev.octoshrimpy.quik.injection.scope.ActivityScope
import io.reactivex.disposables.CompositeDisposable


@Module
class LoginActivityModule {

    @Provides
    @ActivityScope
    fun provideCompositeDiposableLifecycle(): CompositeDisposable = CompositeDisposable()

    @Provides
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun provideMainViewModel(viewModel: LoginViewModel): ViewModel = viewModel

}