package ai.humn.telematics

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.{Failure, Success, Try}
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

object ProcessDataFile {

  def getData(filePath:String):List[Array[String]]={

    val fileContent = Source.fromFile(filePath)

    val lines: Seq[String] = fileContent.getLines().toList

    fileContent.close()

    var results = ListBuffer[ Array[String]]()

    // Parse each line as csv to a collection
    for (line <- lines) {
      results += line.split(",")
    }

    val journey = results.toList
    val j = journey.distinct

    j
  }

  // Function to find the distance of a trip.
  def distance(start:Double, end:Double): Double = {
    var result = end - start
    result
  }

  // Function to find the duration of a trip.
  def duration(start:Double, end:Double): Long = {
    var result = end - start
    result.toLong
  }

  // Function to find the average speed of a trip.
  def avgSpeed(distance:Double, duration:Double): Double = {
    var result = distance / (duration / 1000.0 / 3600.0)
    result
  }

  // Function to find journeys that are 90 minutes or more.
  def journey90(j:List[Array[String]]): Unit = {

    var i = 1;
    var ok = true
    while(ok){
      var dura = duration(j(i)(2).toLong, j(i)(3).toLong)
      if( dura >= (90 * 1000 * 60) ){
        var dist = distance(j(i)(8).toDouble, j(i)(9).toDouble)
        if(dist > 0.0){
          var avgS = avgSpeed(dist, dura)
          println(s"JourneyId: ${j(i)(0)} ${j(i)(1)} distance ${dist} durationMS ${dura} avgSpeed in kph was ${avgS}")
          //i = i + 1
        }
      }
      i = i + 1
      if( i >= j.size ){
        ok = false
      }
    }
  }

  // Function to find the average speed per journey in kph.
  def avgSpeedPerJourney(j:List[Array[String]]): Unit = {

    val journeyBuffer = ArrayBuffer[String]()
    var i = 1
    var ok = true
    while(ok){
      if(!journeyBuffer.exists(_==j(i)(0))){
        journeyBuffer += j(i)(0)
        var dist = distance(j(i)(8).toDouble, j(i)(9).toDouble)
        var dura = duration(j(i)(2).toLong, j(i)(3).toLong)
        if(dist > 0.0 && dura > 0.0){
          var avgS = avgSpeed(dist, dura)
          println(s"JourneyId: ${j(i)(0)} ${j(i)(1)} distance ${dist} durationMS ${dura} avgSpeed in kph was ${avgS}")
        }
      }
      i = i + 1
      if( i > 9 ) ok = false
    }
  }

  // Function to find mileage per driver and most active driver. 
  def mileageByDriver(j:List[Array[String]]): Unit = {
    
    val journeyBuffer = ArrayBuffer[String]()
    var i = 1
    var ok = true
    var highest = 0
    var mostActiveDriver = ""
    var temp = ""
    var total = 0
    val mileageTotal = mutable.Map.empty[String, Int]
    while(ok){
      if(!journeyBuffer.exists(_==j(i)(0))){
        journeyBuffer += j(i)(0) 
        var dist = distance(j(i)(8).toDouble, j(i)(9).toDouble)
        var dura = duration(j(i)(2).toLong, j(i)(3).toLong)
        if(dist > 0.0 && dura > 0.0){
          if(temp != j(i)(1)){
            total = 0
            total = total + dist.toInt
            mileageTotal.put(j(i)(1), total)
          }else{
            total = total + dist.toInt
            mileageTotal(j(i)(1)) = total
          }
          if(total > highest ){
            highest = total.toInt
            mostActiveDriver = j(i)(1)
          }
        }
        temp = j(i)(1)
      } 
      i = i + 1
      if( i >= j.size ) ok = false
    }

    mileageTotal.foreach { case (key, value) =>
      println(s"$key drove ${value} kilometers")
    }
    println()
    // 4. Find the most active driver - the driver who has driven the most kilometers.
    println(s"Most active driver is ${mostActiveDriver}")
  }

  def main(args: Array[String]): Unit = {

    val filePath = "/Users/gautham/Interview assessment/AON/de-coding-test-gautham-prakash/src/test/resources/2021-10-05_journeys.csv" // Replace this file path with the CSV file path in your repo.
    val j = getData(filePath)
    
    // 1. Find journeys that are 90 minutes or more. 
    println("Journeys of 90 minutes or more.")
    journey90(j)
    println();

    // 2. Find the average speed per journey in kph.
    println("Average speed in Kph")
    avgSpeedPerJourney(j)
    println();

    //3. Find the total mileage by driver for the whole day and most active driver.
    println("Mileage By Driver")
    mileageByDriver(j)
  }

}
