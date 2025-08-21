import scala.collection.mutable

object ScalaFastSlowPointersExamples {
  
  case class ListNode(var x: Int = 0, var next: ListNode = null)
  
  // 1. Linked List Cycle
  def hasCycle(head: ListNode): Boolean = {
    var slow = head
    var fast = head
    while (fast != null && fast.next != null) {
      slow = slow.next
      fast = fast.next.next
      if (slow == fast) return true
    }
    false
  }
  
  // 2. Linked List Cycle II
  def detectCycle(head: ListNode): ListNode = {
    var slow = head
    var fast = head
    while (fast != null && fast.next != null) {
      slow = slow.next
      fast = fast.next.next
      if (slow == fast) {
        slow = head
        while (slow != fast) {
          slow = slow.next
          fast = fast.next
        }
        return slow
      }
    }
    null
  }
  
  // 3. Middle of the Linked List
  def middleNode(head: ListNode): ListNode = {
    var slow = head
    var fast = head
    while (fast != null && fast.next != null) {
      slow = slow.next
      fast = fast.next.next
    }
    slow
  }
  
  // 4. Happy Number
  def isHappy(n: Int): Boolean = {
    def getNext(num: Int): Int = {
      var sum = 0
      var temp = num
      while (temp > 0) {
        val digit = temp % 10
        sum += digit * digit
        temp /= 10
      }
      sum
    }
    
    var slow = n
    var fast = n
    do {
      slow = getNext(slow)
      fast = getNext(getNext(fast))
    } while (slow != fast)
    
    slow == 1
  }
  
  // 5. Find the Duplicate Number
  def findDuplicate(nums: Array[Int]): Int = {
    var slow = nums(0)
    var fast = nums(0)
    do {
      slow = nums(slow)
      fast = nums(nums(fast))
    } while (slow != fast)
    
    slow = nums(0)
    while (slow != fast) {
      slow = nums(slow)
      fast = nums(fast)
    }
    slow
  }
  
  // 6. Palindrome Linked List
  def isPalindrome(head: ListNode): Boolean = {
    def reverse(node: ListNode): ListNode = {
      var prev: ListNode = null
      var curr = node
      while (curr != null) {
        val next = curr.next
        curr.next = prev
        prev = curr
        curr = next
      }
      prev
    }
    
    var slow = head
    var fast = head
    while (fast != null && fast.next != null) {
      slow = slow.next
      fast = fast.next.next
    }
    
    var secondHalf = reverse(slow)
    var firstHalf = head
    
    while (secondHalf != null) {
      if (firstHalf.x != secondHalf.x) return false
      firstHalf = firstHalf.next
      secondHalf = secondHalf.next
    }
    true
  }
  
  // 7. Remove Nth Node From End of List
  def removeNthFromEnd(head: ListNode, n: Int): ListNode = {
    val dummy = ListNode(0)
    dummy.next = head
    var slow = dummy
    var fast = dummy
    
    for (_ <- 0 to n) {
      fast = fast.next
    }
    
    while (fast != null) {
      slow = slow.next
      fast = fast.next
    }
    
    slow.next = slow.next.next
    dummy.next
  }
  
  // 8. Intersection of Two Linked Lists
  def getIntersectionNode(headA: ListNode, headB: ListNode): ListNode = {
    var a = headA
    var b = headB
    while (a != b) {
      a = if (a == null) headB else a.next
      b = if (b == null) headA else b.next
    }
    a
  }
  
  // 9. Circular Array Loop
  def circularArrayLoop(nums: Array[Int]): Boolean = {
    def getNextIndex(i: Int, forward: Boolean): Int = {
      val direction = nums(i) > 0
      if (direction != forward) return -1
      
      val next = (i + nums(i)) % nums.length
      val normalizedNext = if (next < 0) next + nums.length else next
      
      if (normalizedNext == i) -1 else normalizedNext
    }
    
    for (i <- nums.indices) {
      if (nums(i) == 0) continue
      
      var slow = i
      var fast = i
      val forward = nums(i) > 0
      
      do {
        slow = getNextIndex(slow, forward)
        fast = getNextIndex(fast, forward)
        if (fast != -1) fast = getNextIndex(fast, forward)
      } while (slow != -1 && fast != -1 && slow != fast)
      
      if (slow != -1 && slow == fast) return true
    }
    false
  }
  
