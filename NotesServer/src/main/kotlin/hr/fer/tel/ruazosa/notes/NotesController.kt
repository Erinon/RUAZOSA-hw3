package hr.fer.tel.ruazosa.notes

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by dejannovak on 09/05/2018.
 */
@RestController
class NotesController {

    @Autowired
    lateinit var notesService: NotesService

    @GetMapping("/api/notes/{noteId}")
    fun findNoteById(@PathVariable("noteId") noteId: Long): Note? {
        return notesService.findNoteById(noteId)
    }

    @GetMapping("/api/notes")
    fun findAllNotes() : List<Note> {
        return notesService.findAllNotes()
    }

    @PostMapping("/api/notes")
    fun saveNote(@RequestBody note: Note) {
        return notesService.saveNote(note)
    }

    @DeleteMapping("/api/notes/{noteId}")
    fun deleteNote(@PathVariable("noteId") noteId: Long) {
        return notesService.deleteNoteById(noteId)
    }

    @PutMapping("/api/notes")
    fun updateNote(@RequestBody note: Note) {
        return notesService.saveNote(note)
    }
}