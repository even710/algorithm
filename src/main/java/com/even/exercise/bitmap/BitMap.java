package com.even.exercise.bitmap;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/11 17:05
 */
public class BitMap {
    private long[] words;
    private int WORDS_SIZE;
    private int WORD_BITS_SIZE = 6;

    private void set(int bitIndex) {
        if (bitIndex < 0) throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
        int byteIndex = bitIndex >> WORD_BITS_SIZE;//右移6位，即除以64
        words[byteIndex] |= 1 << bitIndex;//令1左移byteIndex位
    }

    private boolean get(int bitIndex) {
        if (bitIndex < 0) throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
        int byteIndex = bitIndex >> WORD_BITS_SIZE;
        return (byteIndex < WORDS_SIZE - 1) && ((words[byteIndex] & (1 << bitIndex)) != 0);
    }

    public BitMap(int n) {
        this.WORDS_SIZE = n;
        this.words = new long[n];
    }

    public BitMap() {
        this.words = new long[4];
    }

    public static void main(String[] args) {
        long a = 12L;
        System.out.println(a);
    }
}
