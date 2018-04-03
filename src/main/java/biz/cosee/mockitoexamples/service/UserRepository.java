package biz.cosee.mockitoexamples.service;

import biz.cosee.mockitoexamples.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
