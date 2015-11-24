package sri.templates.crossplatform.mobile

import chandu0101.macros.tojs.JSMacro
import sri.core.React

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import scala.scalajs.js.{UndefOr => U, undefined}

case class SeekBarAndroid(key: U[String] = undefined,
                              style: U[js.Any] = undefined,
                              progress: U[Int] = undefined,
                              max: U[Int] = undefined,
                              onChange: U[Int => Unit] = undefined) {
    def apply() = {
        val props = JSMacro[SeekBarAndroid](this)
        val seekBarAndroid = g.require("react-native-seekbar-android")
        val f = React.createFactory(seekBarAndroid)
        f(props)
    }
}
