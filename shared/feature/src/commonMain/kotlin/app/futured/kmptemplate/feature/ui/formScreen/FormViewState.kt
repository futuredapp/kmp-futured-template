package app.futured.kmptemplate.feature.ui.formScreen

import app.futured.kmptemplate.feature.data.model.AlertDialogUi

data class FormViewState(
    val firstName: String = "",
    val surname: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val alertDialogUi: AlertDialogUi? = null
)
