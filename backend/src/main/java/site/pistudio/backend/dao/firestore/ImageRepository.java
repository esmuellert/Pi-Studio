package site.pistudio.backend.dao.firestore;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import site.pistudio.backend.entities.firestore.Image;

import java.util.UUID;

public interface ImageRepository extends DatastoreRepository<Image, UUID> {
    void deleteImageById(UUID id);

    Image findImageById(UUID id);
}
