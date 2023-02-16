package com;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.entity.UserEntity;

/**
 * Hello world!
 *
 */

/*
 * hibernate 6 interfaces
 * configuration,session,sessionfactory,query,Transaction,criteria
 */

/*
 * Session created using sessionfactory -> sessionfactory will read all
 * configuration session -> query execute //insert list delete update
 * 
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Project Started!");

		Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();

		Session session = sf.openSession(); // we can run the query using session

		UserEntity user = new UserEntity();

		// user.setUserName("Rutvi");
		// user.setEmail("thakkarrutvi2002@gmail.com");
		// user.setPassword("rutvi2002");

		session.save(user);

		// user.setUserName(userName);
		// user.setPassword(password);
		// user.setEmail(email);

		System.out.println(session);

		int ch;
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("0. for Exit");
			System.out.println("1. for Insert");
			System.out.println("2. for Update");
			System.out.println("3. for Delete");
			System.out.println("4. for List");

			System.out.println("Enter Your choice:");
			ch = sc.nextInt();

			switch (ch) {

			case 0:
				session.close();
				System.exit(0);
				break;

			case 1:
				// user.setUserName("Rutvi");
				// user.setEmail("thakkarrutvi2002@gmail.com");
				// user.setPassword("rutvi2002");

				System.out.println("Enter Your Name: ");
				user.setUserName(sc.next());

				System.out.println("Enter Your email: ");
				user.setEmail(sc.next());

				System.out.println("Enter Your password: ");
				user.setPassword(sc.next());

				session.save(user); // For Insert operation
				break;

			case 2:
				System.out.println("Enter UserId you want to update: ");
				int userId = sc.nextInt();
				UserEntity user1 = session.get(UserEntity.class, userId);
				if (user1 == null) {
					System.out.println("UserId is Wrong!");

				} else {
					// DDL - Insert , Update , Delete
					Transaction tx = session.beginTransaction();
					System.out.println("Your UserName is " + user1.getUserName());
					System.out.println("Enter New UserName or enter old to remain same : ");
					user1.setUserName(sc.next());

					System.out.println("Your Email is " + user1.getEmail());
					System.out.println("Enter New Email or enter old to remain same : ");
					user1.setEmail(sc.next());
					// email, password
					System.out.println("Your Password is " + user1.getEmail());
					System.out.println("Enter New Password or enter old to remain same : ");
					user1.setPassword(sc.next());

					session.update(user1);
					tx.commit();

				}
				break;

			case 3:
				// delete from users where userId=1
				System.out.println("Enter UserId you want to delete: ");
				userId = sc.nextInt();
				Transaction tx = session.beginTransaction();
				user1 = session.get(UserEntity.class, userId);
				try {
					// company -> deduct 500000 -> 400000
					// user -> add -> add 100000
					session.delete(user1); // if any error generate than catch block will be execute.
				} catch (Exception e) {
					tx.rollback();
				}
				// commit
				tx.commit(); // COMMIT in SQL is a transaction control language that is used to permanently
								// save the changes done in the transaction in tables/databases.
				break;

			case 4:
				Query query = session.createQuery("from UserEntity");// give me all records of the //
																		// UserEntity class
				//
				List<UserEntity> users = query.getResultList();
				for (UserEntity userEntity : users) {
					System.out.println(userEntity.getUserId());
					System.out.println(userEntity.getUserName());
					System.out.println(userEntity.getEmail());
					System.out.println(userEntity.getPassword());

				}

				break;

			default:

				break;
			}
			session.close();

		}

	}
}
