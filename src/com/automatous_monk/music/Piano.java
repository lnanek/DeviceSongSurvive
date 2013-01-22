/*
 * Created on Apr 21, 2004
 */
package com.automatous_monk.music;

import jm.JMC;

/**
 * @author Paul Reiners
 */
public class Piano implements Instrument {

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getPlayableRange()
     */
    public int[] getPlayableRange() {
        return new int[] { JMC.A0, JMC.C8 };
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getName()
     */
    public String getName() {
        return "Piano";
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getID()
     */
    public int getID() {
        return JMC.PIANO;
    }

}
