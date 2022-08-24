package com.herren.gongassistance.quiz;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NumberToString {

    public List<String> numberToString(int[] input) {

        int startNum = input[0];
        int endNum = input[0];
        int nowNum = input[0];
        List<String> result = new ArrayList<>();

//        int[] input = {4, 5, 6, 8, 14, 15, 16};

        for(int i =1; i < input.length; i++) {
            if(++nowNum == input[i]) {
                endNum = nowNum;
            } else {
                result.add(getResultString(startNum, endNum));
                startNum = input[i];
                endNum = input[i];
                nowNum = input[i];
            }

            if(i == (input.length -1)) {
                result.add(getResultString(startNum, endNum));
            }
        }
        return result;
    }

    private String getResultString(int startNum, int endNum) {
        if(startNum == endNum) {
            return String.valueOf(startNum);
        } else {
            return String.join("~", String.valueOf(startNum), String.valueOf(endNum));
        }
    }
}
