package com.corndel.framerate.repositories;

import com.corndel.framerate.DB;
import com.corndel.framerate.models.Review;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewRepository {
  public static List<Review> findByMovie(int movieId) throws SQLException {
    var query = "SELECT * FROM REVIEWS WHERE movieId = ?";

    try (var con = DB.getConnection();
         var stmt = con.prepareStatement(query)) {
      stmt.setInt(1, movieId);
      try (var rs = stmt.executeQuery()) {
        var reviews = new ArrayList<Review>();
        while (rs.next()) {
          reviews.add(new Review());
        }
        return reviews;
      }
    }
  }

  public static Review createReview(int movieID,String reviewText , int rating) throws SQLException {
    var query = "INSERT INTO reviews (movieId, content, rating) VALUES (?,?,?) RETURNING *";

    try (var conn = DB.getConnection();
         var stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, movieID);
      stmt.setString(2, reviewText);
      stmt.setInt(3, rating);

      try (var rs = stmt.executeQuery()) {
        if (rs.next()) {
          int movieId = rs.getInt("id");
          int returnedMovieId = rs.getInt("movieId");
          Date returnedDate = rs.getDate("createdAt");
          int returnedRating = rs.getInt("rating");

          return new Review(movieId, returnedMovieId, returnedRating, returnedDate);
        } else {
          throw new SQLException("Failed to insert review");
        }
      }
    }
  }


  public static void main(String[] args) throws SQLException {
    var reviewInserted = ReviewRepository.createReview(2,"Amazing movie",10);
    System.out.println(reviewInserted + "Review Inserted");
  }
}
