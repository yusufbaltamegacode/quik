package dev.octoshrimpy.quik.feature.login
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.moez.QKSMS.common.util.MaskedPhoneNumberTextWatcher
import com.moez.QKSMS.common.util.SmsCodeTextWatcher
import com.moez.QKSMS.model.DeviceInfo
import dagger.android.AndroidInjection
import dev.octoshrimpy.quik.R
import dev.octoshrimpy.quik.common.Navigator
import dev.octoshrimpy.quik.common.base.QkThemedActivity
import dev.octoshrimpy.quik.common.util.extensions.dismissKeyboard
import dev.octoshrimpy.quik.common.widget.QkEditText
import dev.octoshrimpy.quik.interactor.PingService
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_login.loginButton
import kotlinx.android.synthetic.main.activity_login.main
import kotlinx.android.synthetic.main.activity_login.phoneInput
import kotlinx.android.synthetic.main.activity_login.smsCodeInput
import kotlinx.android.synthetic.main.gallery_activity.root

import javax.inject.Inject

class LoginActivity : QkThemedActivity(), LoginView {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

  lateinit  var maskedPhoneWatcher: MaskedPhoneNumberTextWatcher

  lateinit  var smsCodeWatcher : SmsCodeTextWatcher

 lateinit var inputs : List<QkEditText>

    override fun onCreate(savedInstanceState: Bundle?) {



        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        main.setOnTouchListener { v, _ ->
            dismissKeyboard()
            true
        }
        viewModel.bindView(this);

        loginButton.setOnClickListener{
            loginRequest();
        }

        maskedPhoneWatcher = MaskedPhoneNumberTextWatcher(phoneInput)
        smsCodeWatcher = SmsCodeTextWatcher(smsCodeInput)

        phoneInput.addTextChangedListener(maskedPhoneWatcher)
        smsCodeInput.addTextChangedListener(smsCodeWatcher)

        inputs  = listOf(phoneInput,smsCodeInput);

        DeviceInfo.init(this);
        // Foreground Service başlat
        val intent = Intent(this, PingService::class.java)
        ContextCompat.startForegroundService(this, intent)

    }



    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
    }
    override val goToMainScreen: Subject<Boolean> = PublishSubject.create()


    override fun loginRequest() {
        var isValid = true
        var firstErrorInput: QkEditText? = null

        for (input in inputs) {
            if (input.error != null) {
                isValid = false
                input.error = input.error // hata mesajını göster
                if (firstErrorInput == null) {
                    firstErrorInput = input
                }
            }
        }

        if(!isValid){

            firstErrorInput?.requestFocus()
            return;
        }




        println("message login requesti yapıldı")
        goToMainScreen.onNext(true);

    }

    override fun render(state: LoginState) {
            state.hasError=false
    }


}