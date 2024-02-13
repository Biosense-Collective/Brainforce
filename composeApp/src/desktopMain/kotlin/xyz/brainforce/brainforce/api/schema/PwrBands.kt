package xyz.brainforce.brainforce.api.schema

import xyz.brainforce.brainforce.api.data.ParameterType

sealed class PwrBands(endpoint: String, type: ParameterType) : BfiEndpoint("PwrBands/$endpoint", type) {

    sealed class Left(endpoint: String, type: ParameterType) : PwrBands("Left/$endpoint", type) {

        data object Gamma : Left("Gamma", ParameterType.FLOAT_POS)

        data object Beta : Left("Beta", ParameterType.FLOAT_POS)

        data object Alpha : Left("Alpha", ParameterType.FLOAT_POS)

        data object Theta : Left("Theta", ParameterType.FLOAT_POS)

        data object Delta : Left("Delta", ParameterType.FLOAT_POS)
    }

    sealed class Right(endpoint: String, type: ParameterType) : PwrBands("Left/$endpoint", type) {

        data object Gamma : Right("Gamma", ParameterType.FLOAT_POS)

        data object Beta : Right("Beta", ParameterType.FLOAT_POS)

        data object Alpha : Right("Alpha", ParameterType.FLOAT_POS)

        data object Theta : Right("Theta", ParameterType.FLOAT_POS)

        data object Delta : Right("Delta", ParameterType.FLOAT_POS)
    }

    sealed class Avg(endpoint: String, type: ParameterType) : PwrBands("Left/$endpoint", type) {

        data object Gamma : Avg("Gamma", ParameterType.FLOAT_POS)

        data object Beta : Avg("Beta", ParameterType.FLOAT_POS)

        data object Alpha : Avg("Alpha", ParameterType.FLOAT_POS)

        data object Theta : Avg("Theta", ParameterType.FLOAT_POS)

        data object Delta : Avg("Delta", ParameterType.FLOAT_POS)
    }
}