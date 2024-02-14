package xyz.brainforce.brainforce.api.data.file

import brainflow.BoardIds
import brainflow.BoardShim
import brainflow.BrainFlowInputParams
import kotlinx.serialization.Serializable
import xyz.brainforce.brainforce.api.data.IpProtocol
import kotlin.math.min

@Serializable
data class BrainforceConfig(
    var timeout: Int = 5,
    var ipPort: Int = 0,
    var protocol: IpProtocol = IpProtocol.UDP,
    var ipAddress: String = "",
    var serialPort: String = "",
    var macAddress: String = "",
    var otherInfo: String = "",
    var streamerParams: String = "",
    var serialNumber: String = "",
    var file: String = "",
    var boardId: Int = BoardIds.MUSE_2_BOARD._code,
    var windowSeconds: Int = 2,
    var refreshRate: Int = 60,
    var emaDecay: Float = 1f,
    var debug: Boolean = false,
    var oscPort: Int = 9000,
    val fftSize: Int = 2048,
) : FileLayout {

    fun asBrainflowParams(): BrainFlowInputParams = BrainFlowInputParams().also {
        it.mac_address = macAddress
        it.serial_port = serialPort
        it.serial_number = serialNumber
        it.ip_address = ipAddress
        it.ip_protocol = protocol.value
        it.ip_port = ipPort
        it.timeout = min(timeout, 3)
        it.file = file
        it.other_info = otherInfo
        it.master_board = boardId
    }

    fun asBoardShim(): BoardShim = BoardShim(
        boardId,
        asBrainflowParams()
    )
}
