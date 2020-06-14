package com.nsu.photo_anthropology.repositories;

import com.nsu.photo_anthropology.entities.Files;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<Files, Long> {
}
