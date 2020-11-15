package site.pistudio.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findAdminByUsername(String username);
}
