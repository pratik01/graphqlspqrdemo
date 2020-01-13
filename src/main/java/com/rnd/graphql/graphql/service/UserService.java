package com.rnd.graphql.graphql.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rnd.graphql.graphql.models.User;
import com.rnd.graphql.graphql.models.Vehicle;
import com.rnd.graphql.graphql.repository.UserRepository;
import com.rnd.graphql.graphql.service.SelectQueryBuilder.Builder;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityManager em;

	public User createUser(User entity) {
		return userRepository.save(entity);
	}

	@SuppressWarnings({ "unchecked" })
	public List<User> findAll() throws ReflectiveOperationException {

		String parentField[] = { "id", "firstName" };
		String[] childField = { "type" };
		Builder squery = new SelectQueryBuilder.Builder().addParentField(parentField, "u")
				.addChildField("v", childField).from(User.class, "u")
				.on(JoinType.LEFT, Vehicle.class, "v", "user", "id", "u");
		System.out.println(squery.build());

		List<Object[]> list = em.createQuery(squery.build()).getResultList();
		squery.transformation(list);
		/*
		 * list.forEach(t -> { System.out.println(t.getId());
		 * //System.out.println(t.get("id")); //System.out.println(t.get("firstName"));
		 * });
		 */

		return userRepository.findAll();
	}

	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

}
