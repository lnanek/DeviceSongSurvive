/*
 * Created on Mar 13, 2004
 */
package com.automatous_monk;

import jm.JMC;
import jm.music.data.Note;

/**
 * @author Paul Reiners
 */
public class CumBinNumToNoteConverter
    extends BinNumToNoteConverter
    implements JMC {

    private int precedingBinNum;

    public CumBinNumToNoteConverter() {
        precedingBinNum = 0;
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.CARowToNoteConverter#convert(int[], com.automatous_monk.HorizontalBias, int[])
     */
    public Note convert(
        int[] cARow,
        HorizontalBias bias,
        int[] possiblePitches) {

        int cumulativePitch = convertToPitch(cARow, bias, possiblePitches);
        Note note = new Note(cumulativePitch, CAConstants.DEFAULT_NOTE);

        return note;
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.CARowToNoteConverter#convertToPitch(int[], com.automatous_monk.HorizontalBias, int[])
     */
    public int convertToPitch(
        int[] cARow,
        HorizontalBias bias,
        int[] possiblePitches) {

        int newBinNum = super.convertToNumber(cARow, bias);
        int cumBinNum = (precedingBinNum + newBinNum) % 128;
        int cumulativePitch =
            possiblePitches[cumBinNum % possiblePitches.length];
        precedingBinNum = cumBinNum;

        return cumulativePitch;
    }
}
