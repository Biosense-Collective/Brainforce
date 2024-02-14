package xyz.brainforce.brainforce.api.data

import xyz.brainforce.brainforce.api.data.osc.OSCValue
import xyz.brainforce.brainforce.api.schema.BfiEndpoint

class DataSegment(vararg data: Pair<BfiEndpoint, OSCValue<*>>) : Map<BfiEndpoint, OSCValue<*>> by mapOf(*data)