class ListNode(var x: Int = 0) {
  var next: ListNode = null
}

object ScalaLinkedListReversalExamples {
  
  // 1. Reverse Linked List
  def reverseList(head: ListNode): ListNode = {
    var prev: ListNode = null
    var curr = head
    while (curr != null) {
      val next = curr.next
      curr.next = prev
      prev = curr
      curr = next
    }
    prev
  }
  
  // 2. Reverse Linked List II
  def reverseBetween(head: ListNode, left: Int, right: Int): ListNode = {
    val dummy = new ListNode(0)
    dummy.next = head
    var prev = dummy
    for (_ <- 1 until left) prev = prev.next
    
    val start = prev.next
    val then = start.next
    
    for (_ <- 0 until right - left) {
      start.next = then.next
      then.next = prev.next
      prev.next = then
      then = start.next
    }
    dummy.next
  }
  
  // 3. Reverse Nodes in k-Group
  def reverseKGroup(head: ListNode, k: Int): ListNode = {
    var curr = head
    var count = 0
    while (curr != null && count != k) {
      curr = curr.next
      count += 1
    }
    
    if (count == k) {
      curr = reverseKGroup(curr, k)
      while (count > 0) {
        val tmp = head.next
        head.next = curr
        curr = head
        head = tmp
        count -= 1
      }
      head = curr
    }
    head
  }
  
  // 4. Swap Nodes in Pairs
  def swapPairs(head: ListNode): ListNode = {
    val dummy = new ListNode(0)
    dummy.next = head
    var prev = dummy
    
    while (prev.next != null && prev.next.next != null) {
      val first = prev.next
      val second = prev.next.next
      
      prev.next = second
      first.next = second.next
      second.next = first
      prev = first
    }
    dummy.next
  }
  
  // 5. Rotate List
  def rotateRight(head: ListNode, k: Int): ListNode = {
    if (head == null || head.next == null || k == 0) return head
    
    var tail = head
    var length = 1
    while (tail.next != null) {
      tail = tail.next
      length += 1
    }
    
    tail.next = head
    val stepsToNewHead = length - k % length
    var newTail = head
    for (_ <- 1 until stepsToNewHead) newTail = newTail.next
    
    val newHead = newTail.next
    newTail.next = null
    newHead
  }
  
  // 6. Odd Even Linked List
  def oddEvenList(head: ListNode): ListNode = {
    if (head == null) return null
    
    val odd = head
    val even = head.next
    val evenHead = even
    
    while (even != null && even.next != null) {
      odd.next = even.next
      odd = odd.next
      even.next = odd.next
      even = even.next
    }
    odd.next = evenHead
    head
  }
  
  // 7. Partition List
  def partition(head: ListNode, x: Int): ListNode = {
    val beforeHead = new ListNode(0)
    val afterHead = new ListNode(0)
    var before = beforeHead
    var after = afterHead
    
    while (head != null) {
      if (head.x < x) {
        before.next = head
        before = before.next
      } else {
        after.next = head
        after = after.next
      }
      head = head.next
    }
    
    after.next = null
    before.next = afterHead.next
    beforeHead.next
  }
  
  // 8. Add Two Numbers
  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    val dummy = new ListNode(0)
    var curr = dummy
    var carry = 0
    
    while (l1 != null || l2 != null || carry != 0) {
      val sum = (if (l1 != null) l1.x else 0) + (if (l2 != null) l2.x else 0) + carry
      carry = sum / 10
      curr.next = new ListNode(sum % 10)
      curr = curr.next
      
      if (l1 != null) l1 = l1.next
      if (l2 != null) l2 = l2.next
    }
    dummy.next
  }
  
  // 9. Remove Duplicates from Sorted List II
  def deleteDuplicates(head: ListNode): ListNode = {
    val dummy = new ListNode(0)
    dummy.next = head
    var prev = dummy
    
    while (head != null) {
      if (head.next != null && head.x == head.next.x) {
        while (head.next != null && head.x == head.next.x) {
          head = head.next
        }
        prev.next = head.next
      } else {
        prev = prev.next
      }
      head = head.next
    }
    dummy.next
  }
  
  // 10. Reverse Nodes in Even Length Groups
  def reverseEvenLengthGroups(head: ListNode): ListNode = {
    var prev = head
    var groupLen = 2
    
    while (prev.next != null) {
      var node = prev
      var nodeCount = 0
      
      for (_ <- 0 until groupLen if node.next != null) {
        node = node.next
        nodeCount += 1
      }
      
      if (nodeCount % 2 == 0) {
        val reversedHead = reverseGroup(prev.next, nodeCount)
        val originalNext = prev.next
        prev.next = reversedHead
        prev = originalNext
      } else {
        prev = node
      }
      groupLen += 1
    }
    head
  }
  
  private def reverseGroup(head: ListNode, k: Int): ListNode = {
    var prev: ListNode = null
    var curr = head
    for (_ <- 0 until k) {
      val next = curr.next
      curr.next = prev
      prev = curr
      curr = next
    }
    head.next = curr
    prev
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Linked List Reversal Examples ===")
    
    // Test 1: Reverse Linked List
    val list1 = createList(Array(1, 2, 3, 4, 5))
    val reversed = reverseList(list1)
    println(s"Reverse List: ${listToString(reversed)}")
    
    // Test 2: Reverse Between
    val list2 = createList(Array(1, 2, 3, 4, 5))
    val reversedBetween = reverseBetween(list2, 2, 4)
    println(s"Reverse Between(2,4): ${listToString(reversedBetween)}")
    
    // Test 3: Reverse K Group
    val list3 = createList(Array(1, 2, 3, 4, 5))
    val reversedK = reverseKGroup(list3, 2)
    println(s"Reverse K Group(2): ${listToString(reversedK)}")
    
    // Test 4: Swap Pairs
    val list4 = createList(Array(1, 2, 3, 4))
    val swapped = swapPairs(list4)
    println(s"Swap Pairs: ${listToString(swapped)}")
    
    // Test 5: Rotate Right
    val list5 = createList(Array(1, 2, 3, 4, 5))
    val rotated = rotateRight(list5, 2)
    println(s"Rotate Right(2): ${listToString(rotated)}")
  }
  
  private def createList(arr: Array[Int]): ListNode = {
    val dummy = new ListNode(0)
    var curr = dummy
    for (num <- arr) {
      curr.next = new ListNode(num)
      curr = curr.next
    }
    dummy.next
  }
  
  private def listToString(head: ListNode): String = {
    val result = scala.collection.mutable.ListBuffer[Int]()
    var curr = head
    while (curr != null) {
      result += curr.x
      curr = curr.next
    }
    result.mkString("[", ",", "]")
  }
}