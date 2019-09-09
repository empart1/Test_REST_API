package client.repository;

import client.domain.Userrrr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Userrrr, Integer> {
}
