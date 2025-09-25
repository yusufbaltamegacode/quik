package com.moez.QKSMS.common.util



import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MaskedPhoneNumberTextWatcher(
    private val editText: EditText
) : TextWatcher {

    private var isFormatting = false

    init {
        afterTextChanged(editText.text)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) return
        isFormatting = true

        // sadece rakamları al
        val digits = s.toString().replace("[^\\d]".toRegex(), "")
        val builder = StringBuilder()
        val length = digits.length

        if (length > 0) {
            builder.append("(")
            builder.append(digits.substring(0, minOf(3, length)))
        }
        if (length >= 4) {
            builder.append(")")
            builder.append(digits.substring(3, minOf(6, length)))
        }
        if (length >= 7) {
            builder.append("-")
            builder.append(digits.substring(6, minOf(10, length)))
        }

        // maskelenmiş string
        val newText = builder.toString()

        if (editText.text.toString() != newText) {
            editText.setText(newText)
            editText.setSelection(newText.length)
        }

        // ✅ Validasyon
        validate(digits)

        isFormatting = false
    }

    private fun validate(digits: String) {
        when {
            digits.length < 10 -> {
                editText.error = "Telefon numarası 10 haneli olmalı"
            }
            else -> {
                editText.error = null
            }
        }
    }
}


class SmsCodeTextWatcher(
    private val editText: EditText,
    private val maxLength: Int = 6
) : TextWatcher {

    private var isEditing = false

    init {
    afterTextChanged(editText.text)
    }




    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isEditing) return
        isEditing = true

        var text = s.toString()

        // maxLength kontrolü
        if (text.length > maxLength) {
            text = text.take(maxLength)
            editText.setText(text)
            editText.setSelection(text.length)
        }

        // sadece rakam kontrolü ve anlık validasyon

        when {
            text.isEmpty() -> editText.error = "Boş Bırakılamaz"
            text.any { !it.isDigit() } -> editText.error = "Sadece rakam girebilirsiniz"
            text.length < maxLength -> editText.error = "Kod $maxLength haneli olmalı"
            else -> editText.error = null
        }

        isEditing = false
    }
}