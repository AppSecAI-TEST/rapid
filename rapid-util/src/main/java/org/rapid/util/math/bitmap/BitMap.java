package org.rapid.util.math.bitmap;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;

import org.rapid.util.common.enums.BIT;

public class BitMap {

	public static void main(String[] args) {
		BitSet bitSet = new BitSet(1);
		bitSet.set(100, true);
		System.out.println(Arrays.toString(bitSet.toByteArray()));
	}
}
