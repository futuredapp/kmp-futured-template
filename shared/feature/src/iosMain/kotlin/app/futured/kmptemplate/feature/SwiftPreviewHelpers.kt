package app.futured.kmptemplate.feature

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

fun <T: Any> mockEmptyFlow(): Flow<T> = emptyFlow()
