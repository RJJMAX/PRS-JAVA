package com.maxtrain.prs.request;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {
	
	
	private final String Status_REVIEW = "REVIEW";
	private final String Status_APPROVED = "APPROVED";
	private final String Status_REJECTED = "REJECTED";
	
	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping("reviews")
	public ResponseEntity<Iterable<Request>> getRequestInReview(){
		Iterable<Request> requestInReview = reqRepo.findByStatus(Status_REVIEW);
		return new ResponseEntity<Iterable<Request>>(requestInReview, HttpStatus.OK);
	}
	

	@GetMapping
	public ResponseEntity<Iterable<Request>> getRequests(){
		Iterable<Request> requests = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	
	@GetMapping ("{id}")
	public ResponseEntity<Request> getRequest(@PathVariable int id){
		Optional<Request> request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request){
		Request newRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewRequest(@PathVariable int id, @RequestBody Request request) {
		String newStatus = request.getTotal() <= 50 ? Status_APPROVED : Status_REVIEW;
		request.setStatus(newStatus);
		return putRequest(id, request);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus(Status_APPROVED);
		return putRequest(id, request);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus(Status_REJECTED);
		return putRequest(id, request);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Request> putRequest(@PathVariable int id, @RequestBody Request request){
		if(request.getId() != id) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	reqRepo.save(request);
	return new ResponseEntity<Request>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("{id}")
	public ResponseEntity<Request> deleteProduct(@PathVariable int id){
		Optional<Request> request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqRepo.delete(request.get());
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
