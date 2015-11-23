package sri.templates.crossplatform.mobile

import sri.core._
import sri.mobile.components.ios.SliderIOS
import sri.universal.all._
import sri.universal.components._
import sri.universal.styles.UniversalStyleSheet

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.{UndefOr => U}

object AlcoCalculation {
  def alcoholLevel(numberOfBeers: Int) = {
    numberOfBeers * 15 / ((80 * 1.7) - (0.15 * 4))
  }
}

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

  def slider(isIos: Boolean, start: Int, max: Int, onChange: Int => Unit) = {
    isIos match {
      case true => SliderIOS(style = styles.slider, minimumValue = 0.0, maximumValue = max.toDouble, value = start.toDouble,
        onValueChange = onChange.compose((v: Double) => v.toInt))()
      case _ => SeekBarAndroid(style = styles.slider, progress = start, max = max, onChange = onChange)()
    }
  }

  case class State(numberOfBeers: Int = 0)

  @ScalaJSDefined
  class Component extends ReactComponent[Unit, State] {

    initialState(State())

    def render() = {
      val platform = if (isIOSPlatform) "iOS"
      else if (isAndroidPlatform) "Android"
      else "Web"

      View(style = styles.container)(
        Text(style = styles.title)(s"JavaBin alkometer med Sri på $platform "),

        Text(style = styles.centeredText)(s"${state.numberOfBeers} enheter"),

        slider(isIOSPlatform, 0, 16, { value: Int =>
          println(value)
          setState(state.copy(numberOfBeers = value))
        }),

        Text(style = styles.centeredText)("Din promille:"),
        Text(style = styles.alco(state.numberOfBeers))(
          AlcoCalculation.alcoholLevel(state.numberOfBeers).formatted("%.2f") + " %"
        ),

        beerImages(state.numberOfBeers),

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

    private def colorPrefix(numberOfBeers: Int) = numberOfBeers * 255 / 15

    def alco(numberOfBeers: Int) = style(
      fontSize := 30,
      fontWeight.bold,
      alignSelf.center,
      color := s"rgb(${colorPrefix(numberOfBeers)}, 0, 0)"
    )
  }

  val ctor = getTypedConstructor(js.constructorOf[Component], classOf[Component])

  def apply() = createElementNoProps(ctor)
}
