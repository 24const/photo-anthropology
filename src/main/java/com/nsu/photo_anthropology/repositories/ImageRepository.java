package com.nsu.photo_anthropology.repositories;

import com.nsu.photo_anthropology.entities.Images;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Images, Long> {
}
