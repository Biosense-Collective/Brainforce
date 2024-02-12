package xyz.brainforce.brainforce.framework.lifecycle

sealed class AppException(message: String) : Exception(message) {

    class FailedToFindApp : AppException("Main app definition could not be found! Make sure it is marked as @Singleton")
}