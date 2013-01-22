/*
 * Created on Mar 20, 2004
 */
package com.automatous_monk.music;

/**
 * @author Paul Reiners
 */
public class Key {

    private Tonic tonic;
    private Mode mode;
    private int[] allNaturals;
    private boolean[] isInKey;

    public Key(Mode mode, Tonic tonic) {
        this.mode = mode;
        this.tonic = tonic;
        initializeAllNaturals();
    }

    public Key(Mode mode) {
        this(mode, Tonic.C);
    }

    private void initializeAllNaturals() {
        int lowPitch = tonic.getLowPitch();

        int[] scale = mode.getScale();
        isInKey = new boolean[128];
        int cnt = 0;
        for (int p = 0; p < 128; p++) {
            int diff = absVal(p - lowPitch) % Mode.HALF_STEPS_IN_OCTAVE;
            for (int i = 0; i < scale.length; i++) {
                if ((p >= lowPitch && diff == scale[i])
                    || (p < lowPitch
                        && (diff == 0
                            || diff == Mode.HALF_STEPS_IN_OCTAVE - scale[i]))) {
                    isInKey[p] = true;
                    cnt++;

                    break;
                }
            }
        }

        allNaturals = new int[cnt];
        int index = 0;
        for (int p = 0; p < 128; p++) {
            if (isInKey[p]) {
                allNaturals[index++] = p;
            }
        }
    }

    private int absVal(int n) {
        return n >= 0 ? n : -n;
    }

    public int[] getNaturals(int loNote, int hiNote) {
        int cnt = 0;
        for (int p = loNote; p <= hiNote; p++) {
            if (isInKey[p]) {
                cnt++;
            }
        }
        int[] naturals = new int[cnt];
        int index = 0;
        for (int p = loNote; p <= hiNote; p++) {
            if (isInKey[p]) {
                naturals[index++] = p;
            }
        }

        return naturals;
    }

    public int[] getNaturals() {
        return allNaturals;
    }
    /**
     * @return
     */
    public Mode getMode() {
        return mode;
    }

}
