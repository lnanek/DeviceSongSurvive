/*
 * Created on Apr 18, 2004
 */
package com.automatous_monk.music;

import jm.JMC;

/**
 * @author Paul Reiners
 */
public class Cello implements Instrument {

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getPlayableRange()
     */
    public int[] getPlayableRange() {
        return new int[] { JMC.C2, JMC.E4 };
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getName()
     */
    public String getName() {
        return "Cello";
    }

    /* (non-Javadoc)
     * @see com.automatous_monk.music.Instrument#getID()
     */
    public int getID() {
        return JMC.VIOLIN_CELLO;
    }
}
