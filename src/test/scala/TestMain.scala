import matrix.marshalling.MatrixHandlerJsonDecoder
import matrix.MatrixHandler._
import org.scalatest.FlatSpec

class TestMain extends FlatSpec {

  import matrix.util.NameToValueInterpolator._

  val handlerDictionary = MatrixHandlerJsonDecoder("./resources/matrixdef.json")

  val handler = handlerDictionary.decoded("test")


  "entireRowSize" should "be correct" in {
    assert(handler.entireRowSize("test") == 9)
  }

  {
    import handler._
    import matrix.util.StringTo._

    implicit val tableHeader = handler.tableHeaderDict("test")

    "col\"ichidayo\" at 2nd line" should "be b" in {

      tableBodyDict("test")
        .zipWithIndex
        .foreach(p => {
          implicit val row = p._1
          if (p._2 == 0) {
            assert(colval"ichidayo" == "b")
          }
        })

    }

    "col\"column5\" at 3rd line" should "be 5 which is String and can be evaluated as Int/Double" in {

      tableBodyDict("test")
        .zipWithIndex
        .foreach {
          case (row, i) if i == 1 => {
            implicit val _row = row
            assert(colval"column5".toIntWithDefault() == 5)
            assert(colval"column5".toDoubleWithDefault() == 5d)
          }

          case _ => /* nothing to do */
        }

    }

    "col\"column5\" (that is not configured in json) at 4th line" should "be momo,nga.da,yo" in {

      tableBodyDict("test")
        .zipWithIndex
        .foreach {
          case (row, i) if i == 2 => {
            implicit val _row = row
            assert(colval"column5" == "momo,nga.da,yo")
          }

          case _ => /* nothing to do */
        }

    }

    "where syntax" should "work correctly" in {

      val matchedRows =
        tableBodyDict("test")
          .where(
            Vector(
              ("column5", "5")
            )
          )

      assert(matchedRows.size == 3)

      val matchedRows2 =
        matchedRows
          .whereNot(
            Vector(
              ("column7", "8")
            )
          )

      assert(matchedRows2.size == 2)


      //      val todoMatchedRows =
      //        tableBody
      //          .where(
      //            cond"column5 == 5 && column7 != 8"
      //          )
      //      )


    }
  }

}
