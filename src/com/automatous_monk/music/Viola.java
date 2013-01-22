/*
 * Created on Apr 18, 2004
 */
package com.automatous_monk.music;

import jm.JMC;

/**
 * @author Paul Reiners
 */
public class Viola implements Instrument {

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getPlayableRange()
     */
    public int[] getPlayableRange() {
        return new int[] { JMC.C3, JMC.E5 };
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getName()
     */
    public String getName() {
        return "Viola";
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getID()
     */
    public int getID() {
        return JMC.VIOLA;
    }
}
