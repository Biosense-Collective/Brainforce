import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import xyz.brainforce.brainforce.api.service.runMain
import xyz.brainforce.brainforce.ui.App

fun main() = runMain()

//        = application {
//    Window(onCloseRequest = ::exitApplication, title = "Brainforce") {
//        App()
//    }
//}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}