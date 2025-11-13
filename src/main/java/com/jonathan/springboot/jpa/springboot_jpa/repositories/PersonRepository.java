package com.jonathan.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jonathan.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    // CHECK DOCUMENTATION FOR QUERY METHODS IN SPRING DATA
    
    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    // like: used to filter and the "%" is used to search from the left to the right of the word 
    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByName(String name);

    // same as using like but without the @Query already declared by spring data
    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    // ?1 means first parameter and ?2 second parameter
    @Query("SELECT p FROM Person p WHERE p.programmingLanguage=?1 AND p.name=?2")
    List<Person> findByProgrammingLanguageAndNameV1(String programmingLanguage, String name);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> fetchPersonData(String programmingLanguage, String name);

    @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    List<Object[]> fetchPersonDataNyName(String name);

    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> fetchPersonData();
}
