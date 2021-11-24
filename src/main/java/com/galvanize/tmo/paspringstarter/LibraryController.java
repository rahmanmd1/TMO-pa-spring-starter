package com.galvanize.tmo.paspringstarter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

	private static List<Book> books = new ArrayList<>();

	@GetMapping("/health")
	public void health() {

	}

	@PostMapping("/api/books")
	public ResponseEntity<Object> addBook(@RequestBody Book book) {

		int id = getLatestId()+1;
		book.setId(id);
		
		books.add(book);
		
		return new ResponseEntity<>(book,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/api/books")
	public ResponseEntity<Object> getBooks() {


		List<Book> sortedList = books.stream()
		.sorted(Comparator.comparing(Book::getTitle))
		.collect(Collectors.toList());
		
		Map<String,List<Book>> res = new HashMap<>();
				
		res.put("books", sortedList);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/api/books")
	public ResponseEntity<Object> delBooks() {


		books.removeAll(books);
		
		return new ResponseEntity<>("",HttpStatus.NO_CONTENT);
		
	}

	

	private int getLatestId() {

		return books.stream().mapToInt(bk -> bk.getId()).max().orElse(0);

	}
}
