package hr.fer.tel.ruazosa.lecture6.notes

import android.annotation.SuppressLint
import android.app.Activity
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import hr.fer.tel.ruazosa.lecture6.notes.net.RestRetrofit
import java.text.SimpleDateFormat
import java.util.*

class EnterNewNote : AppCompatActivity() {

    private var noteTitle: EditText? = null
    private var noteDescription: EditText? = null
    private var storeButton: Button? = null
    private var cancelButton: Button? = null
    private var forEdit: Boolean = false

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_new_note)

        noteTitle = findViewById(R.id.note_title)
        noteDescription = findViewById(R.id.note_description)
        storeButton = findViewById(R.id.store_button)
        cancelButton = findViewById(R.id.cancel_button)

        var id: Long? = null
        val title: String?
        val description: String?

        val extras = intent.extras

        if (extras != null) {
            id = extras.getLong("noteId")
            title = extras.getString("noteTitle")
            description = extras.getString("noteDescription")

            (noteTitle as EditText).setText(title)
            (noteDescription as EditText).setText(description)

            forEdit = true
        }

        storeButton?.setOnClickListener {
            val dateFormatter = SimpleDateFormat("dd'.'MM'.'yyyy ',' HH:mm")
            val dateAsString = dateFormatter.format(Date())

            val note = NotesModel.Note(noteTitle = noteTitle?.text.toString(),
                    noteDescription = noteDescription?.text.toString(), noteDate = dateAsString)

            if (note.noteTitle.isNullOrBlank() || note.noteDescription.isNullOrBlank()) {
                Toast.makeText(this,"Please provide both title and description!", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            if (forEdit) {
                note.noteId = id

                UpdateNoteTask().execute(note)
            } else {
                AddNoteTask().execute(note)
            }

            LoadNotesTask().execute().get()

            setResult(Activity.RESULT_OK)

            finish()
        }

        cancelButton?.setOnClickListener {
            finish()
        }
    }

    private inner class AddNoteTask: AsyncTask<NotesModel.Note, Void, Void>() {
        override fun doInBackground(vararg note: NotesModel.Note?): Void? {
            val rest = RestRetrofit()

            rest.addNote(note[0]!!)

            return null
        }
    }

    private inner class UpdateNoteTask: AsyncTask<NotesModel.Note, Void, Void>() {
        override fun doInBackground(vararg note: NotesModel.Note?): Void? {
            val rest = RestRetrofit()

            rest.updateNote(note[0]!!)

            return null
        }
    }

    private inner class LoadNotesTask: AsyncTask<Void, Void, List<NotesModel.Note>?>() {
        override fun doInBackground(vararg params: Void): List<NotesModel.Note>? {
            val rest = RestRetrofit()

            return rest.getListOfNotes()
        }

        override fun onPostExecute(notes: List<NotesModel.Note>?) {
            updateNotesList(notes)
        }
    }

    private fun updateNotesList(notes: List<NotesModel.Note>?) {
        if(notes != null) {
            NotesModel.notesList.removeAll { true }
            NotesModel.notesList.addAll(notes)
        }
    }
}