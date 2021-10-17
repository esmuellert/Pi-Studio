package site.pistudio.backend.dao.mysql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.mysql.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findAdminByUsername(String username);
}
