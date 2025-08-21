import scala.collection.mutable

object ScalaPrefixSumExamples {
  
  // 1. Subarray Sum Equals K
  def subarraySum(nums: Array[Int], k: Int): Int = {
    val map = mutable.Map(0 -> 1)
    var sum = 0
    var count = 0
    
    for (num <- nums) {
      sum += num
      count += map.getOrElse(sum - k, 0)
      map(sum) = map.getOrElse(sum, 0) + 1
    }
    count
  }
  
  // 2. Continuous Subarray Sum
  def checkSubarraySum(nums: Array[Int], k: Int): Boolean = {
    val map = mutable.Map(0 -> -1)
    var sum = 0
    
    for (i <- nums.indices) {
      sum += nums(i)
      val mod = sum % k
      if (map.contains(mod)) {
        if (i - map(mod) > 1) return true
      } else {
        map(mod) = i
      }
    }
    false
  }
  
  // 3. Maximum Size Subarray Sum Equals k
  def maxSubArrayLen(nums: Array[Int], k: Int): Int = {
    val map = mutable.Map(0 -> -1)
    var sum = 0
    var maxLen = 0
    
    for (i <- nums.indices) {
      sum += nums(i)
      if (map.contains(sum - k)) {
        maxLen = math.max(maxLen, i - map(sum - k))
      }
      if (!map.contains(sum)) {
        map(sum) = i
      }
    }
    maxLen
  }
  
  // 4. Binary Subarrays With Sum
  def numSubarraysWithSum(nums: Array[Int], goal: Int): Int = {
    val map = mutable.Map(0 -> 1)
    var sum = 0
    var count = 0
    
    for (num <- nums) {
      sum += num
      count += map.getOrElse(sum - goal, 0)
      map(sum) = map.getOrElse(sum, 0) + 1
    }
    count
  }
  
  // 5. Contiguous Array
  def findMaxLength(nums: Array[Int]): Int = {
    val map = mutable.Map(0 -> -1)
    var sum = 0
    var maxLen = 0
    
    for (i <- nums.indices) {
      sum += (if (nums(i) == 1) 1 else -1)
      if (map.contains(sum)) {
        maxLen = math.max(maxLen, i - map(sum))
      } else {
        map(sum) = i
      }
    }
    maxLen
  }
  
  // 6. Range Sum Query - Immutable
  class NumArray(nums: Array[Int]) {
    private val prefixSum = new Array[Int](nums.length + 1)
    for (i <- nums.indices) {
      prefixSum(i + 1) = prefixSum(i) + nums(i)
    }
    
    def sumRange(left: Int, right: Int): Int = {
      prefixSum(right + 1) - prefixSum(left)
    }
  }
  
  // 7. Product of Array Except Self
  def productExceptSelf(nums: Array[Int]): Array[Int] = {
    val result = new Array[Int](nums.length)
    result(0) = 1
    
    for (i <- 1 until nums.length) {
      result(i) = result(i - 1) * nums(i - 1)
    }
    
    var right = 1
    for (i <- nums.length - 1 to 0 by -1) {
      result(i) *= right
      right *= nums(i)
    }
    result
  }
  
  // 8. Subarray Sums Divisible by K
  def subarraysDivByK(nums: Array[Int], k: Int): Int = {
    val map = mutable.Map(0 -> 1)
    var sum = 0
    var count = 0
    
    for (num <- nums) {
      sum += num
      val mod = ((sum % k) + k) % k
      count += map.getOrElse(mod, 0)
      map(mod) = map.getOrElse(mod, 0) + 1
    }
    count
  }
  
  // 9. Path Sum III
  def pathSum(root: TreeNode, targetSum: Int): Int = {
    val map = mutable.Map(0L -> 1)
    
    def dfs(node: TreeNode, currSum: Long): Int = {
      if (node == null) return 0
      
      val newSum = currSum + node.`val`
      val count = map.getOrElse(newSum - targetSum, 0)
      
      map(newSum) = map.getOrElse(newSum, 0) + 1
      val result = count + dfs(node.left, newSum) + dfs(node.right, newSum)
      map(newSum) = map(newSum) - 1
      
      result
    }
    
    dfs(root, 0)
  }
  
  // 10. Minimum Operations to Reduce X to Zero
  def minOperations(nums: Array[Int], x: Int): Int = {
    val target = nums.sum - x
    if (target < 0) return -1
    if (target == 0) return nums.length
    
    val map = mutable.Map(0 -> -1)
    var sum = 0
    var maxLen = -1
    
    for (i <- nums.indices) {
      sum += nums(i)
      if (map.contains(sum - target)) {
        maxLen = math.max(maxLen, i - map(sum - target))
      }
      if (!map.contains(sum)) {
        map(sum) = i
      }
    }
    
    if (maxLen == -1) -1 else nums.length - maxLen
  }
  
  class TreeNode(var `val`: Int = 0) {
    var left: TreeNode = null
    var right: TreeNode = null
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Prefix Sum Examples ===")
    
    // Test 1: Subarray Sum Equals K
    println(s"Subarray Sum K([1,1,1], 2): ${subarraySum(Array(1, 1, 1), 2)}")
    
    // Test 2: Continuous Subarray Sum
    println(s"Check Subarray Sum([23,2,4,6,7], 6): ${checkSubarraySum(Array(23, 2, 4, 6, 7), 6)}")
    
    // Test 3: Maximum Size Subarray Sum Equals k
    println(s"Max SubArray Len([1,-1,5,-2,3], 3): ${maxSubArrayLen(Array(1, -1, 5, -2, 3), 3)}")
    
    // Test 4: Binary Subarrays With Sum
    println(s"Num Subarrays With Sum([1,0,1,0,1], 2): ${numSubarraysWithSum(Array(1, 0, 1, 0, 1), 2)}")
    
    // Test 5: Contiguous Array
    println(s"Find Max Length([0,1]): ${findMaxLength(Array(0, 1))}")
    
    // Test 6: Range Sum Query
    val numArray = new NumArray(Array(-2, 0, 3, -5, 2, -1))
    println(s"Sum Range(0, 2): ${numArray.sumRange(0, 2)}")
    
    // Test 7: Product Except Self
    println(s"Product Except Self([1,2,3,4]): ${productExceptSelf(Array(1, 2, 3, 4)).mkString("[", ",", "]")}")
    
    // Test 8: Subarrays Divisible by K
    println(s"Subarrays Div By K([4,5,0,-2,-3,1], 5): ${subarraysDivByK(Array(4, 5, 0, -2, -3, 1), 5)}")
    
    // Test 9: Path Sum III (mock test)
    println("Path Sum III: Requires tree structure")
    
    // Test 10: Minimum Operations
    println(s"Min Operations([1,1,4,2,3], 5): ${minOperations(Array(1, 1, 4, 2, 3), 5)}")
  }
}