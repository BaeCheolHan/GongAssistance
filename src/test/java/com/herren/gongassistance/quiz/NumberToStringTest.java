package com.herren.gongassistance.quiz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberToStringTest {

    private final NumberToString sut = new NumberToString();

    @Test
    @DisplayName("문제에서 주어진 예시 1번 테스트")
    void test1() {
        int[] input = {4, 5, 6, 8, 14, 15, 16};

        List<String> result = sut.numberToString(input);

        Assertions.assertLinesMatch(Arrays.asList("4~6", "8", "14~16"), result);
    }

    @Test
    @DisplayName("문제에서 주어진 예시 2번 테스트")
    void test2() {
        int[] input = {13, 14, 15, 16, 21, 23, 24, 25, 100};

        List<String> result = sut.numberToString(input);

        Assertions.assertLinesMatch(Arrays.asList("13~16", "21", "23~25", "100"), result);

    }

    @Test
    @DisplayName("추가 테스트1")
    void test3() {
        int[] input = {1, 3, 5, 7, 9, 10};

        List<String> result = sut.numberToString(input);

        Assertions.assertLinesMatch(Arrays.asList("1", "3", "5", "7", "9~10"), result);
    }

    @Test
    @DisplayName("추가 테스트2")
    void test4() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 10};

        List<String> result = sut.numberToString(input);

        Assertions.assertLinesMatch(Arrays.asList("1~8", "10"), result);
    }

    @Test
    @DisplayName("추가 테스트3")
    void test5() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 13, 14, 15, 16};

        List<String> result = sut.numberToString(input);

        Assertions.assertLinesMatch(Arrays.asList("1~8", "10", "12~16"), result);
    }

    @Test
    @DisplayName("추가 테스트4")
    void test6() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 13, 14, 15, 16, 18};

        List<String> result = sut.numberToString(input);

        Assertions.assertLinesMatch(Arrays.asList("1~8", "10", "12~16", "18"), result);
    }

    @Test
    @DisplayName("추가 테스트5")
    void test7() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 13, 14, 15, 16, 18, 19, 20};

        List<String> result = sut.numberToString(input);

        Assertions.assertLinesMatch(Arrays.asList("1~8", "10", "12~16", "18~20"), result);
    }
}