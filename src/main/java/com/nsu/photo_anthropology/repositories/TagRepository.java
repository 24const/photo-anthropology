package com.nsu.photo_anthropology.repositories;

import com.nsu.photo_anthropology.entities.Tags;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tags, Long> {
}
