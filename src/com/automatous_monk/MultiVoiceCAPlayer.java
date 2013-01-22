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
 * Created on Jan 14, 2004
 */
package com.automatous_monk;

import com.automatous_monk.music.*;

import jm.JMC;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

/**
 * @author Paul Reiners
 */
public class MultiVoiceCAPlayer extends CAPlayer implements JMC {

    private static void usage() {
        System.err.println("usage: java CAViewer <rule> <generation count>");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            usage();

            return;
        }
        int rule = Integer.parseInt(args[0]);
        int generationCnt = Integer.parseInt(args[1]);

        MultiVoiceCAPlayer player = new MultiVoiceCAPlayer();
        FixedNoteConverter converter =
            new FixedNoteConverter(new Key(Mode.MAJOR));
        CellularAutomaton cA = new ExpandingUniverseCA(rule, generationCnt);
        player.getScore(cA, converter, "Organ", JMC.ORGAN);
    }

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
     * @see camusic.CAPlayer#getPart(CellularAutomaton, camusic.CellToPhraseConverter)
     */
    public Part getPart(
        CellularAutomaton cA,
        FixedNoteConverter converter,
        String name,
        int instrument) {

        //Parts can have a name, instrument, and channel.
        Part organ = new Part(name, instrument, 0);

        int cARadius = cA.getRadius();
        for (int pos = -cARadius; pos <= cARadius; pos++) {
            int[] cellHistory = cA.getCellHistory(pos);
            // Convert the cell history to a phrase
            Phrase phr =
                converter.convertCellHistoryToPhrase(cellHistory, pos, false);

            if (phr != null) {
                //add phrase to a part
                organ.addPhrase(phr);
            }
        }

        return organ;
    }
}
