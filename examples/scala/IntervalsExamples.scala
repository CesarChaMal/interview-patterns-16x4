import scala.collection.mutable

object ScalaIntervalsExamples {
  
  // 1. Merge Intervals
  def merge(intervals: Array[Array[Int]]): Array[Array[Int]] = {
    val sorted = intervals.sortBy(_(0))
    val merged = mutable.ListBuffer[Array[Int]]()
    
    for (interval <- sorted) {
      if (merged.isEmpty || merged.last(1) < interval(0)) {
        merged += interval
      } else {
        merged.last(1) = math.max(merged.last(1), interval(1))
      }
    }
    
    merged.toArray
  }
  
  // 2. Insert Interval
  def insert(intervals: Array[Array[Int]], newInterval: Array[Int]): Array[Array[Int]] = {
    val result = mutable.ListBuffer[Array[Int]]()
    var i = 0
    
    // Add all intervals before newInterval
    while (i < intervals.length && intervals(i)(1) < newInterval(0)) {
      result += intervals(i)
      i += 1
    }
    
    // Merge overlapping intervals
    while (i < intervals.length && intervals(i)(0) <= newInterval(1)) {
      newInterval(0) = math.min(newInterval(0), intervals(i)(0))
      newInterval(1) = math.max(newInterval(1), intervals(i)(1))
      i += 1
    }
    result += newInterval
    
    // Add remaining intervals
    while (i < intervals.length) {
      result += intervals(i)
      i += 1
    }
    
    result.toArray
  }
  
  // 3. Non-overlapping Intervals
  def eraseOverlapIntervals(intervals: Array[Array[Int]]): Int = {
    val sorted = intervals.sortBy(_(1))
    var count = 0
    var end = sorted(0)(1)
    
    for (i <- 1 until sorted.length) {
      if (sorted(i)(0) < end) {
        count += 1
      } else {
        end = sorted(i)(1)
      }
    }
    
    count
  }
  
  // 4. Meeting Rooms
  def canAttendMeetings(intervals: Array[Array[Int]]): Boolean = {
    val sorted = intervals.sortBy(_(0))
    for (i <- 1 until sorted.length) {
      if (sorted(i)(0) < sorted(i - 1)(1)) return false
    }
    true
  }
  
  // 5. Meeting Rooms II
  def minMeetingRooms(intervals: Array[Array[Int]]): Int = {
    val sorted = intervals.sortBy(_(0))
    val heap = mutable.PriorityQueue[Int]()(Ordering.Int.reverse)
    
    for (interval <- sorted) {
      if (heap.nonEmpty && heap.head <= interval(0)) {
        heap.dequeue()
      }
      heap.enqueue(interval(1))
    }
    
    heap.size
  }
  
  // 6. Interval List Intersections
  def intervalIntersection(firstList: Array[Array[Int]], secondList: Array[Array[Int]]): Array[Array[Int]] = {
    val result = mutable.ListBuffer[Array[Int]]()
    var i = 0
    var j = 0
    
    while (i < firstList.length && j < secondList.length) {
      val start = math.max(firstList(i)(0), secondList(j)(0))
      val end = math.min(firstList(i)(1), secondList(j)(1))
      
      if (start <= end) {
        result += Array(start, end)
      }
      
      if (firstList(i)(1) < secondList(j)(1)) i += 1
      else j += 1
    }
    
    result.toArray
  }
  
  // 7. Employee Free Time
  def employeeFreeTime(schedule: List[List[Array[Int]]]): List[Array[Int]] = {
    val intervals = mutable.ListBuffer[Array[Int]]()
    for (employee <- schedule; interval <- employee) {
      intervals += interval
    }
    
    val sorted = intervals.sortBy(_(0))
    val merged = mutable.ListBuffer[Array[Int]]()
    
    for (interval <- sorted) {
      if (merged.isEmpty || merged.last(1) < interval(0)) {
        merged += interval
      } else {
        merged.last(1) = math.max(merged.last(1), interval(1))
      }
    }
    
    val result = mutable.ListBuffer[Array[Int]]()
    for (i <- 1 until merged.length) {
      result += Array(merged(i - 1)(1), merged(i)(0))
    }
    
    result.toList
  }
  
