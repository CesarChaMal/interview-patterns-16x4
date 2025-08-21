import scala.collection.mutable

object ScalaHeapExamples {
  
  // 1. Kth Largest Element in an Array
  def findKthLargest(nums: Array[Int], k: Int): Int = {
    val minHeap = mutable.PriorityQueue[Int]()(Ordering.Int.reverse)
    for (num <- nums) {
      minHeap.enqueue(num)
      if (minHeap.size > k) minHeap.dequeue()
    }
    minHeap.head
  }
  
  // 2. Top K Frequent Elements
  def topKFrequent(nums: Array[Int], k: Int): Array[Int] = {
    val count = mutable.Map[Int, Int]()
    nums.foreach(num => count(num) = count.getOrElse(num, 0) + 1)
    
    val minHeap = mutable.PriorityQueue[(Int, Int)]()(Ordering.by(_._2).reverse)
    for ((num, freq) <- count) {
      minHeap.enqueue((num, freq))
      if (minHeap.size > k) minHeap.dequeue()
    }
    
    minHeap.map(_._1).toArray
  }
  
  // 3. Merge k Sorted Lists
  case class ListNode(var x: Int = 0, var next: ListNode = null)
  
  def mergeKLists(lists: Array[ListNode]): ListNode = {
    val minHeap = mutable.PriorityQueue[(Int, Int, ListNode)]()(Ordering.by(_._1).reverse)
    
    for (i <- lists.indices if lists(i) != null) {
      minHeap.enqueue((lists(i).x, i, lists(i)))
    }
    
    val dummy = ListNode(0)
    var curr = dummy
    
    while (minHeap.nonEmpty) {
      val (value, i, node) = minHeap.dequeue()
      curr.next = node
      curr = curr.next
      
      if (node.next != null) {
        minHeap.enqueue((node.next.x, i, node.next))
      }
    }
    
    dummy.next
  }
  
  // 4. Find Median from Data Stream
  class MedianFinder {
    private val maxHeap = mutable.PriorityQueue[Int]() // for smaller half
    private val minHeap = mutable.PriorityQueue[Int]()(Ordering.Int.reverse) // for larger half
    
    def addNum(num: Int): Unit = {
      maxHeap.enqueue(num)
      minHeap.enqueue(maxHeap.dequeue())
      
      if (minHeap.size > maxHeap.size) {
        maxHeap.enqueue(minHeap.dequeue())
      }
    }
    
    def findMedian(): Double = {
      if (maxHeap.size > minHeap.size) maxHeap.head.toDouble
      else (maxHeap.head + minHeap.head) / 2.0
    }
  }
  
  // 5. K Closest Points to Origin
  def kClosest(points: Array[Array[Int]], k: Int): Array[Array[Int]] = {
    val maxHeap = mutable.PriorityQueue[(Int, Array[Int])]()(Ordering.by(_._1))
    
    for (point <- points) {
      val dist = point(0) * point(0) + point(1) * point(1)
      maxHeap.enqueue((dist, point))
      if (maxHeap.size > k) maxHeap.dequeue()
    }
    
    maxHeap.map(_._2).toArray
  }
  
  // 6. Last Stone Weight
  def lastStoneWeight(stones: Array[Int]): Int = {
    val maxHeap = mutable.PriorityQueue[Int]()
    stones.foreach(maxHeap.enqueue(_))
    
    while (maxHeap.size > 1) {
      val first = maxHeap.dequeue()
      val second = maxHeap.dequeue()
      if (first != second) {
        maxHeap.enqueue(first - second)
      }
    }
    
    if (maxHeap.isEmpty) 0 else maxHeap.head
  }
  
  // 7. Task Scheduler
  def leastInterval(tasks: Array[Char], n: Int): Int = {
    val count = mutable.Map[Char, Int]()
    tasks.foreach(task => count(task) = count.getOrElse(task, 0) + 1)
    
    val maxHeap = mutable.PriorityQueue[Int]()
    count.values.filter(_ > 0).foreach(maxHeap.enqueue(_))
    
    var time = 0
    while (maxHeap.nonEmpty) {
      val temp = mutable.ListBuffer[Int]()
      for (i <- 0 to n) {
        if (maxHeap.nonEmpty) {
          val freq = maxHeap.dequeue()
          if (freq > 1) temp += (freq - 1)
        }
        time += 1
        if (maxHeap.isEmpty && temp.isEmpty) return time
      }
      temp.foreach(maxHeap.enqueue(_))
    }
    time
  }
  
