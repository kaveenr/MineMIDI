package com.kaveenrodrigo.minemidi;

import com.kaveenrodrigo.minemidi.midi.MidiCtrl;
import org.bukkit.Note;
import org.bukkit.block.NoteBlock;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;
import com.kaveenrodrigo.minemidi.midi.MidiCtrl;



/**
 * Created by pc on 4/10/2017.
 */
public class EventListner implements Listener {

    FileConfiguration fc;

    public EventListner(FileConfiguration fc){
        super();
        this.fc = fc;
    }

    @EventHandler
    public void onNotePlayEvent(NotePlayEvent event){
        NoteBlock evblock = (NoteBlock) event.getBlock().getState();
        handleNoteBlock(evblock,event.getInstrument().ordinal() + 1);
    }

    private void handleNoteBlock(NoteBlock note, int channel){
        MidiCtrl mc = MidiCtrl.getInstance(this.fc.getString("device_name"));
        Note cur = note.getNote();
        mc.startNote(true,channel,note.getNote().getTone().ordinal() +54,120);
    }

}
