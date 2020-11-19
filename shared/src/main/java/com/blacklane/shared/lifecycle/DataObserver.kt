package com.blacklane.shared.lifecycle

import com.blacklane.shared.utils.strings.StringProvider

class DataObserver<Result> {

    private var observer: (ResourceObserver<Result>)? = null

    var value: ViewState<Result>? = null
        set(value) {
            field = value
            @Suppress("UNCHECKED_CAST")
            observer?.onChanged(value as ViewState<Result>)
        }

    fun observe(observer: ResourceObserver<Result>) {
        this.observer = observer
    }

    class ResourceObserver<ResultType>(
        val loadingState: ((Boolean) -> Unit)? = null,
        val error: ((StringProvider) -> Unit)? = null,
        val data: ((ResultType) -> Unit)?
    ) {
        fun onChanged(currentValue: ViewState<ResultType>?) {
            when (currentValue) {
                is ViewState.Loading -> {
                    loadingState?.invoke(true)
                }
                is ViewState.Error -> {
                    loadingState?.invoke(false)
                    error?.invoke(currentValue.message)
                }
                is ViewState.Success<ResultType> -> {
                    loadingState?.invoke(false)
                    data?.invoke(currentValue.data)
                }
            }
        }
    }
}
