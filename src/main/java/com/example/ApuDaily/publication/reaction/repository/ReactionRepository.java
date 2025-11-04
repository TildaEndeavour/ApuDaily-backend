package com.example.ApuDaily.publication.reaction.repository;

import com.example.ApuDaily.publication.reaction.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    @Query("SELECT r FROM Reaction r WHERE r.user.id = :userId AND r.targetType.id = :targetTypeId AND r.entityId = :entityId")
    Optional<Reaction> findReactionFromTarget(
            @Param("userId") Long userId,
            @Param("targetTypeId") Long targetTypeId,
            @Param("entityId") Long entityId
    );
}