  // 8. Ugly Number II
  def nthUglyNumber(n: Int): Int = {
    val minHeap = mutable.PriorityQueue[Long]()(Ordering.Long.reverse)
    val seen = mutable.Set[Long]()
    
    minHeap.enqueue(1L)
    seen.add(1L)
    
    var ugly = 1L
    for (_ <- 0 until n) {
      ugly = minHeap.dequeue()
      for (factor <- Array(2, 3, 5)) {
        val next = ugly * factor
        if (!seen.contains(next)) {
          minHeap.enqueue(next)
          seen.add(next)
        }
      }
    }
    ugly.toInt
  }
  
  // 9. Meeting Rooms II
  def minMeetingRooms(intervals: Array[Array[Int]]): Int = {
    val sorted = intervals.sortBy(_(0))
    val minHeap = mutable.PriorityQueue[Int]()(Ordering.Int.reverse)
    
    for (interval <- sorted) {
      if (minHeap.nonEmpty && minHeap.head <= interval(0)) {
        minHeap.dequeue()
      }
      minHeap.enqueue(interval(1))
    }
    minHeap.size
  }
  
  // 10. Kth Smallest Element in a Sorted Matrix
  def kthSmallest(matrix: Array[Array[Int]], k: Int): Int = {
    val minHeap = mutable.PriorityQueue[(Int, Int, Int)]()(Ordering.by(_._1).reverse)
    
    for (i <- matrix.indices) {
      minHeap.enqueue((matrix(i)(0), i, 0))
    }
    
    for (_ <- 0 until k - 1) {
      val (value, row, col) = minHeap.dequeue()
      if (col + 1 < matrix(0).length) {
        minHeap.enqueue((matrix(row)(col + 1), row, col + 1))
      }
    }
    minHeap.head._1
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Heap Examples ===")
    
    // Test 1: Kth Largest Element
    println(s"Kth Largest([3,2,1,5,6,4], k=2): ${findKthLargest(Array(3,2,1,5,6,4), 2)}")
    
    // Test 2: Top K Frequent Elements
    println(s"Top K Frequent([1,1,1,2,2,3], k=2): ${topKFrequent(Array(1,1,1,2,2,3), 2).mkString("[", ",", "]")}")
    
    // Test 4: Median Finder
    val mf = new MedianFinder()
    mf.addNum(1)
    mf.addNum(2)
    println(s"Median after adding 1,2: ${mf.findMedian()}")
    
    // Test 5: K Closest Points
    val points = Array(Array(1,3), Array(-2,2))
    println(s"K Closest Points(k=1): ${kClosest(points, 1).map(_.mkString("[", ",", "]")).mkString("[", ",", "]")}")
    
    // Test 6: Last Stone Weight
    println(s"Last Stone Weight([2,7,4,1,8,1]): ${lastStoneWeight(Array(2,7,4,1,8,1))}")
    
    // Test 7: Task Scheduler
    println(s"Least Interval(['A','A','A','B','B','B'], n=2): ${leastInterval(Array('A','A','A','B','B','B'), 2)}")
    
    // Test 8: Ugly Number II
    println(s"10th Ugly Number: ${nthUglyNumber(10)}")
    
    // Test 9: Meeting Rooms II
    val meetings = Array(Array(0,30), Array(5,10), Array(15,20))
    println(s"Min Meeting Rooms: ${minMeetingRooms(meetings)}")
    
    // Test 10: Kth Smallest in Matrix
    val matrix = Array(Array(1,5,9), Array(10,11,13), Array(12,13,15))
    println(s"Kth Smallest in Matrix(k=8): ${kthSmallest(matrix, 8)}")
  }
}