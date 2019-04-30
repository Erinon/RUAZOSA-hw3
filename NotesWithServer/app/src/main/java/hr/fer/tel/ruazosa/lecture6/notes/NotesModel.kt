package hr.fer.tel.ruazosa.lecture6.notes

import java.io.Serializable

/**
 * Created by dejannovak on 09/04/2018.
 */
object NotesModel {

    data class Note (
            var noteId: Long? = null,
            var noteTitle: String? = null,
            var noteDescription: String? = null,
            var noteDate: String? = null
    ) : Serializable

    var notesList: MutableList<Note> = mutableListOf()
}