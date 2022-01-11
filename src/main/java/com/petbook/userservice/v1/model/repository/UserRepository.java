package com.petbook.userservice.v1.model.repository;

import com.petbook.userservice.v1.model.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUserIdAndPassword(String userId, String password);

	Optional<UserEntity> findByUserId(String userId);
}
