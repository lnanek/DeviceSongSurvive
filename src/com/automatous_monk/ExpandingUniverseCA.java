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
public class ExpandingUniverseCA extends CellularAutomaton {

    private int radius;

    /**
     * @param rule
     * @param startState
     * @param generationCnt
     */
    public ExpandingUniverseCA(int rule, int[] startState, int generationCnt) {

        super(rule, startState);

        this.generationCnt = generationCnt;
    }

    /**
     * @param rule
     * @param generationCnt
     */
    public ExpandingUniverseCA(int rule, int generationCnt) {
        this(rule, new int[] { 1 }, generationCnt);
    }

    /**
     * @param pos
     * @return History of the cell
     */
    public int[] getCellHistory(int pos) {
        return getCellHistory(pos, generationCnt);
    }

    public int[] getLimitedCellHistory(int pos) {
        return getLimitedCellHistory(pos, generationCnt);
    }

    public int[] getLinearizedHistory() {
        if (generations == null) {
            generateGenerations();
        }
        int[] history = new int[generationCnt * generationCnt];
        int row = 0;
        int col = 0;
        int dCol = 1;
        for (int i = 0; i < history.length; i++) {
            history[i] = generations[row][col];
            if (i == (row + 1) * (row + 1) - 1) {
                row++;
                if (dCol > 0) {
                    col = generations[row].length - 1;
                } else {
                    col = 0;
                }
                dCol = -dCol;
            } else {
                col += dCol;
            }
        }

        return history;
    }

    /**
     * @param pos
     * @return History of cell.
     */
    public int[] getCellHistory(int pos, int genCnt) {
        if (generations == null) {
            generateGenerations();
        }
        int[] cellHistory = new int[genCnt];
        for (int i = 0; i < genCnt; i++) {
            int index = pos + i;
            if (0 <= index && index < 2 * i + 1) {
                cellHistory[i] = generations[i][index];
            }
        }

        return cellHistory;
    }

    public int[] getLimitedCellHistory(int pos, int genCnt) {
        int[] cellHistory = getCellHistory(pos, genCnt);
        int absVal = pos >= 0 ? pos : -pos;
        int cellLife = genCnt - absVal;
        int[] limitedCellHistory = new int[cellLife];
        System.arraycopy(
            cellHistory,
            cellHistory.length - cellLife,
            limitedCellHistory,
            0,
            cellLife);

        return limitedCellHistory;
    }

    /**
     * @return Radius of the CA.
     */
    public int getRadius() {
        if (generations == null) {
            generateGenerations();
        }
        int width = generations[generations.length - 1].length;
        radius = (width - 1) / 2;

        return radius;
    }

    public int[] getNextState(int[] state) {
        int[] nextState = new int[state.length + 2];
        for (int i = 0; i < nextState.length; i++) {
            int indexAbove = i - 1;
            int nWVal = 0;
            if (indexAbove - 1 >= 0) {
                nWVal = state[indexAbove - 1];
            }
            int nVal = 0;
            if (0 <= indexAbove && indexAbove < state.length) {
                nVal = state[indexAbove];
            }
            int nEVal = 0;
            if (indexAbove < state.length - 1) {
                nEVal = state[indexAbove + 1];
            }
            int val = getNextStateOfCell(nWVal, nVal, nEVal);

            nextState[i] = val;
        }

        return nextState;
    }
}
