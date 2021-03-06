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
 * Created on Jan 24, 2004
 */
package com.automatous_monk;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

/**
 * @author Paul Reiners
 */
public class BinaryNumberConverter
    extends CAPlayer
    implements CAToPhraseConverter, JMC {

    protected BinNumToNoteConverter rowToNoteConverter;

    public BinaryNumberConverter() {
        rowToNoteConverter = new BinNumToNoteConverter();
    }

    /**
     * Converts the successive rows of a CA to a musical phrase. 
     * @param clllrAutmtn A cellular automaton 
     * @param bias Favor left- or right-side in generating pitches
     * @return A musical phrase corresponding to <code>clllrAutmtn</code> 
     */
    public Phrase convertCAHistoryToPhrase(
        CellularAutomaton clllrAutmtn,
        HorizontalBias bias,
        int[] possiblePitches) {
        Phrase phr = new Phrase();
        for (int i = 0; i < clllrAutmtn.getGenerationCnt(); i++) {
            int[] row = clllrAutmtn.getGenerations()[i];
            Note note = rowToNoteConverter.convert(row, bias, possiblePitches);
            phr.add(note);
        }

        return phr;
    }

    /* (non-Javadoc)
     * @see camusic.CAToPhraseConverter#convertCAHistoryToPhrase(CellularAutomaton)
     */
    public Phrase convertCAHistoryToPhrase(
        CellularAutomaton clllrAutmtn,
        int[] possiblePitches) {
        return convertCAHistoryToPhrase(
            clllrAutmtn,
            HorizontalBias.LEFT,
            possiblePitches);
    }

    /* (non-Javadoc)
     * @see camusic.CAPlayer#getScore(int, int, camusic.CellToPhraseConverter)
     */
    public Score getScore(
        CellularAutomaton cA,
        FixedNoteConverter converter,
        String name,
        int instrument) {

        //Create the data objects we want to use
        Score score = new Score("CA Rule " + cA.getRule());

        Part part = getPart(cA, converter, name, instrument);
        //add part to the score
        score.addPart(part);

        return score;
    }

    /* (non-Javadoc)
     * @see camusic.CAPlayer#getPart(int, int, camusic.CellToPhraseConverter)
     */
    public Part getPart(
        CellularAutomaton cA,
        FixedNoteConverter converter,
        String name,
        int instrument) {

        return getPart(cA, converter, HorizontalBias.LEFT, name, instrument);
    }

    public Part getPart(
        CellularAutomaton clllrAutmtn,
        FixedNoteConverter converter,
        HorizontalBias bias,
        String name,
        int instrument) {

        //Parts can have a name, instrument, and channel.
        Part organ = new Part(name, instrument, 0);

        // Convert the CA history to a phrase
        Phrase phrase =
            convertCAHistoryToPhrase(
                clllrAutmtn,
                bias,
                converter.getPossiblePitches());

        //add phrase to the part
        organ.addPhrase(phrase);

        return organ;
    }
}
