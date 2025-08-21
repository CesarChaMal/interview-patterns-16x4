import scala.collection.mutable

object ScalaBacktrackingExamples {
  
  // 1. Permutations
  def permute(nums: Array[Int]): List[List[Int]] = {
    val result = mutable.ListBuffer[List[Int]]()
    def backtrack(path: List[Int], used: Array[Boolean]): Unit = {
      if (path.length == nums.length) result += path
      else for (i <- nums.indices if !used(i)) {
        used(i) = true
        backtrack(path :+ nums(i), used)
        used(i) = false
      }
    }
    backtrack(List(), Array.fill(nums.length)(false))
    result.toList
  }
  
  // 2. Combinations
  def combine(n: Int, k: Int): List[List[Int]] = {
    val result = mutable.ListBuffer[List[Int]]()
    def backtrack(start: Int, path: List[Int]): Unit = {
      if (path.length == k) result += path
      else for (i <- start to n) backtrack(i + 1, path :+ i)
    }
    backtrack(1, List())
    result.toList
  }
  
  // 3. Subsets
  def subsets(nums: Array[Int]): List[List[Int]] = {
    val result = mutable.ListBuffer[List[Int]]()
    def backtrack(start: Int, path: List[Int]): Unit = {
      result += path
      for (i <- start until nums.length) backtrack(i + 1, path :+ nums(i))
    }
    backtrack(0, List())
    result.toList
  }
  
  // 4. N-Queens
  def solveNQueens(n: Int): List[List[String]] = {
    val result = mutable.ListBuffer[List[String]]()
    def isValid(board: Array[Int], row: Int, col: Int): Boolean = {
      for (i <- 0 until row) {
        if (board(i) == col || math.abs(board(i) - col) == math.abs(i - row)) return false
      }
      true
    }
    def backtrack(board: Array[Int], row: Int): Unit = {
      if (row == n) {
        result += board.map(col => "." * col + "Q" + "." * (n - col - 1)).toList
      } else {
        for (col <- 0 until n if isValid(board, row, col)) {
          board(row) = col
          backtrack(board, row + 1)
        }
      }
    }
    backtrack(Array.fill(n)(-1), 0)
    result.toList
  }
  
  // 5. Generate Parentheses
  def generateParenthesis(n: Int): List[String] = {
    val result = mutable.ListBuffer[String]()
    def backtrack(path: String, open: Int, close: Int): Unit = {
      if (path.length == 2 * n) result += path
      else {
        if (open < n) backtrack(path + "(", open + 1, close)
        if (close < open) backtrack(path + ")", open, close + 1)
      }
    }
    backtrack("", 0, 0)
    result.toList
  }
  
  // 6. Word Search
  def exist(board: Array[Array[Char]], word: String): Boolean = {
    def backtrack(i: Int, j: Int, k: Int): Boolean = {
      if (k == word.length) return true
      if (i < 0 || i >= board.length || j < 0 || j >= board(0).length || board(i)(j) != word(k)) return false
      val temp = board(i)(j)
      board(i)(j) = '#'
      val found = backtrack(i+1,j,k+1) || backtrack(i-1,j,k+1) || backtrack(i,j+1,k+1) || backtrack(i,j-1,k+1)
      board(i)(j) = temp
      found
    }
    for (i <- board.indices; j <- board(0).indices if backtrack(i, j, 0)) return true
    false
  }
  
  // 7. Palindrome Partitioning
  def partition(s: String): List[List[String]] = {
    val result = mutable.ListBuffer[List[String]]()
    def isPalindrome(str: String): Boolean = str == str.reverse
    def backtrack(start: Int, path: List[String]): Unit = {
      if (start == s.length) result += path
      else for (end <- start + 1 to s.length) {
        val substr = s.substring(start, end)
        if (isPalindrome(substr)) backtrack(end, path :+ substr)
      }
    }
    backtrack(0, List())
    result.toList
  }
  
  // 8. Letter Combinations of Phone Number
  def letterCombinations(digits: String): List[String] = {
    if (digits.isEmpty) return List()
    val mapping = Map('2'->"abc",'3'->"def",'4'->"ghi",'5'->"jkl",'6'->"mno",'7'->"pqrs",'8'->"tuv",'9'->"wxyz")
    val result = mutable.ListBuffer[String]()
    def backtrack(index: Int, path: String): Unit = {
      if (index == digits.length) result += path
      else for (c <- mapping(digits(index))) backtrack(index + 1, path + c)
    }
    backtrack(0, "")
    result.toList
  }
  
  // 9. Sudoku Solver
  def solveSudoku(board: Array[Array[Char]]): Unit = {
    def isValid(row: Int, col: Int, c: Char): Boolean = {
      for (i <- 0 until 9) {
        if (board(i)(col) == c || board(row)(i) == c || board(3*(row/3)+i/3)(3*(col/3)+i%3) == c) return false
      }
      true
    }
    def backtrack(): Boolean = {
      for (i <- 0 until 9; j <- 0 until 9 if board(i)(j) == '.') {
        for (c <- '1' to '9' if isValid(i, j, c)) {
          board(i)(j) = c
          if (backtrack()) return true
          board(i)(j) = '.'
        }
        return false
      }
      true
    }
    backtrack()
  }
  
  // 10. Combination Sum
  def combinationSum(candidates: Array[Int], target: Int): List[List[Int]] = {
    val result = mutable.ListBuffer[List[Int]]()
    def backtrack(start: Int, path: List[Int], sum: Int): Unit = {
      if (sum == target) result += path
      else if (sum < target) {
        for (i <- start until candidates.length) {
          backtrack(i, path :+ candidates(i), sum + candidates(i))
        }
      }
    }
    backtrack(0, List(), 0)
    result.toList
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Backtracking Examples ===")
    
    // Test 1: Permutations
    println(s"Permutations([1,2,3]): ${permute(Array(1,2,3))}")
    
    // Test 2: Combinations
    println(s"Combinations(4,2): ${combine(4,2)}")
    
    // Test 3: Subsets
    println(s"Subsets([1,2,3]): ${subsets(Array(1,2,3))}")
    
    // Test 4: N-Queens
    println(s"N-Queens(4): ${solveNQueens(4).size} solutions")
    
    // Test 5: Generate Parentheses
    println(s"Generate Parentheses(3): ${generateParenthesis(3)}")
    
    // Test 6: Word Search
    val board = Array(Array('A','B','C','E'),Array('S','F','C','S'),Array('A','D','E','E'))
    println(s"Word Search 'ABCCED': ${exist(board, "ABCCED")}")
    
    // Test 7: Palindrome Partitioning
    println(s"Palindrome Partition 'aab': ${partition("aab")}")
    
    // Test 8: Letter Combinations
    println(s"Letter Combinations '23': ${letterCombinations("23")}")
    
    // Test 10: Combination Sum
    println(s"Combination Sum([2,3,6,7], 7): ${combinationSum(Array(2,3,6,7), 7)}")
  }
}