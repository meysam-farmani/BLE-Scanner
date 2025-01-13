object Flavors {
    const val OriginDimension = "origin"

    const val ProdOrigin = "prod"

    val flavorDimensions = listOf(OriginDimension)

    private val prodBaseUrl = "https://run.mocky.io/"

    val flavors = listOf<FlavorConfig>(
        FlavorBuilder(
            name = ProdOrigin,
            dimension = OriginDimension,
        ).setBaseUrl(prodBaseUrl)
            .build(),
    )

    data class FlavorConfig(
        val name: String,
        val dimension: String,
        val applicationIdSuffix: String?,
        val versionNameSuffix: String?,
        val buildConfigs: List<Variable>
    ) {
        data class Variable(val type: String, val name: String, val value: String)
    }

    class FlavorBuilder(val name: String, val dimension: String) {

        private var baseUrl: String? = null
        private var suffixName: String? = null

        fun setBaseUrl(url: String) = this.apply { baseUrl = url }

        fun build() = FlavorConfig(
            name = name,
            dimension = dimension,
            applicationIdSuffix = null,
            versionNameSuffix = if (suffixName.isNullOrEmpty().not()) " - ${suffixName}" else null,
            buildConfigs = arrayListOf<FlavorConfig.Variable>().apply {
                if (baseUrl != null) {
                    add(FlavorConfig.Variable("String", "BASE_URL", "\"$baseUrl\""))
                }

                add(FlavorConfig.Variable("boolean", "LOGGING", "${Application.LOGGING}"))

                add(FlavorConfig.Variable("String", "LIBRARY_VERSION_NAME", "\"${Application.versionName}\""))
            }
        )
    }
}