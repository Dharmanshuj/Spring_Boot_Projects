package com.example.test.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.test.Entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query(value = "INSERT INTO user (id, name, email, password) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    void saveUser(@Param("id") int id, @Param("name") String name, @Param("email") String email, @Param("password") String password);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIdNot(String email, int id);

    List<User> findAllByEmailIn(List<String> emails);
}
