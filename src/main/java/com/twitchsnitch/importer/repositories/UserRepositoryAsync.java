package com.twitchsnitch.importer.repositories;

import com.twitchsnitch.importer.entity.User;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UserRepositoryAsync extends ReactiveNeo4jRepository<User, Long> {

    @Query("MATCH (c:User) WHERE c.twitch_followers_from_update_date IS NULL AND c.twitch_id IS NOT NULL RETURN c.twitch_id")
    Flux<String> getAllUsersWithoutFollowersFrom();

}
