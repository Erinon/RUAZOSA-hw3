package hr.fer.tel.ruazosa.lecture6.notes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import hr.fer.tel.ruazosa.lecture6.notes.net.RestRetrofit
import java.text.SimpleDateFormat

class ListOfNotes : AppCompatActivity() {

    private var fab: FloatingActionButton? = null
    private var listView: ListView? = null
    private var notesAdapter : NotesAdapter? = null
    private val EDIT_NOTE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_notes)

        fab = findViewById(R.id.fab)
        fab?.setOnClickListener {
            val startEnterNewNoteIntent = Intent(this, EnterNewNote::class.java)

            startActivity(startEnterNewNoteIntent)
        }

        listView = findViewById(R.id.list_view)

        notesAdapter = NotesAdapter()
        listView?.adapter = notesAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDIT_NOTE) {
            if (resultCode == Activity.RESULT_OK) {
                notesAdapter?.notifyDataSetChanged()
            }
        }
    }

    inner class NotesAdapter : BaseAdapter() {

        private var notesList = NotesModel.notesList

        init {
            LoadNotesTask().execute()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view: View?
            val vh: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.note_in_list, parent, false)

                vh = ViewHolder(view)

                view.tag = vh
            } else {
                view = convertView

                vh = view.tag as ViewHolder
            }

            vh.noteTitle.text = notesList[position].noteTitle
            vh.noteTime.text = notesList[position].noteDate

            vh.noteDelete.setOnClickListener {
                RemoveNoteTask().execute(getItemId(position))

                NotesModel.notesList.remove(notesList[position])

                this.notifyDataSetChanged()
            }

            vh.noteEdit.setOnClickListener {
                val editNote = Intent(this@ListOfNotes, EnterNewNote::class.java)

                editNote.putExtra("noteId", getItemId(position))
                editNote.putExtra("noteTitle", notesList[position].noteTitle)
                editNote.putExtra("noteDescription", notesList[position].noteDescription)

                startActivityForResult(editNote, EDIT_NOTE)
            }

            return view
        }

        override fun getItem(position: Int): Any {
            return notesList[position]
        }

        override fun getItemId(position: Int): Long {
            return notesList[position].noteId!!
        }

        override fun getCount(): Int {
            return notesList.size
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

            notesAdapter?.notifyDataSetChanged()
        }
    }

    private inner class RemoveNoteTask: AsyncTask<Long, Void, Void>() {
        override fun doInBackground(vararg id: Long?): Void? {
            val rest = RestRetrofit()

            rest.deleteNote(id[0]!!)

            return null
        }
    }

    private class ViewHolder(view: View?) {

        val noteTime = view?.findViewById(R.id.note_time) as TextView
        val noteTitle = view?.findViewById(R.id.note_title) as TextView
        val noteDelete = view?.findViewById(R.id.ivDelete) as ImageView
        val noteEdit = view?.findViewById(R.id.ivEdit) as ImageView

        // if you target API 26, you should change to:
        /*init {
            this.tvTitle = view?.findViewById<TextView>(R.id.tvTitle) as TextView
            this.tvContent = view?.findViewById<TextView>(R.id.tvContent) as TextView
        }*/
    }
}