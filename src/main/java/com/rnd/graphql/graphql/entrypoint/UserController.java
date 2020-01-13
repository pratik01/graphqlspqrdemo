package com.rnd.graphql.graphql.entrypoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rnd.graphql.graphql.models.User;
import com.rnd.graphql.graphql.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService uService;
	
	@GetMapping
	public List<User> findAll(){
		try {
			return uService.findAll();
		} catch (ReflectiveOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
