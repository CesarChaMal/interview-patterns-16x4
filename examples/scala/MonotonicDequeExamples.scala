import scala.collection.mutable

object ScalaMonotonicDequeExamples {
  
  // 1. Sliding Window Maximum
  def maxSlidingWindow(nums: Array[Int], k: Int): Array[Int] = {
    val deque = mutable.ArrayDeque[Int]()
    val result = mutable.ArrayBuffer[Int]()
    
    for (i <- nums.indices) {
      while (deque.nonEmpty && deque.head < i - k + 1) {
        deque.removeHead()
      }
      while (deque.nonEmpty && nums(deque.last) < nums(i)) {
        deque.removeLast()
      }
      deque.addOne(i)
      if (i >= k - 1) {
        result += nums(deque.head)
      }
    }
    result.toArray
  }
  
  // 2. Sliding Window Minimum
  def minSlidingWindow(nums: Array[Int], k: Int): Array[Int] = {
    val deque = mutable.ArrayDeque[Int]()
    val result = mutable.ArrayBuffer[Int]()
    
    for (i <- nums.indices) {
      while (deque.nonEmpty && deque.head < i - k + 1) {
        deque.removeHead()
      }
      while (deque.nonEmpty && nums(deque.last) > nums(i)) {
        deque.removeLast()
      }
      deque.addOne(i)
      if (i >= k - 1) {
        result += nums(deque.head)
      }
    }
    result.toArray
  }
  
  // 3. Constrained Subsequence Sum
  def constrainedSubsetSum(nums: Array[Int], k: Int): Int = {
    val deque = mutable.ArrayDeque[Int]()
    val dp = new Array[Int](nums.length)
    
    for (i <- nums.indices) {
      while (deque.nonEmpty && deque.head < i - k) {
        deque.removeHead()
      }
      dp(i) = nums(i) + (if (deque.nonEmpty) math.max(0, dp(deque.head)) else 0)
      while (deque.nonEmpty && dp(deque.last) <= dp(i)) {
        deque.removeLast()
      }
      deque.addOne(i)
    }
    dp.max
  }
  
  // 4. Shortest Subarray with Sum at Least K
  def shortestSubarray(nums: Array[Int], k: Int): Int = {
    val prefixSum = Array.ofDim[Long](nums.length + 1)
    for (i <- nums.indices) {
      prefixSum(i + 1) = prefixSum(i) + nums(i)
    }
    
    val deque = mutable.ArrayDeque[Int]()
    var minLen = Int.MaxValue
    
    for (i <- prefixSum.indices) {
      while (deque.nonEmpty && prefixSum(i) - prefixSum(deque.head) >= k) {
        minLen = math.min(minLen, i - deque.removeHead())
      }
      while (deque.nonEmpty && prefixSum(deque.last) >= prefixSum(i)) {
        deque.removeLast()
      }
      deque.addOne(i)
    }
    
    if (minLen == Int.MaxValue) -1 else minLen
  }
  
  // 5. Jump Game VI
  def maxResult(nums: Array[Int], k: Int): Int = {
    val deque = mutable.ArrayDeque[Int]()
    val dp = new Array[Int](nums.length)
    dp(0) = nums(0)
    deque.addOne(0)
    
    for (i <- 1 until nums.length) {
      while (deque.nonEmpty && deque.head < i - k) {
        deque.removeHead()
      }
      dp(i) = nums(i) + dp(deque.head)
      while (deque.nonEmpty && dp(deque.last) <= dp(i)) {
        deque.removeLast()
      }
      deque.addOne(i)
    }
    
    dp.last
  }
  
  // 6. Largest Rectangle in Histogram
  def largestRectangleArea(heights: Array[Int]): Int = {
    val stack = mutable.Stack[Int]()
    var maxArea = 0
    
    for (i <- 0 to heights.length) {
      val h = if (i == heights.length) 0 else heights(i)
      while (stack.nonEmpty && heights(stack.top) > h) {
        val height = heights(stack.pop())
        val width = if (stack.isEmpty) i else i - stack.top - 1
        maxArea = math.max(maxArea, height * width)
      }
      stack.push(i)
    }
    maxArea
  }
  
  // 7. Maximal Rectangle
  def maximalRectangle(matrix: Array[Array[Char]]): Int = {
    if (matrix.isEmpty) return 0
    val heights = new Array[Int](matrix(0).length)
    var maxArea = 0
    
    for (row <- matrix) {
      for (i <- row.indices) {
        heights(i) = if (row(i) == '1') heights(i) + 1 else 0
      }
      maxArea = math.max(maxArea, largestRectangleArea(heights))
    }
    maxArea
  }
  