  // 8. My Calendar I
  class MyCalendar {
    private val calendar = mutable.ListBuffer[Array[Int]]()
    
    def book(start: Int, end: Int): Boolean = {
      for (event <- calendar) {
        if (start < event(1) && end > event(0)) return false
      }
      calendar += Array(start, end)
      true
    }
  }
  
  // 9. Minimum Number of Arrows to Burst Balloons
  def findMinArrowShots(points: Array[Array[Int]]): Int = {
    val sorted = points.sortBy(_(1))
    var arrows = 1
    var end = sorted(0)(1)
    
    for (i <- 1 until sorted.length) {
      if (sorted(i)(0) > end) {
        arrows += 1
        end = sorted(i)(1)
      }
    }
    
    arrows
  }
  
  // 10. Car Pooling
  def carPooling(trips: Array[Array[Int]], capacity: Int): Boolean = {
    val timeline = Array.fill(1001)(0)
    
    for (trip <- trips) {
      timeline(trip(1)) += trip(0)
      timeline(trip(2)) -= trip(0)
    }
    
    var passengers = 0
    for (change <- timeline) {
      passengers += change
      if (passengers > capacity) return false
    }
    
    true
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Intervals Examples ===")
    
    // Test 1: Merge Intervals
    val intervals1 = Array(Array(1,3), Array(2,6), Array(8,10), Array(15,18))
    println(s"Merge Intervals: ${merge(intervals1).map(_.mkString("[", ",", "]")).mkString("[", ",", "]")}")
    
    // Test 2: Insert Interval
    val intervals2 = Array(Array(1,3), Array(6,9))
    val newInterval = Array(2,5)
    println(s"Insert Interval: ${insert(intervals2, newInterval).map(_.mkString("[", ",", "]")).mkString("[", ",", "]")}")
    
    // Test 3: Non-overlapping Intervals
    val intervals3 = Array(Array(1,2), Array(2,3), Array(3,4), Array(1,3))
    println(s"Erase Overlap Intervals: ${eraseOverlapIntervals(intervals3)}")
    
    // Test 4: Meeting Rooms
    val meetings1 = Array(Array(0,30), Array(5,10), Array(15,20))
    println(s"Can Attend Meetings: ${canAttendMeetings(meetings1)}")
    
    // Test 5: Meeting Rooms II
    val meetings2 = Array(Array(0,30), Array(5,10), Array(15,20))
    println(s"Min Meeting Rooms: ${minMeetingRooms(meetings2)}")
    
    // Test 6: Interval Intersections
    val first = Array(Array(0,2), Array(5,10), Array(13,23), Array(24,25))
    val second = Array(Array(1,5), Array(8,12), Array(15,24), Array(25,26))
    println(s"Interval Intersections: ${intervalIntersection(first, second).map(_.mkString("[", ",", "]")).mkString("[", ",", "]")}")
    
    // Test 7: Employee Free Time
    val schedule = List(
      List(Array(1,3), Array(6,7)),
      List(Array(2,4)),
      List(Array(2,5), Array(9,12))
    )
    println(s"Employee Free Time: ${employeeFreeTime(schedule).length} free intervals")
    
    // Test 8: My Calendar
    val calendar = new MyCalendar()
    println(s"Calendar book(10,20): ${calendar.book(10, 20)}")
    println(s"Calendar book(15,25): ${calendar.book(15, 25)}")
    println(s"Calendar book(20,30): ${calendar.book(20, 30)}")
    
    // Test 9: Min Arrow Shots
    val balloons = Array(Array(10,16), Array(2,8), Array(1,6), Array(7,12))
    println(s"Min Arrow Shots: ${findMinArrowShots(balloons)}")
    
    // Test 10: Car Pooling
    val trips = Array(Array(2,1,5), Array(3,3,7))
    println(s"Car Pooling(capacity=4): ${carPooling(trips, 4)}")
  }
}