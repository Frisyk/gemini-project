package com.dicoding.chatgemini

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.chatgemini.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Set click listener for the "sendprompt" button
        binding.sendprompt.setOnClickListener {
            val prompt = binding.promptinput.editText?.text.toString()

            if (prompt.isNotBlank()) {
                // Show loading indicator
                binding.loading.visibility = View.VISIBLE
                binding.textView.text = "" // Clear previous response

                // Send the user's input to the ViewModel
                viewModel.sendMessage(prompt)
            } else {
                // Show an error if the input is empty
                Toast.makeText(this, "Prompt cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe changes to the generated content and update the UI
        observeGeneratedContent()
    }

    private fun observeGeneratedContent() {
        // Observe the generated content
        viewModel.generatedContent.observe(this) { content ->
            // Hide loading indicator
            binding.loading.visibility = View.GONE

            // Update the TextView with the response
            binding.textView.text = content
        }
    }
}
