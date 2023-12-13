/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding

/**
 * Fragment where the game is played, contains the game logic.
 */
class GameFragment : Fragment(), DialogBtn {

    val dialog = CutomDialog(this)

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding
    val viewModel:GameViewModel by activityViewModels()
    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        //binding = GameFragmentBinding.inflate(inflater, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // Update the UI
        updateNextWordOnScreen()
        //binding.score.text = getString(R.string.score, 0)
        //binding.wordCount.text = getString(
        //        R.string.word_count, 0, MAX_NO_OF_WORDS)
    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    */
    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()
        if (viewModel.currentWordCount != 10){
            if(viewModel.checkPlayerWord(playerWord) == true)
            {
                setErrorTextField(false)
                updateNextWordOnScreen()
            }
            else
            {
                setErrorTextField(true)
            }
        } else{
            dialog.show(requireContext(), "Congratulations!", getDialogMess())
        }
    }

    /*
     * Skips the current word without changing the score.
     * Increases the word count.
     */
    private fun onSkipWord() {
        if(viewModel.skipWord() == true) {
            setErrorTextField(false)
            updateNextWordOnScreen()
        }else
        {
            dialog.show(requireContext(), "Congratulations!", getDialogMess())
        }
    }

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reset();
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /*
     * Displays the next scrambled word on screen.
     */
    private fun updateNextWordOnScreen() {
        binding.score.text = viewModel.score.toString()
        binding.wordCount.text = "${viewModel.currentWordCount} of 10 words"
    }

    private fun getDialogMess() : String {
        //binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord
        return "You scored: ${viewModel.score}"
    }

    override fun onConfirm() {
        restartGame()
    }

    override fun onDeny() {
        exitGame()
    }
}
