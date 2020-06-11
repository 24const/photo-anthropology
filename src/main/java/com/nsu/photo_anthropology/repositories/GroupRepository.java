package com.nsu.photo_anthropology.repositories;

import com.nsu.photo_anthropology.entities.Groups;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Groups, Long> {
}
