package sri.templates.crossplatform.mobile

import sri.mobile.ReactNative
import sri.mobile.all._
import sri.templates.crossplatform.mobile.router.AppRouter
import sri.templates.crossplatform.universal.components.HelloSri

import scala.scalajs.js.JSApp


object MobileApp extends JSApp {

  def main() = {

    val root = createMobileRoot(
      AlcoMeter()
    )
    ReactNative.AppRegistry.registerComponent("JavaBinDemoAlcometerSri", () => root)
  }
}
