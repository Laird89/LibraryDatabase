package ca.sheridancollege.Assignement3.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.Assignement3.beans.Book;
import ca.sheridancollege.Assignement3.beans.Review;

import java.util.List;

import javax.sql.DataSource;

@Repository
public class DatabaseAccess {

    
    private NamedParameterJdbcTemplate jdbc;

    public DatabaseAccess(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<String> getAuthorities() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "SELECT DISTINCT authority FROM authorities";

        return jdbc.queryForList(query, namedParameters,
                String.class);
    }

    
    public List<Book> getBooks(){
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM books";

        BeanPropertyRowMapper<Book> bookMapper = new BeanPropertyRowMapper<>(Book.class);

        List<Book> books = jdbc.query(query, namedParameters, bookMapper);

        return books;
    }

    
    public Book getBook(Long id){
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM books WHERE id = :id";

        namedParameters.addValue("id", id);

        BeanPropertyRowMapper<Book> studentMapper = new BeanPropertyRowMapper<>(Book.class);

        List<Book> books = jdbc.query(query, namedParameters, studentMapper);

        if(books.isEmpty()){
            return null;
        }else {
            return books.get(0);
        }
    }

    public Long addBook (Book book) throws Exception{

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO books (title, author) VALUES (:title, :author)";

        namedParameters.addValue("title", book.getTitle());
        namedParameters.addValue("author", book.getAuthor());

        KeyHolder generatedKey = new GeneratedKeyHolder();

        int returnValue = jdbc.update(query, namedParameters, generatedKey);

        Long bookId = (Long) generatedKey.getKey();

        return (returnValue > 0) ? bookId: 0;
    }

    public List<Review> getReviewsForBook(Long bookId) {
        String query = "SELECT r.id, r.bookId, r.text FROM reviews r " +
                       "JOIN books b ON r.bookId = b.id " +
                       "WHERE b.id = :bookId";
    
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("bookId", bookId);
    
        BeanPropertyRowMapper<Review> reviewMapper = new BeanPropertyRowMapper<>(Review.class);
    
        return jdbc.query(query, namedParameters, reviewMapper);
    }


    public Long addReview (Review review) throws Exception{

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO reviews (bookId, text) VALUES (:bookId, :text)";

        namedParameters.addValue("bookId", review.getBookId());
        namedParameters.addValue("text", review.getText());

        KeyHolder generatedKey = new GeneratedKeyHolder();

        int returnValue = jdbc.update(query, namedParameters, generatedKey);

        Long id = (Long) generatedKey.getKey();

        return (returnValue > 0) ? id: 0;
    }
    
}
