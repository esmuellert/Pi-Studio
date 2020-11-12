package site.pistudio.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.User;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    public User findUserByToken(String token);
}
