package com.rnd.graphql.graphql.entrypoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rnd.graphql.graphql.models.User;
import com.rnd.graphql.graphql.service.UserService;

//@GraphQLApi
@Component
public class UserEntry {

	@Autowired
	private UserService uService;

	// @GraphQLQuery(name = "üsers")
	public List<User> users() throws ReflectiveOperationException {
		return uService.findAll();
	}
}
