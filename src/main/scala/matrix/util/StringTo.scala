package matrix.util

import scala.util.{Success, Try}

object StringTo {

  implicit class Converter(str: String) {

    def toDoubleWithDefault: Double = Try(str.toDouble) match {
      case Success(v) => v
      case _ => 0d
    }

    def toIntWithDefault: Int = Try(str.toInt) match {
      case Success(v) => v
      case _ => 0
    }

  }

}

