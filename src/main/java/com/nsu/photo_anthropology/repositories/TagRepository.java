package com.nsu.photo_anthropology.repositories;

import com.nsu.photo_anthropology.entities.Tags;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tags, Long> {
}
