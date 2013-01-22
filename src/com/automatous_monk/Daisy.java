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
 * Created on Feb 10, 2004
 */

package com.automatous_monk;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;

/**
 * @author Paul Reiners
 */
public class Daisy implements JMC {

    public static void main(String[] args) {
        int[] pitches = { C5, A4, F4, C4, D4, E4, F4, D4, F4, C4 };
        double[] rhythmValues =
            {
                DOTTED_HALF_NOTE,
                DOTTED_HALF_NOTE,
                DOTTED_HALF_NOTE,
                DOTTED_HALF_NOTE,
                QUARTER_NOTE,
                QUARTER_NOTE,
                QUARTER_NOTE,
                HALF_NOTE,
                QUARTER_NOTE,
                2 * DOTTED_HALF_NOTE };
        Note[] notes = new Note[pitches.length];
        for (int i = 0; i < notes.length; i++) {
            // A note is made up of a pitch and duration
            notes[i] = new Note(pitches[i], rhythmValues[i]);
        }
        // A phrase is made up of notes
        Phrase phrase = new Phrase(notes);
        Part pianoPart = new Part("Piano", PIANO);
        // A part is made up of phrases
        pianoPart.add(phrase);
        // A score is made up of part(s)
        int tempo = 180;
        Score daisy = new Score("A Bicycle Built For Two", tempo, pianoPart);
        // In key of F: 1 flat
        daisy.setKeySignature(-1);
        // In 3/4 time
        daisy.setNumerator(3);
        daisy.setDenominator(4);
        // Display score in standard musical notation
        View.notate(daisy);
        // Write out score to MIDI file
        Write.midi(daisy, "C:\\Daisy.mid");
    }
}
