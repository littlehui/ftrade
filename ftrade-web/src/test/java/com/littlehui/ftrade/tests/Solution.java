package com.littlehui.ftrade.tests;

/**
 * @Description TODO
 * @ClassName Solution
 * @Author littlehui
 * @Date 2020/3/29 01:30
 * @Version 1.0
 **/
public class Solution {

    public String reverseLeftWords(String s, int n) {
        if (n >= s.length()) {
            return  s;
        }
        char[] arrays = s.toCharArray();
        char[] newArrays = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (i < n) {
                newArrays[i + n] = arrays[i];
            } else {
                newArrays[i - n] = arrays[i];
            }
        }
        return new String(newArrays);
    }
}
