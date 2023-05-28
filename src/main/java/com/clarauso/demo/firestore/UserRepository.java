package com.clarauso.demo.firestore;

import com.clarauso.demo.model.entity.UserDocument;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends FirestoreReactiveRepository<UserDocument> {}
