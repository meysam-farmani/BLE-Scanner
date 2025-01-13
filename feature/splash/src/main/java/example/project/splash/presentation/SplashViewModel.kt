package example.project.splash.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import example.project.domain.use_case.GetAppVersionNameUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAppVersionNameUseCase: GetAppVersionNameUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    private val _event = Channel<SplashUiEvent>()
    val event: Flow<SplashUiEvent> = _event.receiveAsFlow()

    init {
        getAppVersionName()
        showSplash()
    }

    private fun navigateToMain() {
        _event.trySend(SplashUiEvent.NavigateToMain)
    }

    private fun getAppVersionName() {
        viewModelScope.launch {
            getAppVersionNameUseCase().collect { version ->
                _uiState.update { it.copy(appVersionName = version) }
            }
        }
    }

    private fun showSplash() {
        viewModelScope.launch {
            delay(2000)

            navigateToMain()
        }
    }
}