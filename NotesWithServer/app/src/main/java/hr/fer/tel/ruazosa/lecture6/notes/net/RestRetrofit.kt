package hr.fer.tel.ruazosa.lecture6.notes.net

import hr.fer.tel.ruazosa.lecture6.notes.NotesModel
import retrofit.RestAdapter

class RestRetrofit : RestInterface {

    private val BASE_IP = "10.0.2.2"
    private val service: NotesService

    init {
        val baseURL = "http://$BASE_IP:8080/api/"

        val retrofit = RestAdapter.Builder()
                .setEndpoint(baseURL)
                .build()

        service = retrofit.create(NotesService::class.java)
    }

    override fun getListOfNotes(): List<NotesModel.Note>? {
        return service.listOfNotes
    }

    override fun getNote(id: Long?): NotesModel.Note? {
        return service.getNote(id)
    }

    override fun deleteNote(id: Long) {
        service.deleteNote(id)
    }

    override fun addNote(note: NotesModel.Note) {
        service.addNote(note)
    }

    override fun updateNote(note: NotesModel.Note) {
        service.updateNote(note)
    }

}