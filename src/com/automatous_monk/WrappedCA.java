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
 * Created on Jan 13, 2004
 */
package com.automatous_monk;

/**
 * @author Paul Reiners
 */
public class WrappedCA extends CellularAutomaton {

    private int width;

    /**
     * @param rule
     * @param startState
     * @param generationCnt
     */
    public WrappedCA(int rule, int[] startState, int generationCnt) {

        this(rule, startState, generationCnt, startState.length);
    }

    /**
     * @param rule
     * @param startState
     */
    public WrappedCA(int rule, int[] startState) {

        super(rule, startState);
    }

    /**
     * @param rule
     */
    public WrappedCA(int rule) {

        super(rule);
    }

    /**
     * @param rule
     * @param startState
     * @param generationCnt
     * @param width
     */
    public WrappedCA(int rule, int[] strtStt, int generationCnt, int wdth) {

        super(rule, generationCnt);
        width = wdth;
        startState = new int[width];
        int pos = (width - strtStt.length) / 2;
        System.arraycopy(strtStt, 0, startState, pos, strtStt.length);
    }

    /**
     * @param rule
     * @param width
     */
    public WrappedCA(int rule, int width, int generationCnt) {
        this(rule, new int[width], generationCnt);
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.CellularAutomaton#getLimitedCellHistory(int, int)
     */
    public int[] getLimitedCellHistory(int pos, int genCnt) {
        int[] cellHistory = getCellHistory(pos);
        int[] limitedCellHistory = new int[genCnt];
        System.arraycopy(cellHistory, 0, limitedCellHistory, 0, genCnt);

        return limitedCellHistory;
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.CellularAutomaton#getNextState(int[])
     */
    public int[] getNextState(int[] state) {
        int len = state.length;
        int[] nextState = new int[len];
        for (int i = 0; i < len; i++) {
            int nW = (i - 1) % len;
            if (nW < 0) {
                nW += len;
            }
            int n = i;
            int nE = (i + 1) % len;
            nextState[i] = getNextStateOfCell(state[nW], state[n], state[nE]);
        }

        return nextState;
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.CellularAutomaton#getRadius()
     */
    public int getRadius() {
        return (width - 1) / 2;
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.CellularAutomaton#getCellHistory(int)
     */
    public int[] getCellHistory(int pos) {
        if (generations == null) {
            generateGenerations();
        }
        int[] cellHistory = new int[generationCnt];
        for (int i = 0; i < generationCnt; i++) {
            cellHistory[i] = generations[i][pos + (width - 1) / 2];
        }

        return cellHistory;
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.CellularAutomaton#getLinearizedHistory()
     */
    public int[] getLinearizedHistory() {
        // TODO Auto-generated method stub
        return null;
    }
}
