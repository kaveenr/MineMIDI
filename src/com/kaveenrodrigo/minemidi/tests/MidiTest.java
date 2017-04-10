package com.kaveenrodrigo.minemidi.tests;

import com.kaveenrodrigo.minemidi.midi.MidiCtrl;

/**
 * Created by pc on 4/10/2017.
 */
public class MidiTest {
    public static void main(String[] params){
        MidiCtrl mc = MidiCtrl.getInstance("MINEMIDI");
        mc.startNote(true,1,54,120);
        mc.startNote(true,2,55,120);
        mc.startNote(true,3,56,120);
        mc.startNote(true,4,56,120);
        mc.startNote(true,5,56,120);

    }
}
