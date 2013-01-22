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

package com.automatous_monk.music;

import jm.JMC;

public final class Mode {
	private String name;
	private Mode(String nm) {
		name = nm;
	}
	public String toString() {
		return name;
	}
	public static final Mode CHROMATIC = new Mode("Chromatic"),
		MAJOR = new Mode("Major"),
		MINOR = new Mode("Minor"),
		HARMONIC_MINOR = new Mode("Harmonic Minor"),
		MELODIC_MINOR = new Mode("Melodic Minor"),
		NATURAL_MINOR = new Mode("Natural Minor"),
		DIATONIC_MINOR = new Mode("Diatonic Minor"),
		AEOLIAN = new Mode("Aeolian"),
		DORIAN = new Mode("Dorian"),
		LYDIAN = new Mode("Lydian"),
		MIXOLYDIAN = new Mode("Mixolydian"),
		PENTATONIC = new Mode("Pentatonic"),
		BLUES = new Mode("Blues"),
		TURKISH = new Mode("Turkish"),
		INDIAN = new Mode("Indian");
	public static final Mode[] SCALES =
		{
			CHROMATIC,
			MAJOR,
			MINOR,
			HARMONIC_MINOR,
			NATURAL_MINOR,
			DIATONIC_MINOR,
			AEOLIAN,
			DORIAN,
			LYDIAN,
			MIXOLYDIAN,
			PENTATONIC,
			BLUES,
			TURKISH,
			INDIAN };

	public int[] getScale() {
		if (name.equals("Chromatic")) {
			return JMC.CHROMATIC_SCALE;
		} else if (name.equals("Major")) {
			return JMC.MAJOR_SCALE;
		} else if (name.equals("Minor")) {
			return JMC.MINOR_SCALE;
		} else if (name.equals("Harmonic Minor")) {
			return JMC.HARMONIC_MINOR_SCALE;
		} else if (name.equals("Melodic Minor")) {
			return JMC.MELODIC_MINOR_SCALE;
		} else if (name.equals("Natural Minor")) {
			return JMC.NATURAL_MINOR_SCALE;
		} else if (name.equals("Diatonic Minor")) {
			return JMC.DIATONIC_MINOR_SCALE;
		} else if (name.equals("Aeolian")) {
			return JMC.AEOLIAN_SCALE;
		} else if (name.equals("Dorian")) {
			return JMC.DORIAN_SCALE;
		} else if (name.equals("Lydian")) {
			return JMC.LYDIAN_SCALE;
		} else if (name.equals("Mixolydian")) {
			return JMC.MIXOLYDIAN_SCALE;
		} else if (name.equals("Pentatonic")) {
			return JMC.PENTATONIC_SCALE;
		} else if (name.equals("Blues")) {
			return JMC.BLUES_SCALE;
		} else if (name.equals("Turkish")) {
			return JMC.TURKISH_SCALE;
		} else if (name.equals("Indian")) {
			return JMC.INDIAN_SCALE;
		} else if (name.equals("Major")) {
			return JMC.CHROMATIC_SCALE;
		} else {
			// Shouldn't happen.

			return null;
		}
	}
    
    public final static int HALF_STEPS_IN_OCTAVE = 12;
}
