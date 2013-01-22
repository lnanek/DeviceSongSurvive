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
import jm.constants.Scales;

public final class ScaleType implements Scales, JMC {
	private String name;
	private ScaleType(String nm) {
		name = nm;
	}
	public String toString() {
		return name;
	}
	public static final ScaleType CHROMATIC = new ScaleType("Chromatic"),
		MAJOR = new ScaleType("Major"),
		MINOR = new ScaleType("Minor"),
		HARMONIC_MINOR = new ScaleType("Harmonic Minor"),
		MELODIC_MINOR = new ScaleType("Melodic Minor"),
		NATURAL_MINOR = new ScaleType("Natural Minor"),
		DIATONIC_MINOR = new ScaleType("Diatonic Minor"),
		AEOLIAN = new ScaleType("Aeolian"),
		DORIAN = new ScaleType("Dorian"),
		LYDIAN = new ScaleType("Lydian"),
		MIXOLYDIAN = new ScaleType("Mixolydian"),
		PENTATONIC = new ScaleType("Pentatonic"),
		BLUES = new ScaleType("Blues"),
		TURKISH = new ScaleType("Turkish"),
		INDIAN = new ScaleType("Indian");
	public static final ScaleType[] SCALES =
		{
			CHROMATIC,
			MAJOR,
			MINOR,
			HARMONIC_MINOR,
			MELODIC_MINOR,
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
			return CHROMATIC_SCALE;
		} else if (name.equals("Major")) {
			return MAJOR_SCALE;
		} else if (name.equals("Minor")) {
			return MINOR_SCALE;
		} else if (name.equals("Harmonic Minor")) {
			return HARMONIC_MINOR_SCALE;
		} else if (name.equals("Melodic Minor")) {
			return MELODIC_MINOR_SCALE;
		} else if (name.equals("Natural Minor")) {
			return NATURAL_MINOR_SCALE;
		} else if (name.equals("Diatonic Minor")) {
			return DIATONIC_MINOR_SCALE;
		} else if (name.equals("Aeolian")) {
			return AEOLIAN_SCALE;
		} else if (name.equals("Dorian")) {
			return DORIAN_SCALE;
		} else if (name.equals("Lydian")) {
			return LYDIAN_SCALE;
		} else if (name.equals("Mixolydian")) {
			return MIXOLYDIAN_SCALE;
		} else if (name.equals("Pentatonic")) {
			return PENTATONIC_SCALE;
		} else if (name.equals("Blues")) {
			return BLUES_SCALE;
		} else if (name.equals("Turkish")) {
			return TURKISH_SCALE;
		} else if (name.equals("Indian")) {
			return INDIAN_SCALE;
		} else if (name.equals("Major")) {
			return CHROMATIC_SCALE;
		} else {
			// Shouldn't happen.

			return null;
		}
	}
    
	public int[] getPossiblePitches(String key) {
		int centralPitch = C3;
		if (key.equals("C")) {
			centralPitch = C3;
		}
		else if (key.equals("D")) {
			centralPitch = D3;
		}
		else if (key.equals("A")) {
			centralPitch = A3;
		}
		else if (key.equals("A#")) {
			centralPitch = AS3;
		}
		else if (key.equals("B")) {
			centralPitch = B3;
		}
		
		int[] scale = getScale();
		int[] possiblePitches = new int[scale.length];
		for (int i = 0; i < possiblePitches.length; i++) {
			possiblePitches[i] = centralPitch + scale[i];
		}
		
		return possiblePitches;
	}
}
