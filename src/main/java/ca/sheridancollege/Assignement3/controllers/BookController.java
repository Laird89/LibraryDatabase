package ca.sheridancollege.Assignement3.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ca.sheridancollege.Assignement3.beans.Book;
import ca.sheridancollege.Assignement3.beans.ErrorMessage;
import ca.sheridancollege.Assignement3.database.DatabaseAccess;


@RestController


@RequestMapping("/books")
public class BookController {
    
        private DatabaseAccess da;

    public BookController (DatabaseAccess da){
        this.da = da;
    }

    @GetMapping
    public List<Book> getBooks(){
        return da.getBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id){

        Book book = da.getBook(id);

        if(book != null){
            return ResponseEntity.ok(book);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("No Such Record"));
        }
    }

    @PostMapping (consumes="application/json")
    public ResponseEntity<?> postStudent(@RequestBody Book book){

        try{
            Long id = da.addBook(book);

            book.setId(id);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

            return ResponseEntity.created(location).body(book);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage("Name already exists"));
        }
    }
}
