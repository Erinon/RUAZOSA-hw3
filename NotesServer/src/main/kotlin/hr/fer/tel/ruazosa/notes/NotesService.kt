package hr.fer.tel.ruazosa.notes

import java.util.*

/**
 * Created by dejannovak on 09/05/2018.
 */

interface NotesService {
    fun findNoteById(noteId: Long): Note?
    fun fetchNotes(): List<Note>
    fun saveNote(note: Note)
    fun findAllNotes(): List<Note>
    fun deleteNoteById(noteId: Long)
}