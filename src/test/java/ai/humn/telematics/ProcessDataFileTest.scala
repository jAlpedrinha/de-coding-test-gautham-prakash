package ai.humn.telematics

import java.io.{ByteArrayOutputStream, PrintStream}
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
//import org.scalatest.funspec.AnyFunSpec
import org.scalatestplus.junit.JUnitRunner
import ai.humn.telematics.ProcessDataFile.distance

@RunWith(classOf[JUnitRunner])
class ProcessDataFileTest extends FlatSpec with Matchers {

  val testFilePath = this.getClass.getClassLoader.getResource("2021-10-05_journeys.csv")

  behavior of "TestSuit"
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
    assert(j != null)
  }

  behavior of "Solution to problems"
  var filePath = "/Users/gautham/Interview assessment/AON/de-coding-test-gautham-prakash/src/test/resources/2021-10-05_journeys.csv"
  val j = ProcessDataFile.getData(filePath)
  it should "Find journeys that are more than 90 minutes" in {
    //ProcessDataFile.journey90(j)
    val baos = new ByteArrayOutputStream()
    val ps = new PrintStream(baos)
    Console.withOut(ps) {
      ProcessDataFile.journey90(j)
    }
    val output = baos.toString
    assert(output !=null)
  }

  it should "Find the average speed per journey in kph" in {
    val baos = new ByteArrayOutputStream()
    val ps = new PrintStream(baos)
    Console.withOut(ps) {
      ProcessDataFile.avgSpeedPerJourney(j)
    }
    val output = baos.toString
    assert(output !=null)
  }

  it should "Find the mileage per driver and most active driver" in {
    val baos = new ByteArrayOutputStream()
    val ps = new PrintStream(baos)
    Console.withOut(ps) {
      ProcessDataFile.mileageByDriver(j)
    }
    val output = baos.toString
    assert(output !=null)
  }
}