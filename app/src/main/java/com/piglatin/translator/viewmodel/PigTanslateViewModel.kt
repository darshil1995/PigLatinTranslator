package com.piglatin.translator.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PigTanslateViewModel : ViewModel() {
    var translate: MutableLiveData<String> = MutableLiveData("")

    /**
     * get Pig Latin Word for our English sentence/words
     */
    fun getLatin(word: String) {
        Log.e("TAG", "" + word)
        translate.postValue(pigLatin(word))
    }

    /**
     * Method to translate the phrase word by word
     *
     * @param sentence
     * the sentence in English
     * @return the pig latin translation
     */
    fun pigLatin(sentence: String): String {
        var latin = ""
        var i = 0
        while (i < sentence.length) {
            // First taking care of the punctuation
            while (i < sentence.length && !isLetter(sentence[i])) {
                latin += sentence[i]
                i++
            }

            // if there aren't any words left, break the operation.
            if (i >= sentence.length) break

            // Else we're at the beginning of the word
            val begin = i
            while (i < sentence.length && isLetter(sentence[i])) {
                i++
            }

            // now we're at the end of a word, so translate it
            val end = i
            latin += pigWord(sentence.substring(begin, end))
        }
        return latin
    }

    /**
     * Function to test whether a character is a letter or not
     *
     * @param character
     * the character to test
     * @return True if that character is a letter
     */
    private fun isLetter(character: Char): Boolean {
        return character in 'A'..'z' || character in 'a'..'z'
    }

    /**
     * Function to translate one word into pig latin word
     *
     * @param word
     * the word in english
     * @return the pig latin version
     */
    private fun pigWord(word: String): String? {
        val split = firstVowel(word)
        return if (split == 0) {
            word.substring(split) + word.substring(0, split) + "way"
        } else {
            word.substring(split) + word.substring(0, split) + "ay"
        }
    }

    /**
     * Function to find the index of the first vowel in a word.
     *
     * @param word
     * the word to search
     * @return the index of the first vowel
     */
    private fun firstVowel(strWord: String): Int {
        strWord.lowercase().also { word ->
            for (i in word.indices) if (
                word[i] == 'a' ||
                word[i] == 'e' ||
                word[i] == 'i' ||
                word[i] == 'o' ||
                word[i] == 'u'
            ) return i
        }
        return 0
    }
}