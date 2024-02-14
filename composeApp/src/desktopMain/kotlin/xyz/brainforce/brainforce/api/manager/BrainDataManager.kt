package xyz.brainforce.brainforce.api.manager

import brainflow.BoardShim
import brainflow.BrainFlowPresets
import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.data.DataSegment
import xyz.brainforce.brainforce.api.processor.Processor
import kotlin.math.ceil

@Singleton
class BrainDataManager(
    private val bfiConfigManager: BrainforceConfigManager,
    private val processors: List<Processor>,
) {

    private val sampleSizeCache = mutableMapOf<Int, Int>()

    fun process(boardShim: BoardShim): DataSegment {
        val samples = getSampleSize()
        val data = boardShim.get_current_board_data(samples)
        // Iterate over processors
        return processors
            .filter { it.isSupported(boardShim) }
            .map { it.process(data) }
            .flatten()
    }

    fun reset(): DataSegment = processors
        .map(Processor::reset)
        .flatten()

    private fun getSampleSize(): Int {
        val config = bfiConfigManager.getConfig()
        val fftSize = config.fftSize
        val boardId = config.boardId

        return sampleSizeCache
            .computeIfAbsent(fftSize) { generateSampleSize(it, boardId) }
    }

    private fun generateSampleSize(fftSize: Int, boardId: Int): Int {
        val samplingRate = BoardShim
            .get_sampling_rate(boardId, BrainFlowPresets.ANCILLARY_PRESET)
            .toDouble()
        val windowSeconds = ceil(fftSize / samplingRate)
        val maxSampleSize = (samplingRate * windowSeconds)
            .toInt()

        return maxSampleSize
    }

    private fun List<DataSegment>.flatten(): DataSegment = this
        .flatMap(DataSegment::asPairList)
        .let(::DataSegment)
}