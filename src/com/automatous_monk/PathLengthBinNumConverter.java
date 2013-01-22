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
 * Created on Jan 31, 2004
 */
package com.automatous_monk;

/**
 * @author Paul Reiners
 */
public class PathLengthBinNumConverter extends BinaryNumberConverter {
}

class Path implements Comparable {

    private int pos;
    private int length;
    HorizontalBias bias;

    public Path(int pos, int length, HorizontalBias bias) {
        this.pos = pos;
        this.length = length;
        this.bias = bias;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        Path otherPath = (Path) o;
        if (length != otherPath.length) {
            return length - otherPath.length;
        } else if (getDistFromOrigin() != otherPath.getDistFromOrigin()) {
            return getDistFromOrigin() - otherPath.getDistFromOrigin();
        } else {
            if (bias.equals(HorizontalBias.LEFT)) {
                // Break tie by picking one on the left.
                return pos - otherPath.pos;
            } else {
                // Break tie by picking one on the right.
                return otherPath.pos - pos;
            }
        }
    }

    private int getDistFromOrigin() {
        int dist = (pos >= 0 ? pos : -pos);

        return dist;
    }
    /**
     * @return Position of cell.
     */
    public int getPos() {
        return pos;
    }
}
