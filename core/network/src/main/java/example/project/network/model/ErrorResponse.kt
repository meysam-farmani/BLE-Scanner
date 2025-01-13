package example.project.network.model
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val statusCode: Int,
    val statusPhrase: String,
    val errors: List<Error>,
    val timestamp: String
)
@Serializable
data class Error(
    val code: String,
    val message: String
)