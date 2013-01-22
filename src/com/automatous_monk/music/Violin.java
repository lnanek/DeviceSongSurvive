/*
 * Created on Apr 18, 2004
 */
package com.automatous_monk.music;

import jm.JMC;

/**
 * @author Paul Reiners
 */
public class Violin implements Instrument {

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getPlayableRange()
     */
    public int[] getPlayableRange() {
        return new int[] { JMC.G2, JMC.B5 };
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getName()
     */
    public String getName() {
        return "Violin";
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getID()
     */
    public int getID() {
        return JMC.VIOLIN;
    }
}
