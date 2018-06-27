package matrix.types

object MatrixTypes {
  type Row = Vector[String]
  type Matrix = Vector[Row]
  type Column = String
  type ColumnPositionDict = Map[Column, Int]
}

