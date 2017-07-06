/*
 *
 *  *****************************************************************************
 *  * Copyright ( c ) 2016 Heren Tianjin Inc. All Rights Reserved.
 *  *
 *  * This software is the confidential and proprietary information of Heren Tianjin Inc
 *  * ("Confidential Information").  You shall not disclose such Confidential Information
 *  *  and shall use it only in accordance with the terms of the license agreement
 *  *  you entered into with Heren Tianjin or a Heren Tianjin authorized
 *  *  reseller (the "License Agreement").
 *  ****************************************************************************
 *
 */

package com.heren.turtle.server.utils;

import java.util.Arrays;

/**
 * com.heren.turtle.server.utils
 *
 * @author zhiwei
 * @create 2017-05-17 10:43.
 * @github {@https://github.com/loveqianqian}
 */
public class SplitUtils {

    public static String replaceWord(String[] words, String newWord, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i == count) {
                sb.append(newWord).append("^");
            } else {
                sb.append(words[i]).append("^");
            }
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    public static String replaceWord(String[] words, Object newWord, int count) {
        return replaceWord(words, String.valueOf(newWord), count);
    }

    public static String removeSomeOne(String words, int count) {
        String[] word = words.split("\\^");
        int wordsLength = word.length;
        if (count < wordsLength) {
            System.arraycopy(word, count + 1, word, count, wordsLength - 1 - count);
            String[] newWords = Arrays.copyOf(word, wordsLength - 1);
            StringBuilder sb = new StringBuilder();
            for (String newWord : newWords) {
                sb.append(newWord).append("^");
            }
            return sb.toString().substring(0, sb.length() - 1);
        } else {
            return words;
        }
    }

    public static void main(String[] args) {
        System.out.println(removeSomeOne("1^2^3^4^5^6", 5));
    }
}
