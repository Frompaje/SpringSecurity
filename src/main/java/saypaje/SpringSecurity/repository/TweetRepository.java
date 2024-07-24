package saypaje.SpringSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saypaje.SpringSecurity.entities.Tweet;

import java.util.UUID;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, UUID> {
}
