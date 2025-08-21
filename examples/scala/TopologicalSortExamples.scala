import scala.collection.mutable

object ScalaTopologicalSortExamples {
  
  // 1. Course Schedule
  def canFinish(numCourses: Int, prerequisites: Array[Array[Int]]): Boolean = {
    val indegree = new Array[Int](numCourses)
    val graph = Array.fill(numCourses)(mutable.ListBuffer[Int]())
    
    for (Array(course, pre) <- prerequisites) {
      graph(pre) += course
      indegree(course) += 1
    }
    
    val queue = mutable.Queue[Int]()
    for (i <- indegree.indices if indegree(i) == 0) queue.enqueue(i)
    
    var count = 0
    while (queue.nonEmpty) {
      val course = queue.dequeue()
      count += 1
      for (next <- graph(course)) {
        indegree(next) -= 1
        if (indegree(next) == 0) queue.enqueue(next)
      }
    }
    
    count == numCourses
  }
  
  // 2. Course Schedule II
  def findOrder(numCourses: Int, prerequisites: Array[Array[Int]]): Array[Int] = {
    val indegree = new Array[Int](numCourses)
    val graph = Array.fill(numCourses)(mutable.ListBuffer[Int]())
    
    for (Array(course, pre) <- prerequisites) {
      graph(pre) += course
      indegree(course) += 1
    }
    
    val queue = mutable.Queue[Int]()
    for (i <- indegree.indices if indegree(i) == 0) queue.enqueue(i)
    
    val result = mutable.ArrayBuffer[Int]()
    while (queue.nonEmpty) {
      val course = queue.dequeue()
      result += course
      for (next <- graph(course)) {
        indegree(next) -= 1
        if (indegree(next) == 0) queue.enqueue(next)
      }
    }
    
    if (result.length == numCourses) result.toArray else Array.empty
  }
  
  // 3. Alien Dictionary
  def alienOrder(words: Array[String]): String = {
    val graph = mutable.Map[Char, mutable.Set[Char]]()
    val indegree = mutable.Map[Char, Int]()
    
    for (word <- words; char <- word) {
      indegree(char) = 0
      graph(char) = mutable.Set[Char]()
    }
    
    for (i <- words.indices.dropRight(1)) {
      val w1 = words(i)
      val w2 = words(i + 1)
      if (w1.length > w2.length && w1.startsWith(w2)) return ""
      
      for (j <- 0 until math.min(w1.length, w2.length)) {
        val c1 = w1(j)
        val c2 = w2(j)
        if (c1 != c2) {
          if (!graph(c1).contains(c2)) {
            graph(c1) += c2
            indegree(c2) += 1
          }
          break
        }
      }
    }
    
    val queue = mutable.Queue[Char]()
    for ((char, deg) <- indegree if deg == 0) queue.enqueue(char)
    
    val result = mutable.StringBuilder()
    while (queue.nonEmpty) {
      val char = queue.dequeue()
      result += char
      for (next <- graph(char)) {
        indegree(next) -= 1
        if (indegree(next) == 0) queue.enqueue(next)
      }
    }
    
    if (result.length == indegree.size) result.toString else ""
  }
  
  // 4. Minimum Height Trees
  def findMinHeightTrees(n: Int, edges: Array[Array[Int]]): List[Int] = {
    if (n == 1) return List(0)
    
    val graph = Array.fill(n)(mutable.Set[Int]())
    for (Array(a, b) <- edges) {
      graph(a) += b
      graph(b) += a
    }
    
    val leaves = mutable.Queue[Int]()
    for (i <- 0 until n if graph(i).size == 1) leaves.enqueue(i)
    
    var remaining = n
    while (remaining > 2) {
      val leafCount = leaves.size
      remaining -= leafCount
      for (_ <- 0 until leafCount) {
        val leaf = leaves.dequeue()
        val neighbor = graph(leaf).head
        graph(neighbor) -= leaf
        if (graph(neighbor).size == 1) leaves.enqueue(neighbor)
      }
    }
    
    leaves.toList
  }
  
