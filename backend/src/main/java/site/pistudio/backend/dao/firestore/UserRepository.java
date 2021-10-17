package site.pistudio.backend.dao.firestore;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.firestore.User;

import java.util.UUID;

@Repository
public interface UserRepository extends DatastoreRepository<User, UUID> {
    User findUserById(UUID id);
    User findUserByOpenId(String openId);
}
