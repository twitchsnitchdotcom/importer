package com.twitchsnitch.importer.repositories;

import com.twitchsnitch.importer.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH (u:User) WHERE u.twitch_id IS NULL AND u.sully_id IS NULL RETURN u.login")
    Set<String> getAllUsersWithoutAnyIds();
}
