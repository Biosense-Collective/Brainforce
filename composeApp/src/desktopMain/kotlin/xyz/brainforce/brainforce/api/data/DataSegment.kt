package xyz.brainforce.brainforce.api.data

import xyz.brainforce.brainforce.api.data.osc.OSCValue
import xyz.brainforce.brainforce.api.schema.BfiEndpoint

class DataSegment(vararg data: Pair<BfiEndpoint, OSCValue<*>>) : Map<BfiEndpoint, OSCValue<*>> by mapOf(*data) {

    constructor(items: List<Pair<BfiEndpoint, OSCValue<*>>>) : this(*items.toTypedArray())

    fun asPairList(): List<Pair<BfiEndpoint, OSCValue<*>>> = entries
        .map { it.key to it.value }
}
