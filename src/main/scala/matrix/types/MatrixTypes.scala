package matrix.types

object MatrixTypes {
  type Row = Vector[String]
  type Matrix = Vector[Row]
  type Column = String
  type ColumnDict = Map[Column, Int]

  trait ColumnContext {
    val dict: ColumnDict
  }
}