  // 10. Reorder List
  def reorderList(head: ListNode): Unit = {
    if (head == null || head.next == null) return
    
    def reverse(node: ListNode): ListNode = {
      var prev: ListNode = null
      var curr = node
      while (curr != null) {
        val next = curr.next
        curr.next = prev
        prev = curr
        curr = next
      }
      prev
    }
    
    var slow = head
    var fast = head
    while (fast.next != null && fast.next.next != null) {
      slow = slow.next
      fast = fast.next.next
    }
    
    val secondHalf = reverse(slow.next)
    slow.next = null
    
    var first = head
    var second = secondHalf
    while (second != null) {
      val temp1 = first.next
      val temp2 = second.next
      first.next = second
      second.next = temp1
      first = temp1
      second = temp2
    }
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Fast/Slow Pointers Examples ===")
    
    // Test 1: Has Cycle
    val cycle = ListNode(3)
    cycle.next = ListNode(2)
    cycle.next.next = ListNode(0)
    cycle.next.next.next = ListNode(-4)
    cycle.next.next.next.next = cycle.next
    println(s"Has Cycle: ${hasCycle(cycle)}")
    
    // Test 2: Detect Cycle
    val cycleStart = detectCycle(cycle)
    println(s"Cycle Start: ${if (cycleStart != null) cycleStart.x else "null"}")
    
    // Test 3: Middle Node
    val list = ListNode(1)
    list.next = ListNode(2)
    list.next.next = ListNode(3)
    list.next.next.next = ListNode(4)
    list.next.next.next.next = ListNode(5)
    println(s"Middle Node: ${middleNode(list).x}")
    
    // Test 4: Happy Number
    println(s"Is Happy(19): ${isHappy(19)}")
    
    // Test 5: Find Duplicate
    println(s"Find Duplicate([1,3,4,2,2]): ${findDuplicate(Array(1,3,4,2,2))}")
    
    // Test 6: Palindrome Linked List
    val palindrome = ListNode(1)
    palindrome.next = ListNode(2)
    palindrome.next.next = ListNode(2)
    palindrome.next.next.next = ListNode(1)
    println(s"Is Palindrome: ${isPalindrome(palindrome)}")
    
    // Test 7: Remove Nth From End
    val toRemove = ListNode(1)
    toRemove.next = ListNode(2)
    toRemove.next.next = ListNode(3)
    toRemove.next.next.next = ListNode(4)
    toRemove.next.next.next.next = ListNode(5)
    val removed = removeNthFromEnd(toRemove, 2)
    println("Remove Nth From End completed")
    
    // Test 8: Intersection
    val common = ListNode(8)
    common.next = ListNode(4)
    common.next.next = ListNode(5)
    val listA = ListNode(4)
    listA.next = ListNode(1)
    listA.next.next = common
    val listB = ListNode(5)
    listB.next = ListNode(6)
    listB.next.next = ListNode(1)
    listB.next.next.next = common
    val intersection = getIntersectionNode(listA, listB)
    println(s"Intersection: ${if (intersection != null) intersection.x else "null"}")
    
    // Test 9: Circular Array Loop
    println(s"Circular Array Loop([2,-1,1,2,2]): ${circularArrayLoop(Array(2,-1,1,2,2))}")
    
    // Test 10: Reorder List
    val reorder = ListNode(1)
    reorder.next = ListNode(2)
    reorder.next.next = ListNode(3)
    reorder.next.next.next = ListNode(4)
    reorderList(reorder)
    println("Reorder List completed")
  }
}