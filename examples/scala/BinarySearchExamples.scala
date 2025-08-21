object ScalaBinarySearchExamples {
  
  // 1. Binary Search
  def search(nums: Array[Int], target: Int): Int = {
    var left = 0
    var right = nums.length - 1
    while (left <= right) {
      val mid = left + (right - left) / 2
      if (nums(mid) == target) return mid
      else if (nums(mid) < target) left = mid + 1
      else right = mid - 1
    }
    -1
  }
  
  // 2. First Bad Version
  def firstBadVersion(n: Int): Int = {
    var left = 1
    var right = n
    while (left < right) {
      val mid = left + (right - left) / 2
      if (isBadVersion(mid)) right = mid
      else left = mid + 1
    }
    left
  }
  
  private def isBadVersion(version: Int): Boolean = {
    version >= 4 // Mock implementation
  }
  
  // 3. Search Insert Position
  def searchInsert(nums: Array[Int], target: Int): Int = {
    var left = 0
    var right = nums.length - 1
    while (left <= right) {
      val mid = left + (right - left) / 2
      if (nums(mid) == target) return mid
      else if (nums(mid) < target) left = mid + 1
      else right = mid - 1
    }
    left
  }
  
  // 4. Find Peak Element
  def findPeakElement(nums: Array[Int]): Int = {
    var left = 0
    var right = nums.length - 1
    while (left < right) {
      val mid = left + (right - left) / 2
      if (nums(mid) > nums(mid + 1)) right = mid
      else left = mid + 1
    }
    left
  }
  
  // 5. Search in Rotated Sorted Array
  def searchRotated(nums: Array[Int], target: Int): Int = {
    var left = 0
    var right = nums.length - 1
    while (left <= right) {
      val mid = left + (right - left) / 2
      if (nums(mid) == target) return mid
      
      if (nums(left) <= nums(mid)) {
        if (nums(left) <= target && target < nums(mid)) right = mid - 1
        else left = mid + 1
      } else {
        if (nums(mid) < target && target <= nums(right)) left = mid + 1
        else right = mid - 1
      }
    }
    -1
  }
  
  // 6. Find Minimum in Rotated Sorted Array
  def findMin(nums: Array[Int]): Int = {
    var left = 0
    var right = nums.length - 1
    while (left < right) {
      val mid = left + (right - left) / 2
      if (nums(mid) > nums(right)) left = mid + 1
      else right = mid
    }
    nums(left)
  }
  
  // 7. Search a 2D Matrix
  def searchMatrix(matrix: Array[Array[Int]], target: Int): Boolean = {
    val m = matrix.length
    val n = matrix(0).length
    var left = 0
    var right = m * n - 1
    while (left <= right) {
      val mid = left + (right - left) / 2
      val value = matrix(mid / n)(mid % n)
      if (value == target) return true
      else if (value < target) left = mid + 1
      else right = mid - 1
    }
    false
  }
  
  // 8. Find First and Last Position
  def searchRange(nums: Array[Int], target: Int): Array[Int] = {
    def findFirst(nums: Array[Int], target: Int): Int = {
      var left = 0
      var right = nums.length - 1
      var result = -1
      while (left <= right) {
        val mid = left + (right - left) / 2
        if (nums(mid) == target) {
          result = mid
          right = mid - 1
        } else if (nums(mid) < target) left = mid + 1
        else right = mid - 1
      }
      result
    }
    
    def findLast(nums: Array[Int], target: Int): Int = {
      var left = 0
      var right = nums.length - 1
      var result = -1
      while (left <= right) {
        val mid = left + (right - left) / 2
        if (nums(mid) == target) {
          result = mid
          left = mid + 1
        } else if (nums(mid) < target) left = mid + 1
        else right = mid - 1
      }
      result
    }
    
    Array(findFirst(nums, target), findLast(nums, target))
  }
  
  // 9. Sqrt(x)
  def mySqrt(x: Int): Int = {
    if (x == 0) return 0
    var left = 1
    var right = x
    while (left <= right) {
      val mid = left + (right - left) / 2
      if (mid == x / mid) return mid
      else if (mid < x / mid) left = mid + 1
      else right = mid - 1
    }
    right
  }
  
  // 10. Valid Perfect Square
  def isPerfectSquare(num: Int): Boolean = {
    var left = 1
    var right = num
    while (left <= right) {
      val mid = left + (right - left) / 2
      val square = mid.toLong * mid
      if (square == num) return true
      else if (square < num) left = mid + 1
      else right = mid - 1
    }
    false
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Binary Search Examples ===")
    
    // Test 1: Binary Search
    println(s"Binary Search([-1,0,3,5,9,12], 9): ${search(Array(-1,0,3,5,9,12), 9)}")
    
    // Test 2: First Bad Version
    println(s"First Bad Version(5): ${firstBadVersion(5)}")
    
    // Test 3: Search Insert Position
    println(s"Search Insert([1,3,5,6], 5): ${searchInsert(Array(1,3,5,6), 5)}")
    
    // Test 4: Find Peak Element
    println(s"Find Peak([1,2,3,1]): ${findPeakElement(Array(1,2,3,1))}")
    
    // Test 5: Search in Rotated Sorted Array
    println(s"Search Rotated([4,5,6,7,0,1,2], 0): ${searchRotated(Array(4,5,6,7,0,1,2), 0)}")
    
    // Test 6: Find Minimum in Rotated Sorted Array
    println(s"Find Min([3,4,5,1,2]): ${findMin(Array(3,4,5,1,2))}")
    
    // Test 7: Search a 2D Matrix
    val matrix = Array(Array(1,4,7,11),Array(2,5,8,12),Array(3,6,9,16))
    println(s"Search Matrix(matrix, 5): ${searchMatrix(matrix, 5)}")
    
    // Test 8: Find First and Last Position
    println(s"Search Range([5,7,7,8,8,10], 8): ${searchRange(Array(5,7,7,8,8,10), 8).mkString("[", ",", "]")}")
    
    // Test 9: Sqrt(x)
    println(s"Sqrt(4): ${mySqrt(4)}")
    
    // Test 10: Valid Perfect Square
    println(s"Is Perfect Square(16): ${isPerfectSquare(16)}")
  }
}