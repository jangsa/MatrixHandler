package matrix.util

import better.files.File
import matrix.exception.{FileNotInitializableException, NotSupportedFileException}
import matrix.types.MatrixTypes.Matrix

import scala.annotation.tailrec
import scala.util.matching.Regex

object FileToMatrix {

  private def withExtension(path: String): (String, String) = {

    val extensionMatcher = new Regex("(.*)(\\.[^\\.]+)$")

    path match {
      case extensionMatcher(woExtension, extension) => (woExtension, extension)
      case _ => (path, "")
    }

  }

  def squeezeVec(path: String): Matrix = withExtension(path) match {
    case (_, extension) if extension == ".csv" => csvToMatrix(File(path))
    case (_, extension) => throw NotSupportedFileException("\"" + extension + "\" file is not supported")
    case _ => csvToMatrix(File(path)) // fallback to csv parser
  }

  def csvToMatrix(source: File): Matrix =
    source
      .lines
      .map(
        _
          .split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)")
          .map(_.filter(_ != '\"'))
          .toVector
      )
      .toVector

}

