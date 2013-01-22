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
 * You may contact the program author Paul Reiners at:
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
 * Created on Jan 14, 2004
 */
package com.automatous_monk;

import jm.JMC;
import jm.constants.Scales;
import jm.music.data.Note;
import jm.music.data.Phrase;

import com.automatous_monk.music.Key;
import com.automatous_monk.music.Mode;

/**
 * @author Paul Reiners
 */
public class FixedNoteConverter implements JMC, Scales, CellToPhraseConverter {

    private Key key;
    private int lowNote;
    private int hiNote;
    private int[] possiblePitches;

    public FixedNoteConverter(Key key) {
        this(key, 0, 127);
    }

    public FixedNoteConverter(Key key, int lowNote, int hiNote) {

        this.key = key;
        this.lowNote = lowNote;
        this.hiNote = hiNote;
        possiblePitches = key.getNaturals(lowNote, hiNote);
    }

    /* (non-Javadoc)
     * @see camusic.CellToPhraseConverter#convertCellHistoryToPhrase(int[], int)
     */
    public Phrase convertCellHistoryToPhrase(int[] cellHistory, int cellPos) {
        return convertCellHistoryToPhrase(cellHistory, cellPos, true);
    }

    public Phrase convertCellHistoryToPhrase(
        int[] cellHistory,
        int cellPos,
        boolean normalizePitch) {

        Phrase phr = new Phrase(0.0);

        int pitch = getStartingPitch(cellPos, normalizePitch);
        if (pitch < 0 || pitch > 127) {
            return null;
        }

        for (int i = 0; i < cellHistory.length; i++) {
            Note n;
            if (cellHistory[i] == 1) {
                n = new Note(pitch, CAConstants.DEFAULT_NOTE);
            } else {
                n = new Note(REST, CAConstants.DEFAULT_NOTE);
            }
            phr.addNote(n);
        }

        return phr;
    }

    /* (non-Javadoc)
     * @see camusic.CellToPhraseConverter#convertCAHistoryToPhrase(int[])
     */
    public Phrase convertCellHistoryToPhrase(int[] cellHistory) {

        Phrase phr = convertCellHistoryToPhrase(cellHistory, 0);

        return phr;
    }

    int getStartingPitch(int cellPos) {
        return getStartingPitch(cellPos, true);
    }

    /**
     * @param cellPos Position of the cell.  The position of the center cell is
     *                0, while positions to the right are positive and positions 
     *                to the left are negative.
     * @param normalize If <code>true</code>, make sure return value is a valid 
     *                  MIDI pitch.  That is, make sure that it is between 0 and 
     *                  128.
     * @return The starting pitch of the cell.
     */
    int getStartingPitch(int cellPos, boolean normalize) {
        int[] scale = key.getMode().getScale();
        int interval;
        int scaleWidth = scale.length;
        // Compute the interval from middle C to this pitch.
        if (cellPos >= 0) {
            interval =
                (cellPos / scaleWidth) * Mode.HALF_STEPS_IN_OCTAVE
                    + scale[cellPos % scaleWidth];
        } else {
            interval = (-cellPos / scaleWidth) * Mode.HALF_STEPS_IN_OCTAVE;
            if (-cellPos % scaleWidth != 0) {
                interval += 12 - scale[scaleWidth - (-cellPos % scaleWidth)];
            }
            interval *= -1;
        }

        // Add interval to middle C, C4.
        int pitch = C4 + interval;

        if (normalize) {
            pitch %= 128;
            if (pitch < 0) {
                pitch += 128;
            }
        }

        return pitch;
    }

    /**
     * @return
     */
    public int[] getPossiblePitches() {
        return possiblePitches;
    }
}
