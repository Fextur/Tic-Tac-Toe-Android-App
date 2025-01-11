package com.example.tictactoe

import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Grid
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gameBoard: GridLayout = findViewById(R.id.gameBoard)
        val resetButton: MaterialButton = findViewById(R.id.resetButton)
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        for(i in 0 until 3) {
            for (j in 0 until 3) {
                val button = MaterialButton(this)
                button.textSize = 80f
                button.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                button.setBackgroundResource(R.drawable.button_border)

                button.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i, 1f)
                    columnSpec = GridLayout.spec(j, 1f)
                    width = 0
                    height = 0
                    setMargins(4, 4, 4, 4)
                }

                button.setOnClickListener {
                    if( viewModel.makeMove(i, j) { currentPlayer: Char ->
                            button.text = currentPlayer.toString()
                            button.isEnabled = false;
                        }) {
                        supportActionBar?.subtitle = ""
                        if (viewModel.winner.value != null)  supportActionBar?.title = "Player ${viewModel.winner.value} wins!"
                        else if (viewModel.isDraw.value == true) supportActionBar?.title = "It's a draw!"
                        else supportActionBar?.title = "Player ${viewModel.currentPlayer.value}'s Turn"
                    }
                }
                gameBoard.addView(button)
            }
        }

        viewModel.currentPlayer.observe(this, Observer { player ->
            supportActionBar?.title = "Player $player's Turn"
        })

        resetButton.setOnClickListener{
            viewModel.resetGame()

            supportActionBar?.subtitle = "Let the game begin!"

            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    val button = (gameBoard.getChildAt(i * 3 + j) as MaterialButton)
                    button.text = ""
                    button.isEnabled = true
                }
            }
        }
    }


}