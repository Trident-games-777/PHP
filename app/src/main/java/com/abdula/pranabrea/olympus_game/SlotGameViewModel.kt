package com.abdula.pranabrea.olympus_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdula.pranabrea.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SlotGameViewModel : ViewModel() {
    private val _model = MutableStateFlow<SlotGameModel>(SlotGameModel.Idle(getRandomSlots()))
    val model: StateFlow<SlotGameModel> = _model

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _model.collect {
                when (it) {
                    is SlotGameModel.Spinning -> {
                        val slots = getRandomSlots()
                        delay(300) // Simulate spinning time
                        val win = checkWin(slots)
                        _model.update { SlotGameModel.Result(slots, win) }
                    }
                    else -> { /* Do nothing */ }
                }
            }
        }
    }

    fun processIntent(intent: SlotGameIntent) {
        when (intent) {
            is SlotGameIntent.Spin -> {
                val slots = getRandomSlots()
                _model.value = SlotGameModel.Spinning(slots)
            }
        }
    }

    private fun getRandomSlots(): List<Int> {
        return listOf(
            R.drawable.one,
            R.drawable.one,
            R.drawable.one,
            R.drawable.one,
            R.drawable.one,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.six,
            R.drawable.six,
            R.drawable.six,
            R.drawable.six,
            R.drawable.one,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.one,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.one,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six
        ).shuffled().take(3)
    }

    private fun checkWin(slots: List<Int>): Boolean {
        return slots.distinct().size == 1
    }
}
