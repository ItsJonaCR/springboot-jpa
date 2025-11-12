package com.jonathan.springboot.jpa.springboot_jpa;

import java.lang.classfile.ClassFile.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jonathan.springboot.jpa.springboot_jpa.entities.Person;
import com.jonathan.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	// This method is executed when the app is executed
	@Override
	public void run(String... args) throws Exception {

		findOne();

	}

	public void findOne() {

		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(1L);
		// if(optionalPerson.isPresent()) {
		// 	person = optionalPerson.get();
		// }
		// System.out.println(person);

		repository.findById(1L).ifPresent(System.out::println);
	}

	public void list() {
		// List<Person> persons = (List<Person>) repository.findAll();
		// List<Person> persons = (List<Person>)
		// repository.findByProgrammingLanguage("Java");
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andres");

		persons.stream().forEach(person -> System.out.println(person));

		List<Object[]> personsValues = repository.fetchPersonData("Python", "Pepe");
		personsValues.stream().forEach(person -> System.out.println(person[0] + " is expert in " + person[1]));
		personsValues.stream().forEach(person -> System.out.println(person));
	}
}
