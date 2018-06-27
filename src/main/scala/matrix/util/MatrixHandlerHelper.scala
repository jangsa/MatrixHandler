package matrix.util

import matrix.types.MatrixTypes._
import matrix.util.NameToValueInterpolator._
import StringTo._

object MatrixHandlerHelper {

  implicit class Syntax(matrix: Matrix) {

    def _where(cmp: (Any, Any) => Boolean)
              (colValPair: Vector[(Column, String)])
              (implicit header: Matrix, dictionary: ColumnPositionDict): Matrix =
      matrix
        .filter(implicit row =>
          colValPair
            .foldLeft(true)((acc, p) => {
              acc && cmp(ToValue(StringContext(p._1)).col(), p._2)
            })
        )

    def where(colValPair: Vector[(Column, String)])
             (implicit header: Matrix, dictionary: ColumnPositionDict): Matrix = _where((a, b) => a == b)(colValPair)

    def whereNot(colValPair: Vector[(Column, String)])
                (implicit header: Matrix, dictionary: ColumnPositionDict): Matrix = _where((a, b) => a != b)(colValPair)

    def sumAt(column: Column)
             (implicit header: Matrix, dictionary: ColumnPositionDict): Int = {
      matrix
        .foldLeft(0)((acc, row) => {
          implicit val _row = row

          acc + ToValue(StringContext(column)).col().toIntWithDefault
        })
    }


    def sumdAt(column: Column)
              (implicit header: Matrix, dictionary: ColumnPositionDict): Double = {
      matrix
        .foldLeft(0d)((acc, row) => {
          implicit val _row = row

          acc + ToValue(StringContext(column)).col().toDoubleWithDefault
        })
    }

  }

}
