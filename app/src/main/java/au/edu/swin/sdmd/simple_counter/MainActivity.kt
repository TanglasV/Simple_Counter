package au.edu.swin.sdmd.simple_counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {
    private lateinit var scoreView: TextView
    private lateinit var scoreButton: Button
    private lateinit var stealButton: Button
    private lateinit var resetButton: Button
    private var count = 0

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("count", count)
        super.onSaveInstanceState(outState)
        Log.d("MainActivity", "Saved count to savedInstanceState: $count")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreView = findViewById(R.id.score_view)
        scoreButton = findViewById(R.id.score_button)
        stealButton = findViewById(R.id.steal_button)
        resetButton = findViewById(R.id.reset_button)

        scoreButton.setOnClickListener {
            if (count < 15) {
                count++
                updateCount()
                playSound(R.raw.click_sound)
                Log.d("MainActivity", "Score button clicked: new count is $count")
            }
            checkForWin()
        }
        stealButton.setOnClickListener {
            if (count > 0) {
                count--
                updateCount()
                playSound(R.raw.click_sound)
                Log.d("MainActivity", "Steal button clicked: new count is $count")
            }
        }
        resetButton.setOnClickListener {
            count = 0
            updateCount()
            playSound(R.raw.click_sound)
            Log.i("MainActivity", "Reset button clicked: count reset to 0")
        }

        // Restore count if savedInstanceState is not null
        savedInstanceState?.let {
            count = it.getInt("count", 0)
            updateCount()
            Log.d("MainActivity", "Restored count from savedInstanceState: $count")
        }
    }

    private fun updateCount() {
        scoreView.text = count.toString()
        Log.v("MainActivity", "Updated screen with count: $count")
    }

    private fun playSound(soundResourceId: Int) {
        val mediaPlayer = MediaPlayer.create(this, soundResourceId)
        mediaPlayer.setOnCompletionListener { mp -> mp.release() }
        mediaPlayer.start()
        Log.d("MainActivity", "Playing sound for resource ID: $soundResourceId")
    }

    private fun checkForWin() {
        if (count == 15) {
            playSound(R.raw.yayy)
            Log.i("MainActivity", "Win condition met at count: $count")
        }
    }

}