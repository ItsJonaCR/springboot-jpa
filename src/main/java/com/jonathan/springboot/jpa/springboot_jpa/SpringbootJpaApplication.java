package com.jonathan.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.jonathan.springboot.jpa.springboot_jpa.entities.Person;
import com.jonathan.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	// This method is called when the app is executed
	@Override
	public void run(String... args) throws Exception {

		delete2();
	}

	@Transactional
	public void delete() {

		repository.findAll().forEach(System.out::println);

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the ID of the person to be deleted");
		Long id = sc.nextLong();
		sc.close();
		repository.deleteById(id);

		repository.findAll().forEach(System.out::println);
	}

	@Transactional
	public void delete2() {

		repository.findAll().forEach(System.out::println);

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the ID of the person to be deleted");
		Long id = sc.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(repository::delete, () -> System.out.println("The user does not exist"));

		sc.close();

		repository.deleteById(id);

		repository.findAll().forEach(System.out::println);
	}

	@Transactional
	public void update() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the ID of the person to be modified");
		Long id = sc.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		if (optionalPerson.isPresent()) {
			Person p = optionalPerson.orElseThrow();
			System.out.println("Enter the programming language of " + p.getName() + " " + p.getLastname()
					+ " to be modified. Actual: " + p.getProgrammingLanguage());
			String programmingLanguage = sc.next();
			p.setProgrammingLanguage(programmingLanguage);
			Person updatedPerson = repository.save(p);
			System.out.println(updatedPerson);
		} else {
			System.out.println("The user does not exist");
		}

		// optionalPerson.ifPresent(p -> {
		// System.out.println("Enter the programming language of " + p.getName() + " " +
		// p.getLastname() + " to be modified. Actual: " + p.getProgrammingLanguage());
		// String programmingLanguage = sc.next();
		// p.setProgrammingLanguage(programmingLanguage);
		// Person personDb = repository.save(p);
		// System.out.println(personDb);

		// });
		sc.close();
	}

	// If you have to manipulate data in DB Tables, use Transactional
	@Transactional
	public void create() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name: ");
		String name = sc.next();
		System.out.println("Enter the lastname: ");
		String lastname = sc.next();
		System.out.println("Enter the programming language: ");
		String programmingLanguage = sc.next();
		sc.close();

		// Null ID because autoincrement
		Person person = new Person(null, name, lastname, programmingLanguage);

		Person newPerson = repository.save(person);
		System.out.println(newPerson);

		repository.findById(person.getId()).ifPresent(System.out::println);
	}

	// When it is only a read transaction, use readOnly = true
	@Transactional(readOnly = true)
	public void findOne() {

		Person person = null;
		Optional<Person> optionalPerson = repository.findByNameContaining("se");
		if (!optionalPerson.isEmpty()) {
			person = optionalPerson.get();
		}
		System.out.println(person);

		// repository.findOneName("Pepe").ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
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
