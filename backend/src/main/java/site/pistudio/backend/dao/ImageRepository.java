package site.pistudio.backend.dao;

import org.springframework.data.repository.CrudRepository;
import site.pistudio.backend.entities.Image;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends CrudRepository<Image, UUID> {
    List<Image> findImagesByOrderNumber(long id);
}
