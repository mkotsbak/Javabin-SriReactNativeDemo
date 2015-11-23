package sri.templates.crossplatform.universal.components

import sri.core._
import sri.universal.all._
import sri.universal.components._
import sri.universal.styles.UniversalStyleSheet

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.{UndefOr => U}

object AlcoMeter {

  final val beerURI = ImageSource("http://www.clker.com/cliparts/6/d/0/c/13216380871142975465beer-batter-is-better_1-md.png")

  def beerImage(i: Int) = {
    Image(style = styles.beerImage, source = beerURI, key = i.toString)()

  }
  def beerImages(numberOfBeers: Int) = {
    View(style = styles.beers)(
      (0 to numberOfBeers - 1) map beerImage
    )
  }

  @ScalaJSDefined
  class Component extends ReactComponent[Unit, Unit] {
    def render() = {

      val platform = if (isIOSPlatform) "iOS"
      else if (isAndroidPlatform) "Android"
      else "Web"

      View(style = styles.container)(
        Text(style = styles.title)(s"JavaBin alkometer med Sri på $platform "),
        SeekBarAndroid(style = styles.slider, progress = 2, max = 10, onChange = { value: Int =>
          println(value)
        })(),
        beerImages(3),
        Text(style = styles.centeredText)("Scala.js - Future of app development!")
      )
    }
  }

  object styles extends UniversalStyleSheet {
    val container = style(
      flexOne,
      justifyContent.center,
      backgroundColor := "white"
    )

    val slider = style(
      marginLeft := 10,
      marginRight := 10
    )

    val title = style(
      marginTop := 30,
      fontSize := 30,
      color := "blue"
    )

    val centeredText = style(
      alignSelf.center
    )

    val beers = style(
      marginTop := 30,
      flexOne,
      justifyContent.center,
      width := 350,
      flexDirection.row,
      flexWrap.wrap
    )

    val beerImage = style(
      width := 80,
      height := 100)

    val alco = style(
      fontSize := 30,
      fontWeight.bold
    )
  }

  val ctor = getTypedConstructor(js.constructorOf[Component], classOf[Component])

  def apply() = createElementNoProps(ctor)
}
