package com.arrisdev.userservice.repository;

import com.arrisdev.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select name from User  where id in (:pIdList)")
    List<User> findByIdList(@Param("pIdList") List<Long> idList);
}
