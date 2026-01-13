package com.blockdude.game.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlin.math.sin

class SoundManager(context: Context) {

    private val sampleRate = 22050

    fun playMoveHorizontal() {
        playTone(frequency = 280f, durationMs = 40, volume = 0.3f)
    }

    fun playMoveUp() {
        playTone(frequency = 350f, durationMs = 40, volume = 0.3f)
    }

    fun playPickUp() {
        playKlunk(frequencyStart = 180f, frequencyEnd = 120f, durationMs = 80, volume = 0.5f)
    }

    fun playPlace() {
        playKlunk(frequencyStart = 140f, frequencyEnd = 200f, durationMs = 100, volume = 0.5f)
    }

    fun playLevelComplete() {
        Thread {
            playTone(frequency = 523f, durationMs = 100, volume = 0.6f)
            Thread.sleep(100)
            playTone(frequency = 659f, durationMs = 100, volume = 0.6f)
            Thread.sleep(100)
            playTone(frequency = 784f, durationMs = 200, volume = 0.6f)
        }.start()
    }

    private fun playTone(frequency: Float, durationMs: Int, volume: Float) {
        Thread {
            val numSamples = (sampleRate * durationMs / 1000)
            val samples = ShortArray(numSamples)

            for (i in 0 until numSamples) {
                val envelope = if (i < numSamples / 10) {
                    i.toFloat() / (numSamples / 10)
                } else {
                    1f - ((i - numSamples / 10).toFloat() / (numSamples * 0.9f))
                }.coerceIn(0f, 1f)

                val sample = sin(2.0 * Math.PI * frequency * i / sampleRate) * envelope * volume
                samples[i] = (sample * Short.MAX_VALUE).toInt().toShort()
            }

            playBuffer(samples)
        }.start()
    }

    private fun playKlunk(frequencyStart: Float, frequencyEnd: Float, durationMs: Int, volume: Float) {
        Thread {
            val numSamples = (sampleRate * durationMs / 1000)
            val samples = ShortArray(numSamples)

            for (i in 0 until numSamples) {
                val progress = i.toFloat() / numSamples
                val frequency = frequencyStart + (frequencyEnd - frequencyStart) * progress
                val envelope = (1f - progress * progress).coerceIn(0f, 1f)

                val sample = sin(2.0 * Math.PI * frequency * i / sampleRate) * envelope * volume
                samples[i] = (sample * Short.MAX_VALUE).toInt().toShort()
            }

            playBuffer(samples)
        }.start()
    }

    private fun playBuffer(samples: ShortArray) {
        val bufferSize = samples.size * 2
        val audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setSampleRate(sampleRate)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(bufferSize)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()

        audioTrack.write(samples, 0, samples.size)
        audioTrack.play()

        Thread.sleep((samples.size * 1000L / sampleRate) + 50)
        audioTrack.release()
    }

    fun release() {
        // Nothing to release with AudioTrack approach
    }
}
