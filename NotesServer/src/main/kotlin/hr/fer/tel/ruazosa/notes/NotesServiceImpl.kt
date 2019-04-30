package hr.fer.tel.ruazosa.notes

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by dejannovak on 09/05/2018.
 */

@Service
class NotesServiceImpl: NotesService {


    @Autowired
    lateinit var notesRepository: NoteRepository

    override fun findNoteById(noteId: Long): Note? {
        return notesRepository.findById(noteId).orElse(null)

    }

    override fun saveNote(note: Note) {
        notesRepository.save(note)
    }

    override fun findAllNotes(): List<Note> {
        return notesRepository.findAll()
    }

    override fun deleteNoteById(noteId: Long) {
        notesRepository.deleteById(noteId)
    }

    override fun fetchNotes(): List<Note> {
        return notesRepository.findAll()
    }


}