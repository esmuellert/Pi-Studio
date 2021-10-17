package site.pistudio.backend.dao.firestore;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import site.pistudio.backend.entities.firestore.Admin;

public interface AdminRepository extends DatastoreRepository<Admin, Long> {
    Admin findAdminByUsername(String username);
}
