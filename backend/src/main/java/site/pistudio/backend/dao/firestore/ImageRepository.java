package site.pistudio.backend.dao.firestore;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import site.pistudio.backend.entities.firestore.Image;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends DatastoreRepository<Image, UUID> {
    void deleteImageById(UUID id);

    Image findImageById(UUID id);

    List<Image> findImagesByOrderNumber(long orderNumber);
}
