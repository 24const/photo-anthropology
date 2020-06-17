package com.nsu.photo_anthropology.repositories;

import com.nsu.photo_anthropology.entities.Files;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<Files, Long> {
}
