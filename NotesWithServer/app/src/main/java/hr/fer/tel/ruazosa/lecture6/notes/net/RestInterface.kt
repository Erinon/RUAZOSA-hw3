package hr.fer.tel.ruazosa.lecture6.notes.net

import hr.fer.tel.ruazosa.lecture6.notes.NotesModel

interface RestInterface {
    fun getListOfNotes(): List<NotesModel.Note>?
    fun getNote(id: Long?): NotesModel.Note?
    fun deleteNote(id: Long)
    fun addNote(note: NotesModel.Note)
    fun updateNote(note: NotesModel.Note)
}