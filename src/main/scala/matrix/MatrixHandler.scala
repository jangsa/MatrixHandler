package matrix

import matrix.types.MatrixTypes.{Column, ColumnDict, Matrix}
import matrix.util.FileToMatrix
import matrix.util.NameToValueInterpolator._
import matrix.util.StringTo._

case class MatrixHandler
(
  val filePaths: Map[String, String],
  val headMost: Int,
  val rightMost: Int,
  val leftMost: Int,
  val footerSize: Int,
  val headerRowNum: Int,
  implicit val columnDictionary: ColumnDict
) {

  lazy val wholeDict: Map[String, Matrix] = filePaths.mapValues(FileToMatrix.squeezeVec(_))

  lazy val entireRowSize: Map[String, Int] = wholeDict.mapValues(_.size)

  lazy val footMost = entireRowSize.mapValues(_ - footerSize)

  lazy val headerDict: Map[String, Matrix] = wholeDict.mapValues(_.take(headMost - 1))

  lazy val footerDict: Map[String, Matrix] =
    wholeDict.map {
      case _@(key, m) => (key, m.drop(footMost(key)))
      case p => p
    }

  lazy val bodyDict: Map[String, Matrix] =
    wholeDict.map {
      case _@(key, m) => (key, m.take(footMost(key)).drop(headMost - 1))
      case p => p
    }

  lazy val tableDict: Map[String, Matrix] =
    bodyDict
      .mapValues(m =>
        m.map(row =>
          if (rightMost > 0)
            row.take(rightMost).drop(leftMost)
          else
            row.drop(leftMost)
        ))

  lazy val tableHeaderDict: Map[String, Matrix] = tableDict.mapValues(_.take(headerRowNum))

  lazy val tableBodyDict: Map[String, Matrix] = tableDict.mapValues(_.drop(headerRowNum))

}

object MatrixHandler {

  implicit class Syntax(matrix: Matrix) {

    def _where(cmp: (String, String) => Boolean)
              (colValPair: Vector[(Column, String)])
              (implicit header: Matrix, dictionary: ColumnDict): Matrix =
      matrix
        .filter(implicit row =>
          colValPair
            .foldLeft(true)((acc, p) => {
              acc && cmp(ToValue(StringContext(p._1)).colval(), p._2)
            })
        )

    def where(colValPair: Vector[(Column, String)])
             (implicit header: Matrix, dictionary: ColumnDict): Matrix = _where((a, b) => a == b)(colValPair)

    def whereNot(colValPair: Vector[(Column, String)])
                (implicit header: Matrix, dictionary: ColumnDict): Matrix = _where((a, b) => a != b)(colValPair)

    def sumIntAt(column: Column)
                (implicit header: Matrix, dictionary: ColumnDict): Int = {
      matrix
        .foldLeft(0)((acc, row) => {
          implicit val _row = row

          acc + ToValue(StringContext(column)).colval().toIntWithDefault()
        })
    }

    def sumDoubleAt(column: Column)
                   (implicit header: Matrix, dictionary: ColumnDict): Double = {
      matrix
        .foldLeft(0d)((acc, row) => {
          implicit val _row = row

          acc + ToValue(StringContext(column)).colval().toDoubleWithDefault()
        })
    }

  }

}
