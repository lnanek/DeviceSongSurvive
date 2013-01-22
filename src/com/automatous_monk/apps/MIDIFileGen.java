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
 * Created on Feb 7, 2004
 */
package com.automatous_monk.apps;

import java.io.File;
import java.util.Random;

import jm.JMC;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Write;

import com.automatous_monk.BinaryNumberConverter;
import com.automatous_monk.CAConstants;
import com.automatous_monk.CumulativeBinaryNumberConverter;
import com.automatous_monk.FixedNoteConverter;
import com.automatous_monk.HorizontalBias;
import com.automatous_monk.WrappedCA;
import com.automatous_monk.music.Key;
import com.automatous_monk.music.Mode;

/**
 * @author Paul Reiners
 */
public class MIDIFileGen {

    public static void writeScore(
        int rule,
        String filePath,
        boolean randomStartState,
        int generationCnt, Random randomizer) {

        Score score = new Score("CA Rule " + rule);

        Mode type = Mode.CHROMATIC;
        FixedNoteConverter converter = new FixedNoteConverter(new Key(type));

        BinaryNumberConverter player = new BinaryNumberConverter();
        int[] startState = new int[2 * generationCnt - 1];
        if (!randomStartState) {
            startState[startState.length / 2] = 1;
        } else {
            for (int i = 0; i < startState.length; i++) {
                if (randomizer.nextBoolean()) {
                    startState[i] = 1;
                }
            }
        }
        WrappedCA cA = new WrappedCA(rule, startState, generationCnt);
        Part part =
            player.getPart(
                cA,
                converter,
                HorizontalBias.LEFT,
                "Organ",
                JMC.ORGAN);
        score.addPart(part);
        part =
            player.getPart(
                cA,
                converter,
                HorizontalBias.RIGHT,
                "Organ",
                JMC.ORGAN);
        score.addPart(part);

        player = new CumulativeBinaryNumberConverter();
        part =
            player.getPart(
                cA,
                converter,
                HorizontalBias.LEFT,
                "Organ",
                JMC.ORGAN);
        score.addPart(part);
        part =
            player.getPart(
                cA,
                converter,
                HorizontalBias.RIGHT,
                "Organ",
                JMC.ORGAN);
        score.addPart(part);

        double tempo = 120.0;
        score.setTempo(tempo);

        Write.midi(score, filePath);
    }
}
