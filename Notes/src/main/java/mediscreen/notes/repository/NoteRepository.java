package mediscreen.notes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mediscreen.notes.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

}
