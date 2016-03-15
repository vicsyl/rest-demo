package org.virutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
public class RestDemoApplication {

	/*
	Design a service for storing the following user attributes: name, salutation, email and email preferences.
	Email preferences denote whether the user is interested in obtaining newsletters from the following areas:
	Content → Hollywood, Bollywood, Jazwood; Promo offers and Service updates.

	The data should be persisted in SQL database and published via RESTful API in JSON.
	Design both the database structure and the API. Disregard authentication and authorization.

	Implement endpoint(s) for reading and storing the data in object oriented language of your choice
	(preferab. Moreover, the data should be also synchronized in real-time to a third-party service
	(eg. bulk mailer). Mock the data synchronization with an empty method.

	The results should be delivered as a Git repository, either via public server or compressed in a file.

	Feel free to ask any questions.
	 */

	public static void main(String[] args) {
		SpringApplication.run(RestDemoApplication.class, args);
	}
}
