package sri.templates.crossplatform.web

import org.scalajs.dom
import sri.templates.crossplatform.mobile.AlcoMeter
import sri.templates.crossplatform.universal.components.HelloSri
import sri.templates.crossplatform.web.router.AppRouter
import sri.web.ReactDOM

import scala.scalajs.js.Dynamic.{global => g, literal => json}
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object WebApp extends JSApp {


  @JSExport
  override def main(): Unit = {

    ReactDOM.render(AlcoMeter(), dom.document.getElementById("app"))
  }
}
