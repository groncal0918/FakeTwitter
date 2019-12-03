package com.titus.faketwitter.tweets.hashtags;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {
    Hashtag findByTag(String tag);
}
