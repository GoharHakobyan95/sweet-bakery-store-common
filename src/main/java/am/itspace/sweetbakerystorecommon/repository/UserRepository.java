package am.itspace.sweetbakerystorecommon.repository;

import am.itspace.sweetbakerystorecommon.entity.Role;
import am.itspace.sweetbakerystorecommon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);

    Optional<User> findByEmailAndVerifyToken(String email, String token);

    Optional<User> findByRole(Role role);

    long count();

    @Transactional
    @Query("UPDATE User u SET u.isActive = true WHERE u.id = ?1")
    @Modifying
   void enable(Integer id);

    Optional<User> getByEmail(String email);
}
