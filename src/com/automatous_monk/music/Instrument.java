/*
 * Created on Apr 18, 2004
 */
package com.automatous_monk.music;

/**
 * @author Paul Reiners
 */
public interface Instrument {

    int[] getPlayableRange();
    String getName();
    int getID();
}
