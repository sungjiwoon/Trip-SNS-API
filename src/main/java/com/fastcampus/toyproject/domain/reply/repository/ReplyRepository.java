package com.fastcampus.toyproject.domain.reply.repository;

import com.fastcampus.toyproject.domain.reply.entity.Reply;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findAllByTripTripId(Long tripId);

}
