package hr.fer.tel.ruazosa.lecture6.notes.net

import hr.fer.tel.ruazosa.lecture6.notes.NotesModel
import retrofit.http.*

interface NotesService {
    @get:GET("/notes")
    val listOfNotes: List<NotesModel.Note>

    @GET("/notes/{id}")
    fun getNote(@Path("id") id: Long?): NotesModel.Note

    @DELETE("/notes/{id}")
    fun deleteNote(@Path("id") id: Long?): Void?

    @POST("/notes")
    fun addNote(@Body note: NotesModel.Note): Void?

    @PUT("/notes")
    fun updateNote(@Body note: NotesModel.Note): Void?
}