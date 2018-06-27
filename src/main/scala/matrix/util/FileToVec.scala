package matrix.util

import better.files.File
import matrix.exception.{FileNotInitializableException, NotSupportedFileException}
import matrix.types.MatrixTypes.Matrix

import scala.annotation.tailrec
import scala.util.matching.Regex

trait FileToVec[D] {

  type Domain = D

  val toMatrix: Matrix

}

object FileToVec {

  private def withExtension(path: String): (String, String) = {

    val extensionMatcher = new Regex("(.*)(\\.[^\\.]+)$")

    path match {
      case extensionMatcher(woExtension, extension) => (woExtension, extension)
      case _ => (path, "")
    }

  }

  def squeezeVec(path: String): Matrix = withExtension(path) match {
    case (_, extension) if extension == ".csv" => CsvToVec(File(path)).toMatrix
    case (_, extension) => throw NotSupportedFileException("\"" + extension + "\" file is not supported")
    case _ => CsvToVec(File(path)).toMatrix // fallback to csv parser
  }

}


case class CsvToVec(file: File) extends FileToVec[File] {

  @tailrec
  @deprecated private def normalMode(acc: Seq[Char], remainings: Seq[Char]): Seq[Char] =
    remainings match {
      case h +: t if h == '\"' => insideQuoteMode(acc, t)
      case h +: t => normalMode(h +: acc, t)
      case _ => acc.reverse
    }

  @tailrec
  @deprecated private def insideQuoteMode(acc: Seq[Char], remainings: Seq[Char]): Seq[Char] =
    remainings match {
      case h +: t if h == '\"' || h == ',' => normalMode(acc, t)
      case h +: t if h == '\n' => insideQuoteMode(acc, t)
      case h +: t => insideQuoteMode(h +: acc, t)
      case _ => {
        val result = acc.reverse
        // todo: log this
        println("[WARNING] Invalid CSV syntax line at:" + result)
        result
      }
    }

  @deprecated private def toHarmlessCSVLine(rawLine: String): String = normalMode(Seq.empty, (rawLine: Seq[Char])).mkString

  override lazy val toMatrix: Matrix =
    file
      .lines
      .map(
        _
          .split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)")
          .map(_.filter(_ != '\"'))
          .toVector
      )
      .toVector

}

