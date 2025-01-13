package example.project.splash.presentation


data class SplashUiState(
    val isLoading: Boolean? = false,
    val appVersionName: String? = "",
)
sealed class  SplashUiEvent{
    data class Error(val message: String): SplashUiEvent()
    object NavigateToMain: SplashUiEvent()
}