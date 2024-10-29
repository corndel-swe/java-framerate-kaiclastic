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
  }}

//  public static Review createReview(int id, int movieId, int rating, Date createdAt) throws SQLException {
//    var query = "INSERT INTO reviews (id, movieId, rating, createdAt) VALUES (?, ?, ?, ?) RETURNING id, reviewDate;";
//
//    int lastId;
//    String reviewDate;
//
//    try (var conn = DB.getConnection();
//         var stmt = conn.prepareStatement(query)) {
//
//      stmt.setInt(1, id);
//      stmt.setInt(2, movieId);
//      stmt.setInt(3, rating);
//      stmt.setDate(4, (java.sql.Date) createdAt);
//
//
//      var rs = stmt.executeQuery();
//      if (rs.next()) {
//        lastId = rs.getInt("id");
//        reviewDate = rs.getString("reviewDate");
//      } else {
//        throw new SQLException("Failed to insert review");
//      }
//    }
//
//    return new Review(id, movieId, rating, createdAt);
//  }
//}
