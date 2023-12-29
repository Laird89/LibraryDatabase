package ca.sheridancollege.Assignement3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.Assignement3.beans.Book;
import ca.sheridancollege.Assignement3.beans.Review;
import ca.sheridancollege.Assignement3.beans.SiteUser;
import ca.sheridancollege.Assignement3.database.DatabaseAccess;

@Controller 
public class HomeController {

    @Autowired
    DatabaseAccess da;

    @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String goHome(Model model){

        List<Book> books = da.getBooks();

        model.addAttribute("bookList", books);
        model.addAttribute("book", new Book());
        return "index";
    }

    @GetMapping("/login")
    public String goToLogin(){
        return "login";
    }

    @GetMapping("/viewBook")
    public String goToReview(@RequestParam("id") Long bookId, @RequestParam("title") String bookTitle,Model model){

        List<Review> reviews = da.getReviewsForBook(bookId);
        model.addAttribute("reviews", reviews);
        model.addAttribute("bookTitle", bookTitle);
        model.addAttribute("bookId", bookId);

        System.out.println("Retrieved reviews: " + reviews);

        return "/view-book";
    }

    @GetMapping("/register")
    public String goToRegister(Model model){

        model.addAttribute("siteUser", new SiteUser());
        return "/register";
    }

    @GetMapping("/addBook")
    public String goToAdminSecured(){
        return "/admin/add-book";
    }

    @PostMapping("/submitBook")
    public String addBook(@ModelAttribute Book book) {
        try {
            Long addedBookId = da.addBook(book);

        } catch (Exception e) {

        }
        return "redirect:/";
    }



    @GetMapping("/addReview")
    public String goToAddReview(Model model, @RequestParam("bookId") Long bookId, @RequestParam("title") String bookTitle) {
        model.addAttribute("review", new Review());
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookTitle", bookTitle);
        return "/user/add-review";
    }
    

    @PostMapping("/submitReview")
    public String submitReview(@ModelAttribute Review review) {
        try {
            long addedReviewId = da.addReview(review);
            System.out.println("Added review with ID: " + addedReviewId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
    

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute SiteUser siteUser) {

        String encodedPassword = passwordEncoder.encode(siteUser.getUserPassword());
        UserDetails userDetails = User.builder()
                .username(siteUser.getUserName())
                .password(encodedPassword)
                .roles(siteUser.getUserRole()) 
                .build();

        userDetailsManager.createUser(userDetails);

        return "redirect:/login";
    }


    @GetMapping("/permission-denied")
    public String goToDenied(){
        return "/error/permission-denied";
    }
}
