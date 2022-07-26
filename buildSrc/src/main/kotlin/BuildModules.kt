/**
 * Configuration of build modules
 */
object BuildModules {
    const val APP = ":app"

    object Provide {

        const val APIS = ":provide:apis"
        const val REPOSITORIES = ":provide:repositories"

        object Mocks {
            const val APIS = ":provide:mocks:apis"
        }

        const val DATASOURCES = ":provide:datasources"
        const val SYSTEMS = ":provide:systems"
    }

    object Features {
        const val GALLERY = ":features:gallery"
    }

    object Core {
        const val APIS = ":core:apis"
        const val DATASOURCES = ":core:datasources"
        const val ENTITIES = ":core:entities"
        const val REPOSITORIES = ":core:repositories"
        const val SYSTEMS = ":core:systems"
        const val USECASES = ":core:usecases"
    }

    object Common {
        const val RESOURCES = ":common:resources"
        const val DEPENDENCY_INJECTION = ":common:di"

    }
}
