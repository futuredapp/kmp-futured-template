package app.futured.kmptemplate.feature.domain

import app.futured.kmptemplate.persistence.persistence.ApplicationSettingsPersistence
import app.futured.kmptemplate.util.domain.UseCase
import org.koin.core.annotation.Factory

@Factory
internal class SaveDummyPersistenceValueUseCase(
    private val applicationSettingsPersistence: ApplicationSettingsPersistence
) : UseCase<Boolean, Unit>() {

    override suspend fun build(args: Boolean) {
        applicationSettingsPersistence.setDummyValue(args)
    }
}
