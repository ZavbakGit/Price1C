package `fun`.gladkikh.app.price1c.viewmodel

import androidx.lifecycle.ViewModel
import com.uber.autodispose.ScopeProvider
import io.reactivex.CompletableSource
import io.reactivex.subjects.CompletableSubject

/**
 * This class for ScopeProvider and for .`as`(AutoDispose.autoDisposable(this))
 */
abstract class BaseViewModel : ViewModel(), ScopeProvider {
    protected val onClearedSubject = CompletableSubject.create()

    override fun requestScope(): CompletableSource = onClearedSubject

    override fun onCleared() {
        super.onCleared()
        onClearedSubject.onComplete()
    }

}