package matrix

import matrix.types.MatrixTypes.{ColumnPositionDict, Matrix}
import matrix.util.FileToVec

case class MatrixHandler
(
  val filePath: String,
  val headMost: Int,
  val rightMost: Int,
  val leftMost: Int,
  val footerSize: Int,
  val headerRowNum: Int,
  implicit val columnDictionary: ColumnPositionDict
) {

  lazy val entire: Matrix = FileToVec.squeezeVec(filePath)

  lazy val entireRowSize: Int = entire.size

  lazy val footMost = entireRowSize - footerSize

  lazy val header: Matrix = entire.take(headMost - 1)

  lazy val footer: Matrix = entire.drop(footMost)

  lazy val body: Matrix = entire.take(footMost).drop(headMost - 1)

  lazy val table: Matrix =
    for {
      row <- body
    } yield
      if (rightMost > 0)
        row.take(rightMost).drop(leftMost)
      else
        row.drop(leftMost)

  lazy val tableHeader: Matrix = table.take(headerRowNum)

  lazy val tableBody: Matrix = table.drop(headerRowNum)

}
