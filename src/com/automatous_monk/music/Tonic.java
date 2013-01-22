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

import java.util.HashMap;
import java.util.Map;

import jm.JMC;

public final class Tonic {
    private String name;
    private static Map nameToObject = new HashMap();
    private Tonic(String nm) {
        name = nm;
        if (!nameToObject.containsKey(name)) {
            nameToObject.put(name, this);
        }
    }
    public String toString() {
        return name;
    }
    
    public static Tonic fromString(String name) {
        return (Tonic) nameToObject.get(name);
    }
    
    public static final Tonic C = new Tonic("C"),
        G = new Tonic("G"),
        D = new Tonic("D"),
        A = new Tonic("A"),
        E = new Tonic("E"),
        B_CF = new Tonic("B/C Flat"),
        FS_GF = new Tonic("F Sharp/G Flat"),
        DF_CS = new Tonic("D Flat/C Sharp"),
        AF = new Tonic("A Flat"),
        EF = new Tonic("E Flat"),
        BF = new Tonic("B Flat"),
        F = new Tonic("F");
    public static final Tonic[] key =
        { C, G, D, A, E, B_CF, FS_GF, DF_CS, AF, EF, BF, F };

    public int getLowPitch() {
        if (this.equals(C)) {
            return JMC.C4;
        } else if (this.equals(G)) {
            return JMC.G4;
        } else if (this.equals(D)) {
            return JMC.D4;
        } else if (this.equals(A)) {
            return JMC.A4;
        } else if (this.equals(E)) {
            return JMC.E4;
        } else if (this.equals(B_CF)) {
            return JMC.B4;
        } else if (this.equals(FS_GF)) {
            return JMC.FS4;
        } else if (this.equals(DF_CS)) {
            return JMC.DF4;
        } else if (this.equals(AF)) {
            return JMC.AF4;
        } else if (this.equals(EF)) {
            return JMC.EF4;
        } else if (this.equals(BF)) {
            return JMC.BF4;
        } else { // (this.equals(F)) 
            return JMC.F4;
        }
    }
}
