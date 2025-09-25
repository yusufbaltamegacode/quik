package dev.octoshrimpy.quik.feature.login

import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import dev.octoshrimpy.quik.R
import dev.octoshrimpy.quik.common.Navigator
import dev.octoshrimpy.quik.common.base.QkViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor (
    private val navigator: Navigator,
) : QkViewModel<LoginView, LoginState>(
    LoginState(
        hasError = false
    )
) {

    override fun bindView(view: LoginView) {
        super.bindView(view)

        view.goToMainScreen.subscribe{ value ->

            if(value==true){
                    navigator.showMainActivity();

            }

        }

    }


}