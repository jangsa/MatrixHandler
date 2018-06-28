package matrix.util

import matrix.exception.InvalidColumnNameException
import matrix.types.MatrixTypes._

import scala.annotation.tailrec
import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}

object NameToValueInterpolator {

  @tailrec
  final def placeOf
  (regex: Regex)
  (currentIndex: Int,
   remainings: Row,
   founds: Vector[Int]): Vector[Int] = remainings match {

    case h +: t => {
      val newFounds =
        if (regex.findFirstIn(h).isDefined) currentIndex +: founds
        else founds

      placeOf(regex)(currentIndex + 1, t, newFounds)
    }

    case _ => founds.reverse

  }

  def toColumnIndex(searchWord: String)(implicit header: Matrix): Int = {

    val matcher = new Regex("(.+)@([0-9]+)$")

    val (columnName, index) = searchWord match {
      case matcher(name, indexString) => {
        val _index = Try(indexString.toInt) match {
          case Success(v) if v > 0 => v - 1
          case _ => 0
        }

        (name, _index)
      }

      case _ => (searchWord, 0)
    }

    val founds =
      header
        .flatMap(row => placeOf(new Regex("^" + columnName + "$"))(0, row, Vector.empty))

    Try(founds(index)) match {
      case Success(v) => v
      case _ => throw InvalidColumnNameException("No such column:" + searchWord)
    }

  }

  implicit class ToValue(val colName: StringContext) extends AnyVal {

    def col(args: Any*)(implicit row: Row, dictionary: ColumnPositionDict, header: Matrix): String = {

      val colIndex: Int = Try(colName.parts.iterator.next()) match {
        case Success(v) => dictionary.withDefault(toColumnIndex)(v)
        case Failure(_) => throw InvalidColumnNameException("Give column name \"" + colName.parts.mkString + "\" is invalid")
      }

      Try(row(colIndex)) match {
        case Success(v) => v
        case _ => ""
      }

    }

  }

}
