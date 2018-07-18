package matrix.scaffold

import matrix.MatrixHandler
import matrix.types.MatrixTypes.{ColumnContext, ColumnDict, Matrix}

trait OperationHelper {

  val target: String
  implicit val handler: MatrixHandler
  implicit val context: ColumnContext
  implicit lazy val dict: ColumnDict = context.dict
  implicit lazy val header: Matrix = handler.tableHeaderDict(target)
  lazy val tableBody: Matrix = handler.tableBodyDict(target)

}
