package xyz.brainforce.brainforce.api.data.osc

sealed interface OSCValue<T> {

    val value: T
}