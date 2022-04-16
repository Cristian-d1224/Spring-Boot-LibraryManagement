package com.example.library.controller;


import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/getAll")
    List<Book> getAllBooks(){
        return this.bookRepository.findAll();
    }

    @GetMapping("/getById/{id}")
    Optional<Book> getBookById(@PathVariable String id){
        long idParameter = Long.parseLong(id);
        return this.bookRepository.findById(idParameter);
    }

    @GetMapping("/available/{name}")
    boolean isAvailable(@PathVariable String name){
        return this.bookRepository.findByTitle(name).get().isBorrowed();
    }

    @PutMapping("/borrow")
    String borrowed(@RequestBody Book book){
        if(!book.isBorrowed()){
            Optional<Book> b = this.bookRepository.findByTitle(book.getTitle());
            b.get().setBorrowed(true);
            return "Enjoy it";
        }else{
            return "The book is already borrowed";
        }
    }

    @PutMapping("/deliver")
    String deliver(@RequestBody Book book){
        Optional<Book> b = this.bookRepository.findByTitle(book.getTitle());
        b.get().setBorrowed(false);
        return "We hope you enjoy it.";
    }

    @PostMapping("/donate")
    String donate(@RequestBody Book book){
        this.bookRepository.save(book);
        return "Thank you for your donation";
    }

}