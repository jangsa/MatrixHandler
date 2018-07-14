package matrix.scaffold

import matrix.MatrixHandler
import matrix.types.MatrixTypes.{ColumnContext, ColumnDict, Matrix}

trait OperationHelper {

  val target: String
  implicit val handler: MatrixHandler
  implicit val context: ColumnContext
  implicit val header: Matrix = handler.tableHeaderDict(target)
  val tableBody: Matrix = handler.tableBodyDict(target)

}
