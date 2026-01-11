package com.blockdude.game.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class SoundManager(context: Context) {

    private val soundPool: SoundPool
    private var moveSound: Int = 0
    private var pickUpSound: Int = 0
    private var placeSound: Int = 0
    private var levelCompleteSound: Int = 0
    private var soundsLoaded = false

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        soundPool.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                soundsLoaded = true
            }
        }

        // Load sounds - these will be generated programmatically
        // For now, we'll create simple beeps using the tone generator approach
        // In production, you'd load actual sound files from res/raw
    }

    fun playMove() {
        if (soundsLoaded && moveSound != 0) {
            soundPool.play(moveSound, 0.5f, 0.5f, 1, 0, 1.2f)
        }
    }

    fun playPickUp() {
        if (soundsLoaded && pickUpSound != 0) {
            soundPool.play(pickUpSound, 0.7f, 0.7f, 1, 0, 1.0f)
        }
    }

    fun playPlace() {
        if (soundsLoaded && placeSound != 0) {
            soundPool.play(placeSound, 0.7f, 0.7f, 1, 0, 0.8f)
        }
    }

    fun playLevelComplete() {
        if (soundsLoaded && levelCompleteSound != 0) {
            soundPool.play(levelCompleteSound, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    fun release() {
        soundPool.release()
    }
}
