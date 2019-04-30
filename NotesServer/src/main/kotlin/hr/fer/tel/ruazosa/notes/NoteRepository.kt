package hr.fer.tel.ruazosa.notes

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository

/**
 * Created by dejannovak on 08/05/2018.
 */
/**
 * Created by dejannovak on 08/05/2018.
 */
@Repository
@RepositoryRestResource(exported = true, path="notes")
interface NoteRepository: JpaRepository<Note, Long> {
}