package ai.humn.telematics

import java.io.{ByteArrayOutputStream, PrintStream}
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.scalatestplus.junit.JUnitRunner
import ai.humn.telematics.ProcessDataFile.distance

@RunWith(classOf[JUnitRunner])
class ProcessDataFileTest extends FlatSpec with Matchers {

  val testFilePath = this.getClass.getClassLoader.getResource("2021-10-05_journeys.csv")

  // This test is failing but I don't know why :(
  it should "generate results to stdout" in {
    //println(testFilePath.getPath())
    ProcessDataFile.main(Array(testFilePath.getPath))
  }

  it should "calculate the distance" in {
    assert(ProcessDataFile.distance(2,4) == 2)
  }

  it should "calculate duration" in {
    assert(ProcessDataFile.duration(2,4) == 2)
  }

  it should "calculate average speed" in {
    assert(ProcessDataFile.avgSpeed(3.0,600000) == 18.0)
  }

  it should "Read a CSV file when path of file is provided" in {
    var filePath = "/Users/gautham/Interview assessment/AON/de-coding-test-gautham-prakash/src/test/resources/2021-10-05_journeys.csv"
    val j = ProcessDataFile.getData(filePath)
    assert(j!=null)
  }
}