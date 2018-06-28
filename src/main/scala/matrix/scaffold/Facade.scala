package matrix.scaffold

import matrix.types.MatrixTypes.{ColumnPositionDict, Matrix}

trait Facade {

  implicit val columnDictionary: ColumnPositionDict
  implicit val header: Matrix

}
