package com.corndel.framerate.models;

import java.util.Date;

public class Review {
  private int id;
  public int movieId;
  public Date createdAt;
  public int rating;

  public Review(int userId, int movieIds, int ratings, Date date) {
    this.id = userId;
    this.movieId = movieIds;
    this.createdAt = date;
    this.rating = ratings;
  }

  public Review() {

  }
}
