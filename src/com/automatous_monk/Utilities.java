/* Automatous Monk: A program for generating music from cellular automata
 * 
 * Copyright (C) 2004 by Paul Reiners
 * 
 * This program is free software; you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation; either version 2 of the License, or (at your option) any later 
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple 
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * You may contact the program author: Paul Reiners at:
 * 
 *      paulreiners@earthlink.net
 * 
 * or
 * 
 *      601 Van Ness Avenue
 *      Apartment 1007
 *      San Francisco, CA  94102
 */

/*
 * Created on Jan 19, 2004
 */
package com.automatous_monk;

import java.util.List;

/**
 * @author Paul Reiners
 */
public class Utilities {
	public static int[] getIntArrayFromIntegerList(List integerList) {
		int[] pitchSequence = new int[integerList.size()];
		for (int i = 0; i < pitchSequence.length; i++) {
			pitchSequence[i] = ((Integer) integerList.get(i)).intValue();
		}
		return pitchSequence;
	}

	public static int[] getFractionalEquivalent(double frac) {
		double closestDiff = Double.MAX_VALUE;
		int bestNum = 1;
		int bestDenom = 1;
		for (int num = 1; num < 12; num++) {
			for (int denom = 1; denom < 12; denom++) {
				if (gCD(num, denom) != 1) {
					continue;
				}

				double diff = Math.abs(((double) num / (double) denom) - frac);
				if (diff < closestDiff) {
					closestDiff = diff;
					bestNum = num;
					bestDenom = denom;
				}
			}
		}

		return new int[] { bestNum, bestDenom };
	}

	public static int gCD(int n1, int n2) {
		int nn1 = n1;
		int nn2 = n2;
		
		if (nn2 > nn1) {
			int temp = nn2;
			nn2 = nn1;
			nn1 = temp;
		}
		while (nn2 > 0) {
			int mod = nn1 % nn2;
			nn1 = nn2;
			nn2 = mod;
		}

		return nn1;
	}

	public static int convertRowIndexToRowPos(int rowIndex, int rowLen) {
		int mid = rowLen / 2;
		int pos = rowIndex - mid;

		return pos;
	}

	public static int convertRowPosToRowIndex(int rowPos, int rowLen) {
		int mid = rowLen / 2;
		int rowIndex = rowPos + mid;

		return rowIndex;
	}
}
