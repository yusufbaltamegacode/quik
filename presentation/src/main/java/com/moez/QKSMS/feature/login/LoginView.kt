package dev.octoshrimpy.quik.feature.login

import dev.octoshrimpy.quik.common.base.QkView
import io.reactivex.Observable

interface LoginView: QkView<LoginState> {

    val goToMainScreen: Observable<Boolean>
    fun loginRequest ()


}