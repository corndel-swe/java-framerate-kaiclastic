package com.corndel.framerate;

import com.corndel.framerate.repositories.MovieRepository;
import io.javalin.http.HttpStatus;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.Map;

public class App {
  public static void main(String[] args) {
    var javalin = createApp();
    javalin.start(5050);
  }

  public static Javalin createApp() {
    var app = Javalin.create(
        config -> {
          config.staticFiles.add("/public", Location.CLASSPATH);

          var resolver = new ClassLoaderTemplateResolver();
          resolver.setPrefix("/templates/");
          resolver.setSuffix(".html");
          resolver.setTemplateMode("HTML");

          var engine = new TemplateEngine();
          engine.setTemplateResolver(resolver);

          config.fileRenderer(new JavalinThymeleaf(engine));
        });

    app.get("/", ctx -> {
        var movies = MovieRepository.findAll();
        ctx.render("/allMovies.html", Map.of("movies", movies));
//        ctx.json(movies);
    });
      app.get( "/movie/{id}",
               ctx -> {
          var id = Integer.parseInt(ctx.pathParam("id"));
          var movieById = MovieRepository.findById(id);
          ctx.status(200).json(movieById);
      });

      app.get( "/movies/{genre}",
              ctx -> {
                  var genre  = ctx.pathParam("genre").substring(0, 1).toUpperCase() + ctx.pathParam("genre").substring(1);
                  var movieByGenre = MovieRepository.findByGenre(genre);
                  ctx.status(200).json(movieByGenre);
              });

    return app;
  }
}
