package site.pistudio.backend.dao.mysql;

import org.springframework.data.repository.CrudRepository;
import site.pistudio.backend.entities.mysql.Image;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends CrudRepository<Image, UUID> {
    List<Image> findImagesByOrderNumber(long id);

    void deleteImageById(UUID id);

    Image findImageById(UUID id);
}
