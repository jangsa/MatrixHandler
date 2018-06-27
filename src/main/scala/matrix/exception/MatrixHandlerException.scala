package matrix.exception

sealed abstract class MatrixHandlerException(val message: String) extends Exception(message)
case class InvalidColumnNameException(override val message: String) extends MatrixHandlerException(message)
case class JsonDecodeFailedException(override val message: String) extends MatrixHandlerException(message)
case class NotSupportedFileException(override val message: String) extends MatrixHandlerException(message)
case class CsvSyntaxException(override val message: String) extends MatrixHandlerException(message)
case class FileNotInitializableException(override val message: String) extends MatrixHandlerException(message)