  // 8. Remove K Digits
  def removeKdigits(num: String, k: Int): String = {
    val stack = mutable.Stack[Char]()
    var toRemove = k
    
    for (digit <- num) {
      while (stack.nonEmpty && toRemove > 0 && stack.top > digit) {
        stack.pop()
        toRemove -= 1
      }
      stack.push(digit)
    }
    
    while (toRemove > 0) {
      stack.pop()
      toRemove -= 1
    }
    
    val result = stack.reverse.mkString.dropWhile(_ == '0')
    if (result.isEmpty) "0" else result
  }
  
  // 9. Create Maximum Number
  def maxNumber(nums1: Array[Int], nums2: Array[Int], k: Int): Array[Int] = {
    def maxArray(nums: Array[Int], k: Int): Array[Int] = {
      val result = mutable.ArrayBuffer[Int]()
      var drop = nums.length - k
      for (num <- nums) {
        while (result.nonEmpty && result.last < num && drop > 0) {
          result.removeLast()
          drop -= 1
        }
        if (result.length < k) {
          result += num
        } else {
          drop -= 1
        }
      }
      result.toArray
    }
    
    def merge(nums1: Array[Int], nums2: Array[Int]): Array[Int] = {
      val result = mutable.ArrayBuffer[Int]()
      var i = 0
      var j = 0
      while (i < nums1.length || j < nums2.length) {
        if (greater(nums1, i, nums2, j)) {
          result += nums1(i)
          i += 1
        } else {
          result += nums2(j)
          j += 1
        }
      }
      result.toArray
    }
    
    def greater(nums1: Array[Int], i: Int, nums2: Array[Int], j: Int): Boolean = {
      var x = i
      var y = j
      while (x < nums1.length && y < nums2.length && nums1(x) == nums2(y)) {
        x += 1
        y += 1
      }
      y == nums2.length || (x < nums1.length && nums1(x) > nums2(y))
    }
    
    var result = Array[Int]()
    for (i <- math.max(0, k - nums2.length) to math.min(k, nums1.length)) {
      val candidate = merge(maxArray(nums1, i), maxArray(nums2, k - i))
      if (greater(candidate, 0, result, 0)) {
        result = candidate
      }
    }
    result
  }
  
  // 10. Minimum Window Subsequence
  def minWindow(s: String, t: String): String = {
    def findSubsequence(start: Int): Int = {
      var i = start
      for (char <- t) {
        while (i < s.length && s(i) != char) {
          i += 1
        }
        if (i == s.length) return -1
        i += 1
      }
      i - 1
    }
    
    var minLen = Int.MaxValue
    var result = ""
    
    for (i <- s.indices) {
      if (s(i) == t(0)) {
        val end = findSubsequence(i)
        if (end != -1 && end - i + 1 < minLen) {
          minLen = end - i + 1
          result = s.substring(i, end + 1)
        }
      }
    }
    result
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Monotonic Deque Examples ===")
    
    // Test 1: Sliding Window Maximum
    println(s"Max Sliding Window([1,3,-1,-3,5,3,6,7], 3): ${maxSlidingWindow(Array(1,3,-1,-3,5,3,6,7), 3).mkString("[", ",", "]")}")
    
    // Test 2: Sliding Window Minimum
    println(s"Min Sliding Window([1,3,-1,-3,5,3,6,7], 3): ${minSlidingWindow(Array(1,3,-1,-3,5,3,6,7), 3).mkString("[", ",", "]")}")
    
    // Test 3: Constrained Subsequence Sum
    println(s"Constrained Subset Sum([10,2,-10,5,20], 2): ${constrainedSubsetSum(Array(10,2,-10,5,20), 2)}")
    
    // Test 4: Shortest Subarray
    println(s"Shortest Subarray([1], 1): ${shortestSubarray(Array(1), 1)}")
    
    // Test 5: Jump Game VI
    println(s"Max Result([1,-1,-2,4,-7,3], 2): ${maxResult(Array(1,-1,-2,4,-7,3), 2)}")
    
    // Test 6: Largest Rectangle
    println(s"Largest Rectangle([2,1,5,6,2,3]): ${largestRectangleArea(Array(2,1,5,6,2,3))}")
    
    // Test 7: Maximal Rectangle
    val matrix = Array(Array('1','0','1','0','0'),Array('1','0','1','1','1'),Array('1','1','1','1','1'),Array('1','0','0','1','0'))
    println(s"Maximal Rectangle: ${maximalRectangle(matrix)}")
    
    // Test 8: Remove K Digits
    println(s"Remove K Digits('1432219', 3): ${removeKdigits("1432219", 3)}")
    
    // Test 9: Create Maximum Number
    println(s"Max Number([3,4,6,5], [9,1,2,5,8,3], 5): ${maxNumber(Array(3,4,6,5), Array(9,1,2,5,8,3), 5).mkString("[", ",", "]")}")
    
    // Test 10: Minimum Window Subsequence
    println(s"Min Window('abcdebdde', 'bde'): ${minWindow("abcdebdde", "bde")}")
  }
}