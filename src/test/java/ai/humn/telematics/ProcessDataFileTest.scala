package ai.humn.telematics

import java.io.{ByteArrayOutputStream, PrintStream}
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.scalatestplus.junit.JUnitRunner
import ai.humn.telematics.ProcessDataFile.distance

@RunWith(classOf[JUnitRunner])
class ProcessDataFileTest extends FlatSpec with Matchers {

  val testFilePath = this.getClass.getClassLoader.getResource("2021-10-05_journeys.csv")

  behavior of "TestSuit"
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

  it should "read a CSV file when path of file is provided" in {
    var filePath = "/Users/gautham/Interview assessment/AON/de-coding-test-gautham-prakash/src/test/resources/2021-10-05_journeys.csv"
    val j = ProcessDataFile.getData(filePath)
    assert(j != null)
  }

  it should "calculate distance when invalid start and end distance is provided." in {
    // Invalid start distance and end distance means start distance is greater than end distance. 
    // In this case as per the code, that particular row is omitted based on the calculated distance.
    assert(ProcessDataFile.distance(87,3) == -84)
  }

  it should "calculate duration when invalid start and end duration is provided." in {
    // Invalid start duration and end duration means start duration is greater than end duration. 
    // In this case as per the code, that particular row is omitted based on the calculated duration.
    assert(ProcessDataFile.duration(87,3) == -84)
  }

  it should "calculate distance when floating point numbers are passed to the function" in {
    assert(ProcessDataFile.distance(2.0,4.0) == 2)
  }

  it should "calculate duration when floating point number are passed to the function" in {
    assert(ProcessDataFile.duration(2.0,4.0) == 2)
  }

  behavior of "Test for functions that print solution statements to the console."
  var filePath = "/Users/gautham/Interview assessment/AON/de-coding-test-gautham-prakash/src/test/resources/2021-10-05_journeys.csv"
  val j = ProcessDataFile.getData(filePath)
  it should "find journeys that are more than 90 minutes" in {
    //ProcessDataFile.journey90(j)
    val baos = new ByteArrayOutputStream()
    val ps = new PrintStream(baos)
    Console.withOut(ps) {
      ProcessDataFile.journey90(j)
    }
    val output = baos.toString
    assert(output !=null)
  }

  it should "find the average speed per journey in kph" in {
    val baos = new ByteArrayOutputStream()
    val ps = new PrintStream(baos)
    Console.withOut(ps) {
      ProcessDataFile.avgSpeedPerJourney(j)
    }
    val output = baos.toString
    assert(output !=null)
  }

  it should "find the mileage per driver and most active driver" in {
    val baos = new ByteArrayOutputStream()
    val ps = new PrintStream(baos)
    Console.withOut(ps) {
      ProcessDataFile.mileageByDriver(j)
    }
    val output = baos.toString
    assert(output !=null)
  }
}