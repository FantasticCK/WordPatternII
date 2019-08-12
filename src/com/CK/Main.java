package com.CK;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
//        String pattern = "abab";
//        String str = "redblueredblue";
//        String pattern = "ab";
//        String str = "aa";
        String pattern = "itwasthebestoftimes";
        String str = "ittwaastthhebesttoofttimesss";
        Solution solution = new Solution();
        System.out.println(solution.wordPatternMatch(pattern, str));
    }
}

class Solution {
    public boolean wordPatternMatch(String pattern, String str) {
        if (pattern.length() == 0 && str.length() == 0) return true;
        Map<Character, String> pToWord = new HashMap<>();
        Set<String> visited = new HashSet<>();
        return dfs(pattern, 0, str, 0, pToWord, visited);
    }

    private boolean dfs(String pattern, int p, String str, int s, Map<Character, String> pToWord, Set<String> visited) {
        int plen = pattern.length() - p, slen = str.length() - s;
        if ((plen == 0 && slen != 0) || (plen != 0 && slen == 0))
            return false;
        if (plen == 0)
            return true;

        // pToWord 有pChar的话， 先判断Str从s开始，是不是以pChar对应的value开头
        //如果不是，直接return false;
        char pChar = pattern.charAt(p);
        if (pToWord.containsKey(pChar)) {
            String valueS = pToWord.get(pChar);
            if (!str.startsWith(valueS, s))
                return false;
            return dfs(pattern, p + 1, str, s + valueS.length(), pToWord, visited);
        }

        // PTOWord 没有pChar的情况下，遍历p->Word 可能的搭配
        for (int i = s; i < str.length(); i++) {
            String subS = str.substring(s, i + 1);
            if (!visited.isEmpty() && visited.contains(subS))
                continue;
            pToWord.put(pChar, subS);
            visited.add(subS);
            if (dfs(pattern, p + 1, str, i + 1, pToWord, visited))
                return true;
            pToWord.remove(pChar, subS);
            visited.remove(subS);
        }
        return false;
    }
}


class Solution2 {

    public boolean wordPatternMatch(String pattern, String str) {
        Map<Character, String> map = new HashMap<>();
        Set<String> set = new HashSet<>();

        return isMatch(str, 0, pattern, 0, map, set);
    }

    boolean isMatch(String str, int i, String pat, int j, Map<Character, String> map, Set<String> set) {
        // base case
        if (i == str.length() && j == pat.length()) return true;
        if (i == str.length() || j == pat.length()) return false;


        char c = pat.charAt(j);


        if (map.containsKey(c)) {
            String s = map.get(c);

            if (!str.startsWith(s, i)) {
                return false;
            }

            return isMatch(str, i + s.length(), pat, j + 1, map, set);
        }


        for (int k = i; k < str.length(); k++) {
            String p = str.substring(i, k + 1);

            if (set.contains(p)) {
                continue;
            }


            map.put(c, p);
            set.add(p);

            if (isMatch(str, k + 1, pat, j + 1, map, set)) {
                return true;
            }

            // backtracking
            map.remove(c);
            set.remove(p);
        }

        // we've tried our best but still no luck
        return false;
    }

}