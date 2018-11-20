package com.characterGen.characterservice.Repository;

import com.characterGen.characterservice.Entity.CharacterGen;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterGenRepo extends CrudRepository<CharacterGen, Long> {
}
