package site.pistudio.backend.dao.firestore;

import com.google.cloud.datastore.Key;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.firestore.User;

import java.util.UUID;

@Repository
public interface UserRepository extends DatastoreRepository<User, Key> {
    User findUserById(UUID id);
    User findUserByOpenId(String openId);
}
