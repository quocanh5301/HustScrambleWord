package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var _score = 0
    private var _currentWordCount = 0
    private var _currentScrambledWord:String = "test"

    private var _currentScrambleWordLiveData:MutableLiveData<String> = MutableLiveData()
    private var _currentWord = "test"

    val score
        get() = _score
    val currentWordCount
        get() = _currentWordCount
    val currentScrambledWord:String
        get() = _currentScrambledWord

    val currentScrambleWordLiveData:LiveData<String>
        get() = _currentScrambleWordLiveData

    val _wordList = mutableListOf<String>()

    init {
        getNextWord()
    }

    fun reset(){
        _score = 0
        _currentWordCount = 0
        _currentScrambledWord = "test"

//        _currentScrambleWordLiveData = MutableLiveData()
        _currentWord = "test"
        _wordList.clear()
        getNextWord()
    }

    fun getNextWord()
    {
        val allWords = allWordsList
        _currentWord = allWords.random()

        if(_wordList.contains(_currentWord))
        {
            getNextWord()
        }
        Log.i("getNextWord: ", _currentWord)

        val arrayLetters = _currentWord.toCharArray()
        arrayLetters.shuffle()

        _currentScrambledWord = String(arrayLetters)
        _currentScrambleWordLiveData.value = _currentScrambledWord
        _currentWordCount++
        _wordList.add(_currentWord)
    }

    fun skipWord():Boolean
    {
        if(_currentWordCount < MAX_NO_OF_WORDS - 1)
        {
            getNextWord()
            return true
        }
        return false
    }

    fun checkPlayerWord(playerWord:String):Boolean
    {
        if(_currentWord.equals(playerWord)) {
            _score += SCORE_INCREASE
            if(_currentWordCount < MAX_NO_OF_WORDS)
            {
                getNextWord()
            }
            return true
        }
        else return false
    }

}