package matrix.marshalling

import matrix.MatrixHandler
import matrix.exception.JsonDecodeFailedException

trait Decoder[A] {

  val path: String
  lazy val configString: String = better.files.File(path).contentAsString
  val decoded: A

}

case class MatrixHandlerJsonDecoder(override val path: String) extends Decoder[Map[String, MatrixHandler]] {

  import io.circe.parser._
  import io.circe.generic.auto._

  override val decoded: Map[String, MatrixHandler] =
    decode[Map[String, MatrixHandler]](configString) match {

      case Right(config) => config

      case Left(e) => throw JsonDecodeFailedException(e.getMessage())

    }

}
