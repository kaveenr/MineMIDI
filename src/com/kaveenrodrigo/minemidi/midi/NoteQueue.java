package com.kaveenrodrigo.minemidi.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

/**
 * Created by pc on 4/10/2017.
 */
class NoteQueue implements Runnable {
    private final BlockingQueue<ShortMessage> queue;
    private Receiver rx;
    private Timer decayTimer =  new java.util.Timer();

    NoteQueue(BlockingQueue<ShortMessage> q, Receiver rx) {
        this.queue = q;
        this.rx = rx;
    }

    public void run() {
        try {
            while (true){
                ShortMessage cur = this.queue.take();
                this.rx.send(cur,-1);
                TimerTask decayTask = new java.util.TimerTask() {
                    @Override
                    public void run() {
                        ShortMessage end = new ShortMessage();
                        try {
                            end.setMessage(ShortMessage.NOTE_OFF,cur.getChannel(),cur.getData1(),cur.getData2());
                        } catch (InvalidMidiDataException e) {
                        }
                        rx.send(end,-1);
                    }
                };
                decayTimer.schedule(decayTask, 1000);
            }

        } catch (Exception e) {
            System.out.println("Error in MIDI Queue");
        }
    }
}
