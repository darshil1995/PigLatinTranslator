package com.piglatin.translator.view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.piglatin.translator.R
import com.piglatin.translator.viewmodel.PigTanslateViewModel
import java.util.*

class PigTranslateFragment : Fragment(R.layout.fragment_pigtranslate) {
    private val EXTRA_TEXT_KEY: String = "textKey"
    lateinit var editText: EditText
    lateinit var textViewTranslated: TextView
    lateinit var btnTranslate: Button
    lateinit var pigTanslateViewModel: PigTanslateViewModel
    lateinit var textToSpeech: TextToSpeech
    var isFromClick: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pigTanslateViewModel = PigTanslateViewModel()

        initViews(view)
        onCLickListeners()
        viewModelObservers()
    }

    private fun initViews(view: View) {
        editText = view.findViewById<EditText>(R.id.edit_message)
        textViewTranslated = view.findViewById<TextView>(R.id.textViewTranslated)
        btnTranslate = view.findViewById<Button>(R.id.btnTranslate)
    }

    private fun onCLickListeners() {
        btnTranslate.setOnClickListener {
            isFromClick = true
            pigTanslateViewModel.getLatin(editText.text.toString())
        }
    }

    private fun viewModelObservers() {
        pigTanslateViewModel.translate.observe(viewLifecycleOwner) { latin ->
            if (isFromClick) {
                when {
                    latin.isEmpty() -> {
                        Toast.makeText(
                            context,
                            "Please enter text to translate",
                            Toast.LENGTH_SHORT
                        ).show()
                        textViewTranslated.text = latin
                        textViewTranslated.visibility = View.INVISIBLE
                    }
                    else -> {
                        textViewTranslated.text = latin
                        textViewTranslated.visibility = View.VISIBLE
                        textToSpeech.speak(
                            textViewTranslated.getText().toString(),
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        );
                    }
                }
            }
        }

        textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener
        {
            if (it != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.CANADA)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_TEXT_KEY, textViewTranslated.text.toString());
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val data = savedInstanceState.getString(EXTRA_TEXT_KEY)
            when {
                data.isNullOrEmpty() -> {
                    textViewTranslated.visibility = View.INVISIBLE
                    return
                }
                else -> {
                    textViewTranslated.visibility = View.VISIBLE
                    textViewTranslated.setText(data);
                }
            }
        }
    }
}