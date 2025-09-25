package dev.octoshrimpy.quik.feature.login

data class LoginState(
    var hasError: Boolean = false,

    var pushMainScreen: Boolean = false,
)