  // 5. Sequence Reconstruction
  def sequenceReconstruction(nums: Array[Int], sequences: List[List[Int]]): Boolean = {
    val graph = mutable.Map[Int, mutable.Set[Int]]()
    val indegree = mutable.Map[Int, Int]()
    
    for (num <- nums) {
      graph(num) = mutable.Set[Int]()
      indegree(num) = 0
    }
    
    for (seq <- sequences; i <- seq.indices.dropRight(1)) {
      val from = seq(i)
      val to = seq(i + 1)
      if (!graph.contains(from) || !graph.contains(to)) return false
      if (!graph(from).contains(to)) {
        graph(from) += to
        indegree(to) += 1
      }
    }
    
    val queue = mutable.Queue[Int]()
    for ((num, deg) <- indegree if deg == 0) queue.enqueue(num)
    
    var idx = 0
    while (queue.nonEmpty) {
      if (queue.size > 1) return false
      val num = queue.dequeue()
      if (nums(idx) != num) return false
      idx += 1
      for (next <- graph(num)) {
        indegree(next) -= 1
        if (indegree(next) == 0) queue.enqueue(next)
      }
    }
    
    idx == nums.length
  }
  
  // 6. All Ancestors of a Node in DAG
  def getAncestors(n: Int, edges: Array[Array[Int]]): List[List[Int]] = {
    val graph = Array.fill(n)(mutable.ListBuffer[Int]())
    val indegree = new Array[Int](n)
    
    for (Array(a, b) <- edges) {
      graph(a) += b
      indegree(b) += 1
    }
    
    val ancestors = Array.fill(n)(mutable.Set[Int]())
    val queue = mutable.Queue[Int]()
    
    for (i <- 0 until n if indegree(i) == 0) queue.enqueue(i)
    
    while (queue.nonEmpty) {
      val node = queue.dequeue()
      for (child <- graph(node)) {
        ancestors(child) += node
        ancestors(child) ++= ancestors(node)
        indegree(child) -= 1
        if (indegree(child) == 0) queue.enqueue(child)
      }
    }
    
    ancestors.map(_.toList.sorted).toList
  }
  
  // 7. Parallel Courses
  def minimumSemesters(n: Int, relations: Array[Array[Int]]): Int = {
    val graph = Array.fill(n + 1)(mutable.ListBuffer[Int]())
    val indegree = new Array[Int](n + 1)
    
    for (Array(pre, course) <- relations) {
      graph(pre) += course
      indegree(course) += 1
    }
    
    val queue = mutable.Queue[Int]()
    for (i <- 1 to n if indegree(i) == 0) queue.enqueue(i)
    
    var semesters = 0
    var studied = 0
    
    while (queue.nonEmpty) {
      val size = queue.size
      semesters += 1
      for (_ <- 0 until size) {
        val course = queue.dequeue()
        studied += 1
        for (next <- graph(course)) {
          indegree(next) -= 1
          if (indegree(next) == 0) queue.enqueue(next)
        }
      }
    }
    
    if (studied == n) semesters else -1
  }
  
  // 8. Sort Items by Groups Respecting Dependencies
  def sortItems(n: Int, m: Int, group: Array[Int], beforeItems: List[List[Int]]): Array[Int] = {
    var groupId = m
    for (i <- group.indices if group(i) == -1) {
      group(i) = groupId
      groupId += 1
    }
    
    val itemGraph = Array.fill(n)(mutable.ListBuffer[Int]())
    val groupGraph = Array.fill(groupId)(mutable.ListBuffer[Int]())
    val itemIndegree = new Array[Int](n)
    val groupIndegree = new Array[Int](groupId)
    
    for (i <- beforeItems.indices; prev <- beforeItems(i)) {
      itemGraph(prev) += i
      itemIndegree(i) += 1
      
      if (group(prev) != group(i)) {
        groupGraph(group(prev)) += group(i)
        groupIndegree(group(i)) += 1
      }
    }
    
    def topologicalSort(graph: Array[mutable.ListBuffer[Int]], indegree: Array[Int]): List[Int] = {
      val queue = mutable.Queue[Int]()
      for (i <- indegree.indices if indegree(i) == 0) queue.enqueue(i)
      
      val result = mutable.ListBuffer[Int]()
      while (queue.nonEmpty) {
        val node = queue.dequeue()
        result += node
        for (next <- graph(node)) {
          indegree(next) -= 1
          if (indegree(next) == 0) queue.enqueue(next)
        }
      }
      
      if (result.length == graph.length) result.toList else List.empty
    }
    
    val itemOrder = topologicalSort(itemGraph, itemIndegree)
    val groupOrder = topologicalSort(groupGraph, groupIndegree)
    
    if (itemOrder.isEmpty || groupOrder.isEmpty) return Array.empty
    
    val groupToItems = mutable.Map[Int, mutable.ListBuffer[Int]]()
    for (item <- itemOrder) {
      groupToItems.getOrElseUpdate(group(item), mutable.ListBuffer[Int]()) += item
    }
    
    val result = mutable.ListBuffer[Int]()
    for (g <- groupOrder) {
      result ++= groupToItems.getOrElse(g, mutable.ListBuffer.empty)
    }
    
    result.toArray
  }
  
