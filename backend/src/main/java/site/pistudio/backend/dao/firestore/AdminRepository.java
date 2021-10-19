package site.pistudio.backend.dao.firestore;

import com.google.cloud.datastore.Key;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import site.pistudio.backend.entities.firestore.Admin;

public interface AdminRepository extends DatastoreRepository<Admin, Key> {
    Admin findByUsername(String username);
}
