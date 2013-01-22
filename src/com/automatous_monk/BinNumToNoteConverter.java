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
 * Created on Mar 7, 2004
 */
package com.automatous_monk;

import jm.music.data.Note;

/**
 * @author Paul Reiners
 */
public class BinNumToNoteConverter implements CARowToNoteConverter {

    /**
     * Reorders a row according to significance of digits.
     * @param cARow The row of the CA
     * @param bias Left- or right-side bias
     * @return The reordered row with least-significant digits to right
     */
    int[] reorderRow(int[] row, HorizontalBias bias) {
        int len = row.length;
        int[] reordered = new int[len];
        int mid = len / 2;
        reordered[len - 1] = row[mid];
        for (int i = 0; i < (len - 1) / 2; i++) {
            // Note that this favors one side of the CA over the 
            // other side, but there seems to be no way to get around this.
            if (bias.equals(HorizontalBias.LEFT)) {
                reordered[len - (2 * i + 1) - 1] = row[mid - (i + 1)];
                reordered[len - (2 * i + 2) - 1] = row[mid + i + 1];
            } else {
                reordered[len - (2 * i + 1) - 1] = row[mid + i + 1];
                reordered[len - (2 * i + 2) - 1] = row[mid - (i + 1)];
            }
        }

        return reordered;
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.CARowToNoteConverter#convert(int[], com.automatous_monk.HorizontalBias, int[])
     */
    public Note convert(
        int[] cARow,
        HorizontalBias bias,
        int[] possiblePitches) {

        int pitch = convertToPitch(cARow, bias, possiblePitches);
        Note note = new Note(pitch, CAConstants.DEFAULT_NOTE);

        return note;
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.CARowToNoteConverter#convertToPitch(int[], com.automatous_monk.HorizontalBias, int[])
     */
    public int convertToPitch(
        int[] cARow,
        HorizontalBias bias,
        int[] possiblePitches) {

        int num = convertToNumber(cARow, bias);

        int pitch = possiblePitches[num % possiblePitches.length];

        return pitch;
    }

    int convertToNumber(int[] cARow, HorizontalBias bias) {
        int[] reorderedRow = reorderRow(cARow, bias);
        int num = 0;
        for (int i = 0; i < reorderedRow.length; i++) {
            num = (2 * num + reorderedRow[i]) % 128;
        }
        return num;
    }
}
