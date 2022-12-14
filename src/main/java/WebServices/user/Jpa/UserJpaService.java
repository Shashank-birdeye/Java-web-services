package WebServices.user.Jpa;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import WebServices.user.User;
import WebServices.user.UserNotFoundException;

@RestController
public class UserJpaService {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/jpa/users") 
	public List<User> returnUsers() {
		return userRepository.findAll();
	}
	
	

//	@GetMapping("/users/{id}") 
//	public User returnUser(@PathVariable int id) {
//		 User user = service.findOne(id);
//		 if(user == null) {
//			 throw new UserNotFoundException("User not found with id =" + id);
//		 }
//		 	return user;
//	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<Optional<User>> retrieveUser(@PathVariable int id){
	    Optional<User> user = userRepository.findById(id);
	
	    if(user.isEmpty())
	        throw new UserNotFoundException("id-"+id);
	   
	    EntityModel<Optional<User>> model = EntityModel.of(user);
	    
	    WebMvcLinkBuilder linkToUsers=         
	            linkTo(methodOn(this.getClass()).returnUsers());
	    
	    model.add(linkToUsers.withRel("All-Users"));
	    return model;
	   
	}
	
	
	
	@PostMapping("/jpa/users") 
		public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
			User savedUser =  userRepository.save(user);
			
			
			// CREATED
			///user/{id}     savedUser.getId()
			URI location=ServletUriComponentsBuilder
			    .fromCurrentRequest()
			    .path("/{id}")
			    .buildAndExpand(savedUser.getId()).toUri();
			
			return ResponseEntity.created(location).build();
	}
	
	
	
	
	@DeleteMapping("/jpa/users/{id}") 
	public void deleteUser(@PathVariable int id) {
		   userRepository.deleteById(id);
		
	 //This method will automatically throw an exception if the obj doesnot exist

	}
	
}
