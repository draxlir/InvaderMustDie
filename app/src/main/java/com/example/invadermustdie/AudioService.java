package com.example.invadermustdie;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

public class AudioService {

    private static final String LOG_TAG = "AudioRecordTest";
    private MediaRecorder recorder;
    private String fileName;
    private boolean isRecording = false;

    public AudioService(String fileName) {
        this.fileName = fileName;
    }

    public void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setAudioSamplingRate(44100);
        recorder.setAudioEncodingBitRate(96000);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
        isRecording = true;
        recorder.getMaxAmplitude();
    }

    public void stopRecording() {
        recorder.stop();
        isRecording = false;
        recorder.release();
        recorder = null;
    }

    public int getSoundLevel() {
        return recorder.getMaxAmplitude();
    }

    public boolean isRecording() { return isRecording; }
}
