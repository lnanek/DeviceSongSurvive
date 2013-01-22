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
public abstract class CellularAutomaton {

    private int rule;
    protected int[] startState;
    int[][] generations;
    protected int generationCnt;

    public CellularAutomaton(int rule, int[] startState) {
        this(rule);
        this.startState = startState;
    }

    public CellularAutomaton(int rule, int generationCnt) {
        this(rule);
        this.generationCnt = generationCnt;
    }

    public CellularAutomaton(int rule) {
        this.rule = rule;
    }

    public abstract int[] getNextState(int[] state);

    /**
     * Computes the next state of a cell.
     * @param nWCellState State of the cell to the left of the current cell in 
     *        preceding generation.
     * @param nCellState State of the current cell in preceding generation.
     * @param nECellState State of the cell to the right of the current cell in 
     *        preceding generation.
     * @return Next state of the cell.
     */
    int getNextStateOfCell(int nWCellState, int nCellState, int nECellState) {

        // Find the index of the current state in the rule pattern
        int index = 4 * nWCellState + 2 * nCellState + nECellState;
        // Get the value of the digit in that place
        int nextState = (rule >> index) % 2;

        return nextState;
    }

    /**
     * @return Number of generations
     */
    public int getGenerationCnt() {
        return generationCnt;
    }

    public int[][] getGenerations() {
        if (generations == null) {
            generateGenerations();
        }

        return generations;
    }

    protected void generateGenerations() {
        generations = new int[generationCnt][];
        generations[0] = startState;
        for (int i = 1; i < generationCnt; i++) {
            generations[i] = getNextState(generations[i - 1]);
        }
    }

    abstract public int[] getLimitedCellHistory(int pos, int genCnt);

    public static void main(String[] args) {
    }

    /**
     * @return
     */
    public int getRule() {
        return rule;
    }

    /**
     * @return
     */
    abstract public int getRadius();

    /**
     * @param pos
     * @return History of the cell
     */
    abstract public int[] getCellHistory(int pos);

    abstract public int[] getLinearizedHistory();
}
