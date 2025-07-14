package com.example.DemoApplication.repository;

import com.example.DemoApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}


//findBy, findAllBy, deleteBy, deleteAllBy, existBy, in, notIn, contains, notContains, orderBy
//native query
//findAllByUserEmail(List<String> email)
// save, saveAll