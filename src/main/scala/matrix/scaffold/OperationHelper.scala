package matrix.scaffold

import matrix.MatrixHandler
import matrix.types.MatrixTypes.{ColumnPositionDict, Matrix}

trait OperationHelper {

  val target: String
  implicit val handler: MatrixHandler
  implicit val columnDictionary: ColumnPositionDict = handler.columnDictionary
  implicit val header: Matrix = handler.headerDict(target)
  val tableBody: Matrix = handler.tableBodyDict(target)

}
