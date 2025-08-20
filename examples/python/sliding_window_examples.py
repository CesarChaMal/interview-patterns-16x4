"""
Sliding Window Pattern - 10 Essential Problems
Time Complexity: O(n) for most problems
Space Complexity: O(1) to O(k) depending on problem
"""

from typing import List, Dict
from collections import defaultdict, Counter


class SlidingWindowExamples:
    
    # 1. LC 643 - Maximum Average Subarray I (Easy)
    # Find max average of any subarray of length k
    def find_max_average(self, nums: List[int], k: int) -> float:
        window_sum = sum(nums[:k])
        max_sum = window_sum
        
        for i in range(k, len(nums)):
            window_sum += nums[i] - nums[i - k]
            max_sum = max(max_sum, window_sum)
        
        return max_sum / k
    
    # 2. LC 209 - Minimum Size Subarray Sum (Medium)
    # Find minimum length subarray with sum >= target
    def min_subarray_len(self, target: int, nums: List[int]) -> int:
        left = window_sum = 0
        min_len = float('inf')
        
        for right in range(len(nums)):
            window_sum += nums[right]
            
            while window_sum >= target:
                min_len = min(min_len, right - left + 1)
                window_sum -= nums[left]
                left += 1
        
        return min_len if min_len != float('inf') else 0
    
    # 3. LC 3 - Longest Substring Without Repeating Characters (Medium)
    # Find length of longest substring without repeating characters
    def length_of_longest_substring(self, s: str) -> int:
        char_set = set()
        left = max_len = 0
        
        for right in range(len(s)):
            while s[right] in char_set:
                char_set.remove(s[left])
                left += 1
            
            char_set.add(s[right])
            max_len = max(max_len, right - left + 1)
        
        return max_len
    
    # 4. LC 76 - Minimum Window Substring (Hard)
    # Find minimum window substring containing all characters of t
    def min_window(self, s: str, t: str) -> str:
        if not s or not t:
            return ""
        
        dict_t = Counter(t)
        required = len(dict_t)
        left = right = formed = 0
        window_counts = defaultdict(int)
        ans = float('inf'), None, None
        
        while right < len(s):
            char = s[right]
            window_counts[char] += 1
            
            if char in dict_t and window_counts[char] == dict_t[char]:
                formed += 1
            
            while left <= right and formed == required:
                if right - left + 1 < ans[0]:
                    ans = (right - left + 1, left, right)
                
                char = s[left]
                window_counts[char] -= 1
                if char in dict_t and window_counts[char] < dict_t[char]:
                    formed -= 1
                
                left += 1
            
            right += 1
        
        return "" if ans[0] == float('inf') else s[ans[1]:ans[2] + 1]
    
    # 5. LC 424 - Longest Repeating Character Replacement (Medium)
    # Find longest substring with same character after k replacements
    def character_replacement(self, s: str, k: int) -> int:
        char_count = defaultdict(int)
        left = max_len = max_count = 0
        
        for right in range(len(s)):
            char_count[s[right]] += 1
            max_count = max(max_count, char_count[s[right]])
            
            if right - left + 1 - max_count > k:
                char_count[s[left]] -= 1
                left += 1
            
            max_len = max(max_len, right - left + 1)
        
        return max_len
    
    # 6. LC 567 - Permutation in String (Medium)
    # Check if s2 contains permutation of s1
    def check_inclusion(self, s1: str, s2: str) -> bool:
        if len(s1) > len(s2):
            return False
        
        s1_count = Counter(s1)
        window_count = Counter()
        
        for i in range(len(s2)):
            window_count[s2[i]] += 1
            
            if i >= len(s1):
                if window_count[s2[i - len(s1)]] == 1:
                    del window_count[s2[i - len(s1)]]
                else:
                    window_count[s2[i - len(s1)]] -= 1
            
            if window_count == s1_count:
                return True
        
        return False
    
    # 7. LC 438 - Find All Anagrams in a String (Medium)
    # Find all start indices of anagrams of p in s
    def find_anagrams(self, s: str, p: str) -> List[int]:
        if len(p) > len(s):
            return []
        
        p_count = Counter(p)
        window_count = Counter()
        result = []
        
        for i in range(len(s)):
            window_count[s[i]] += 1
            
            if i >= len(p):
                if window_count[s[i - len(p)]] == 1:
                    del window_count[s[i - len(p)]]
                else:
                    window_count[s[i - len(p)]] -= 1
            
            if window_count == p_count:
                result.append(i - len(p) + 1)
        
        return result
    
    # 8. LC 1004 - Max Consecutive Ones III (Medium)
    # Find max consecutive 1s after flipping at most k zeros
    def longest_ones(self, nums: List[int], k: int) -> int:
        left = zero_count = max_len = 0
        
        for right in range(len(nums)):
            if nums[right] == 0:
                zero_count += 1
            
            while zero_count > k:
                if nums[left] == 0:
                    zero_count -= 1
                left += 1
            
            max_len = max(max_len, right - left + 1)
        
        return max_len
    
    # 9. LC 1456 - Maximum Number of Vowels in a Substring (Medium)
    # Find max vowels in any substring of length k
    def max_vowels(self, s: str, k: int) -> int:
        vowels = set('aeiou')
        current_vowels = sum(1 for c in s[:k] if c in vowels)
        max_vowels = current_vowels
        
        for i in range(k, len(s)):
            if s[i] in vowels:
                current_vowels += 1
            if s[i - k] in vowels:
                current_vowels -= 1
            
            max_vowels = max(max_vowels, current_vowels)
        
        return max_vowels
    
    # 10. LC 1208 - Get Equal Substrings Within Budget (Medium)
    # Find max length substring where cost <= maxCost
    def equal_substring(self, s: str, t: str, max_cost: int) -> int:
        left = current_cost = max_len = 0
        
        for right in range(len(s)):
            current_cost += abs(ord(s[right]) - ord(t[right]))
            
            while current_cost > max_cost:
                current_cost -= abs(ord(s[left]) - ord(t[left]))
                left += 1
            
            max_len = max(max_len, right - left + 1)
        
        return max_len


def test_sliding_window():
    """Test all sliding window examples"""
    sw = SlidingWindowExamples()
    
    # Test cases
    print("=== Sliding Window Examples ===")
    
    # 1. Max Average Subarray
    print(f"1. Max average: {sw.find_max_average([1,12,-5,-6,50,3], 4)}")  # 12.75
    
    # 2. Min Subarray Length
    print(f"2. Min subarray len: {sw.min_subarray_len(7, [2,3,1,2,4,3])}")  # 2
    
    # 3. Longest Substring
    print(f"3. Longest substring: {sw.length_of_longest_substring('abcabcbb')}")  # 3
    
    # 4. Min Window
    print(f"4. Min window: '{sw.min_window('ADOBECODEBANC', 'ABC')}'")  # "BANC"
    
    # 5. Character Replacement
    print(f"5. Character replacement: {sw.character_replacement('ABAB', 2)}")  # 4
    
    print("All sliding window examples working!")


if __name__ == "__main__":
    test_sliding_window()