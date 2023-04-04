package com.maxtrain.prs.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository usRepo;
	@GetMapping
	public ResponseEntity<Iterable<User>> getUsers(){
		Iterable<User> users = usRepo.findAll();
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping ("{id}")
	public ResponseEntity<User> getUser(@PathVariable int id){
		Optional<User> user = usRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	@GetMapping("{username}/{password}")
	public ResponseEntity<User> loginUser(@PathVariable String username, @PathVariable String password){
		Optional<User> user = usRepo.findByUsernameAndPassword(username, password);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<User> postUser(@RequestBody User User){
		User newUser = usRepo.save(User);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	@PutMapping("{id}")
	public ResponseEntity<User> putUser(@PathVariable int id, @RequestBody User user){
		if(user.getId() != id) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	usRepo.save(user);
	return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings({"rawtypes"})
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable int id){
		Optional<User> user = usRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		usRepo.delete(user.get());
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
