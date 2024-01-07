import data.di.dataModule
import data.di.platformModule
import domain.di.domainModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import presentation.di.presentationModule

fun initKoin(koinApp: ((KoinApplication) -> Unit)? = null) {
    startKoin {
        koinApp?.invoke(this)
        modules(dataModule, domainModule, presentationModule, platformModule)
    }
}