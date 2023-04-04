package com.maxtrain.prs.request;

//import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface RequestRepository extends CrudRepository <Request, Integer> {
	Iterable<Request> findByStatus(String status);
}
