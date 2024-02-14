package xyz.brainforce.brainforce.api.processor

import brainflow.BoardShim
import xyz.brainforce.brainforce.api.data.DataSegment

interface Processor {

    fun isSupported(board: BoardShim): Boolean = true

    fun process(data: Array<DoubleArray>): DataSegment

    fun reset(): DataSegment = DataSegment()
}