  // 9. Build a Matrix With Conditions
  def buildMatrix(k: Int, rowConditions: Array[Array[Int]], colConditions: Array[Array[Int]]): Array[Array[Int]] = {
    def topologicalSort(conditions: Array[Array[Int]]): List[Int] = {
      val graph = Array.fill(k + 1)(mutable.ListBuffer[Int]())
      val indegree = new Array[Int](k + 1)
      
      for (Array(a, b) <- conditions) {
        graph(a) += b
        indegree(b) += 1
      }
      
      val queue = mutable.Queue[Int]()
      for (i <- 1 to k if indegree(i) == 0) queue.enqueue(i)
      
      val result = mutable.ListBuffer[Int]()
      while (queue.nonEmpty) {
        val node = queue.dequeue()
        result += node
        for (next <- graph(node)) {
          indegree(next) -= 1
          if (indegree(next) == 0) queue.enqueue(next)
        }
      }
      
      if (result.length == k) result.toList else List.empty
    }
    
    val rowOrder = topologicalSort(rowConditions)
    val colOrder = topologicalSort(colConditions)
    
    if (rowOrder.isEmpty || colOrder.isEmpty) return Array.empty
    
    val rowPos = rowOrder.zipWithIndex.toMap
    val colPos = colOrder.zipWithIndex.toMap
    
    val result = Array.ofDim[Int](k, k)
    for (i <- 1 to k) {
      result(rowPos(i))(colPos(i)) = i
    }
    
    result
  }
  
  // 10. Find All Possible Recipes
  def findAllRecipes(recipes: Array[String], ingredients: List[List[String]], supplies: Array[String]): List[String] = {
    val supplySet = supplies.toSet
    val graph = mutable.Map[String, mutable.ListBuffer[String]]()
    val indegree = mutable.Map[String, Int]()
    
    for (i <- recipes.indices) {
      val recipe = recipes(i)
      indegree(recipe) = 0
      for (ingredient <- ingredients(i) if !supplySet.contains(ingredient)) {
        graph.getOrElseUpdate(ingredient, mutable.ListBuffer[String]()) += recipe
        indegree(recipe) += 1
      }
    }
    
    val queue = mutable.Queue[String]()
    for ((recipe, deg) <- indegree if deg == 0) queue.enqueue(recipe)
    
    val result = mutable.ListBuffer[String]()
    while (queue.nonEmpty) {
      val recipe = queue.dequeue()
      result += recipe
      for (next <- graph.getOrElse(recipe, mutable.ListBuffer.empty)) {
        indegree(next) -= 1
        if (indegree(next) == 0) queue.enqueue(next)
      }
    }
    
    result.toList
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Topological Sort Examples ===")
    
    // Test 1: Course Schedule
    println(s"Can Finish(2, [[1,0]]): ${canFinish(2, Array(Array(1,0)))}")
    
    // Test 2: Course Schedule II
    println(s"Find Order(2, [[1,0]]): ${findOrder(2, Array(Array(1,0))).mkString("[", ",", "]")}")
    
    // Test 3: Alien Dictionary
    println(s"Alien Order(['wrt','wrf','er','ett','rftt']): ${alienOrder(Array("wrt","wrf","er","ett","rftt"))}")
    
    // Test 4: Minimum Height Trees
    println(s"Min Height Trees(4, [[1,0],[1,2],[1,3]]): ${findMinHeightTrees(4, Array(Array(1,0),Array(1,2),Array(1,3)))}")
    
    // Test 5: Sequence Reconstruction
    println(s"Sequence Reconstruction([1,2,3], [[1,2],[1,3]]): ${sequenceReconstruction(Array(1,2,3), List(List(1,2),List(1,3)))}")
    
    // Test 6: All Ancestors
    println(s"Get Ancestors(8, [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]): ${getAncestors(8, Array(Array(0,3),Array(0,4),Array(1,3),Array(2,4),Array(2,7),Array(3,5),Array(3,6),Array(3,7),Array(4,6)))}")
    
    // Test 7: Parallel Courses
    println(s"Minimum Semesters(3, [[1,3],[2,3]]): ${minimumSemesters(3, Array(Array(1,3),Array(2,3)))}")
    
    // Test 8: Sort Items
    println("Sort Items: Complex test case")
    
    // Test 9: Build Matrix
    println("Build Matrix: Complex test case")
    
    // Test 10: Find All Recipes
    println(s"Find All Recipes: ${findAllRecipes(Array("bread"), List(List("yeast","flour")), Array("yeast","flour","corn"))}")
  }
}