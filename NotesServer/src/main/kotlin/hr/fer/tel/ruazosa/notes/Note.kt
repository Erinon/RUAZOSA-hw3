package hr.fer.tel.ruazosa.notes

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Created by dejannovak on 08/05/2018.
 */
@Entity
data class Note(
    @Id @GeneratedValue
    var noteId: Long? = null,
    var noteDate: String? = null,
    var noteTitle: String? = null,
    var noteDescription: String? = null
)
