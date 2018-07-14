package matrix.util

import scala.util.{Success, Try}

object StringTo {

  implicit class Converter(str: String) {

    def toDoubleWithDefault(d: Double = 0d): Double = Try(str.toDouble) match {
      case Success(v) => v
      case _ => d
    }

    def toIntWithDefault(d: Int = 0): Int = Try(str.toInt) match {
      case Success(v) => v
      case _ => d
    }

  }

}

