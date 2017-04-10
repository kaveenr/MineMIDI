package com.kaveenrodrigo.minemidi.midi;

import javax.sound.midi.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by pc on 4/10/2017.
 */
public class MidiCtrl {
    private static MidiCtrl instance;

    MidiDevice outputDevice;
    Receiver deviceRx;
    MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();
    BlockingQueue<ShortMessage> queue = new LinkedBlockingQueue<ShortMessage>();
    Thread noteHandler;

    public MidiCtrl(String device_name){
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info device: infos) {
            if(device.getName().equals(device_name)) {
                try {
                    outputDevice = MidiSystem.getMidiDevice(device);
                    outputDevice.open();
                    deviceRx = outputDevice.getReceiver();
                    noteHandler = new Thread(new NoteQueue(this.queue,deviceRx));
                    noteHandler.start();
                } catch (MidiUnavailableException mx){
                    outputDevice = null;
                    System.out.println("Error Occurred while connecting to "+ device_name);
                    System.out.println(mx.getMessage());
                }
            }
        }
        if (outputDevice == null){
            System.out.println(device_name +" not found");
        }else{
            System.out.println("Connected to "+ device_name);
        }
    }

    public void startNote(boolean state,int channel,int note,int velocity){
        ShortMessage onMessage = new ShortMessage();
        try {
            onMessage.setMessage(state ? ShortMessage.NOTE_ON : ShortMessage.NOTE_OFF, channel, note, velocity);
        } catch (InvalidMidiDataException e) {
            System.out.println("Error parsing MIDI Data");
        }
        try {
            queue.put(onMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static MidiCtrl getInstance(String device_name){
        if (instance == null){
            instance = new MidiCtrl(device_name);
        }
        return instance;
    }
}

