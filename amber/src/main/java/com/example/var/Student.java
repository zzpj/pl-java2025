package com.example.var;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private final Integer[] marks;

    public Student(Integer[] marks) {
        this.marks = marks;
    }

    public Integer getNumberOfMarks() {
        return marks.length;
    }

    // TODO: implement here
    // rewrite code using var from JDK 10

    public Integer getTotalSumOfMarks() {
        Integer sum = 0;
        for (Integer mark : marks) {
            sum += mark;
        }
        return sum;
    }

    public BigDecimal getAverageOfMarks() {
        Integer sum = getTotalSumOfMarks();
        return new BigDecimal(sum)
                .divide(new BigDecimal(marks.length), 3, RoundingMode.UP);
    }

    public Integer getMaximumMark() {
        Integer max = Integer.MIN_VALUE;
        for (Integer mark : marks) {
            if (mark > max) {
                max = mark;
            }
        }
        return max;
    }

    public Integer getMinimumMark() {
        Integer min = Integer.MAX_VALUE;
        for (Integer mark : marks) {
            if (mark < min) {
                min = mark;
            }
        }
        return min;
    }

    public List<Integer> getDistinctMarks() {
        List<Integer> distinctMarks = new ArrayList<>();

        for (Integer mark : marks) {
            if (!distinctMarks.contains(mark)) {
                distinctMarks.add(mark);
            }
        }
        return distinctMarks;
    }
}
