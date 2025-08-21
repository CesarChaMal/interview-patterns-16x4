object ScalaBinarySearchAnswerExamples {
  
  // 1. Koko Eating Bananas
  def minEatingSpeed(piles: Array[Int], h: Int): Int = {
    var left = 1
    var right = piles.max
    while (left < right) {
      val mid = left + (right - left) / 2
      val hours = piles.map(pile => (pile + mid - 1) / mid).sum
      if (hours <= h) right = mid
      else left = mid + 1
    }
    left
  }
  
  // 2. Capacity To Ship Packages Within D Days
  def shipWithinDays(weights: Array[Int], days: Int): Int = {
    var left = weights.max
    var right = weights.sum
    while (left < right) {
      val mid = left + (right - left) / 2
      var daysNeeded = 1
      var currentWeight = 0
      for (weight <- weights) {
        if (currentWeight + weight > mid) {
          daysNeeded += 1
          currentWeight = weight
        } else {
          currentWeight += weight
        }
      }
      if (daysNeeded <= days) right = mid
      else left = mid + 1
    }
    left
  }
  
  // 3. Split Array Largest Sum
  def splitArray(nums: Array[Int], m: Int): Int = {
    var left = nums.max
    var right = nums.sum
    while (left < right) {
      val mid = left + (right - left) / 2
      var subarrays = 1
      var currentSum = 0
      for (num <- nums) {
        if (currentSum + num > mid) {
          subarrays += 1
          currentSum = num
        } else {
          currentSum += num
        }
      }
      if (subarrays <= m) right = mid
      else left = mid + 1
    }
    left
  }
  
  // 4. Minimum Number of Days to Make m Bouquets
  def minDays(bloomDay: Array[Int], m: Int, k: Int): Int = {
    if (m * k > bloomDay.length) return -1
    var left = bloomDay.min
    var right = bloomDay.max
    while (left < right) {
      val mid = left + (right - left) / 2
      var bouquets = 0
      var consecutive = 0
      for (bloom <- bloomDay) {
        if (bloom <= mid) {
          consecutive += 1
          if (consecutive == k) {
            bouquets += 1
            consecutive = 0
          }
        } else {
          consecutive = 0
        }
      }
      if (bouquets >= m) right = mid
      else left = mid + 1
    }
    left
  }
  
  // 5. Smallest Divisor Given Threshold
  def smallestDivisor(nums: Array[Int], threshold: Int): Int = {
    var left = 1
    var right = nums.max
    while (left < right) {
      val mid = left + (right - left) / 2
      val sum = nums.map(num => (num + mid - 1) / mid).sum
      if (sum <= threshold) right = mid
      else left = mid + 1
    }
    left
  }
  
  // 6. Magnetic Force Between Two Balls
  def maxDistance(position: Array[Int], m: Int): Int = {
    val sortedPos = position.sorted
    var left = 1
    var right = sortedPos.last - sortedPos.head
    while (left < right) {
      val mid = left + (right - left + 1) / 2
      var count = 1
      var lastPos = sortedPos(0)
      for (i <- 1 until sortedPos.length) {
        if (sortedPos(i) - lastPos >= mid) {
          count += 1
          lastPos = sortedPos(i)
          if (count == m) return mid
        }
      }
      if (count >= m) left = mid
      else right = mid - 1
    }
    left
  }
  
  // 7. Minimize Max Distance to Gas Station
  def minmaxGasDist(stations: Array[Int], k: Int): Double = {
    var left = 0.0
    var right = 1e8
    while (right - left > 1e-6) {
      val mid = (left + right) / 2
      val needed = (1 until stations.length).map(i => 
        ((stations(i) - stations(i - 1)) / mid).toInt
      ).sum
      if (needed <= k) right = mid
      else left = mid
    }
    left
  }
  
  // 8. Find K-th Smallest Pair Distance
  def smallestDistancePair(nums: Array[Int], k: Int): Int = {
    val sortedNums = nums.sorted
    var left = 0
    var right = sortedNums.last - sortedNums.head
    while (left < right) {
      val mid = left + (right - left) / 2
      var count = 0
      var leftPtr = 0
      for (rightPtr <- 1 until sortedNums.length) {
        while (sortedNums(rightPtr) - sortedNums(leftPtr) > mid) leftPtr += 1
        count += rightPtr - leftPtr
      }
      if (count >= k) right = mid
      else left = mid + 1
    }
    left
  }
  
  // 9. Median of Two Sorted Arrays
  def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {
    val (shorter, longer) = if (nums1.length > nums2.length) (nums2, nums1) else (nums1, nums2)
    val m = shorter.length
    val n = longer.length
    var left = 0
    var right = m
    
    while (left <= right) {
      val partitionX = (left + right) / 2
      val partitionY = (m + n + 1) / 2 - partitionX
      
      val maxLeftX = if (partitionX == 0) Int.MinValue else shorter(partitionX - 1)
      val minRightX = if (partitionX == m) Int.MaxValue else shorter(partitionX)
      
      val maxLeftY = if (partitionY == 0) Int.MinValue else longer(partitionY - 1)
      val minRightY = if (partitionY == n) Int.MaxValue else longer(partitionY)
      
      if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
        if ((m + n) % 2 == 0) {
          return (math.max(maxLeftX, maxLeftY) + math.min(minRightX, minRightY)) / 2.0
        } else {
          return math.max(maxLeftX, maxLeftY)
        }
      } else if (maxLeftX > minRightY) {
        right = partitionX - 1
      } else {
        left = partitionX + 1
      }
    }
    -1
  }
  
  // 10. Aggressive Cows
  def aggressiveCows(stalls: Array[Int], cows: Int): Int = {
    val sortedStalls = stalls.sorted
    var left = 1
    var right = sortedStalls.last - sortedStalls.head
    while (left < right) {
      val mid = left + (right - left + 1) / 2
      var count = 1
      var lastPos = sortedStalls(0)
      for (i <- 1 until sortedStalls.length) {
        if (sortedStalls(i) - lastPos >= mid) {
          count += 1
          lastPos = sortedStalls(i)
          if (count == cows) return mid
        }
      }
      if (count >= cows) left = mid
      else right = mid - 1
    }
    left
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Binary Search on Answer Examples ===")
    
    // Test 1: Koko Eating Bananas
    println(s"Min Eating Speed([3,6,7,11], 8): ${minEatingSpeed(Array(3,6,7,11), 8)}")
    
    // Test 2: Ship Within Days
    println(s"Ship Within Days([1,2,3,4,5,6,7,8,9,10], 5): ${shipWithinDays(Array(1,2,3,4,5,6,7,8,9,10), 5)}")
    
    // Test 3: Split Array Largest Sum
    println(s"Split Array([7,2,5,10,8], 2): ${splitArray(Array(7,2,5,10,8), 2)}")
    
    // Test 4: Min Days for Bouquets
    println(s"Min Days([1,10,3,10,2], 3, 1): ${minDays(Array(1,10,3,10,2), 3, 1)}")
    
    // Test 5: Smallest Divisor
    println(s"Smallest Divisor([1,2,5,9], 6): ${smallestDivisor(Array(1,2,5,9), 6)}")
    
    // Test 6: Magnetic Force
    println(s"Max Distance([1,2,3,4,7], 3): ${maxDistance(Array(1,2,3,4,7), 3)}")
    
    // Test 7: Min Max Gas Distance
    println(s"Min Max Gas Dist([1,2,3,4,5,6,7,8,9,10], 9): ${minmaxGasDist(Array(1,2,3,4,5,6,7,8,9,10), 9)}")
    
    // Test 8: Smallest Distance Pair
    println(s"Smallest Distance Pair([1,3,1], 1): ${smallestDistancePair(Array(1,3,1), 1)}")
    
    // Test 9: Median of Two Sorted Arrays
    println(s"Find Median([1,3], [2]): ${findMedianSortedArrays(Array(1,3), Array(2))}")
    
    // Test 10: Aggressive Cows
    println(s"Aggressive Cows([1,2,4,8,9], 3): ${aggressiveCows(Array(1,2,4,8,9), 3)}")
  }
}