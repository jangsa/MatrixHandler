package matrix.util

object CondInterpolator {

  implicit class CondToPairVec(val conditions: StringContext) extends AnyVal {
    //todo: make a parser of conditional expressions
    def cond(args: Any*): Vector[(String, String)] = ???
  }

}
