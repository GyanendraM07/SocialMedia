package com.meeting.management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.meeting.management.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>{
List<Comment> findByMeetingId(String meetingId);
}
