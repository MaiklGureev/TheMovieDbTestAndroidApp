package ru.gureev.MovieDbTestAndroidApp.tools;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Genre;
import ru.gureev.MovieDbTestAndroidApp.POJOs.enities.Movie;
import ru.gureev.MovieDbTestAndroidApp.R;

public class Utils {


    //загрузка фото
    public static void loadPicture(ShapeableImageView imageView, String url) {
        if (url.equals(AppConfig.API_IMAGE_URL + "null")) {
            Picasso.get().load(R.drawable.empty_movie_image).into(imageView);
        } else {
            Picasso.get().load(url).into(imageView);
        }
    }

    //перевод жанров из фильма в строку через запятую
    public static String genresToString(Movie movie) {
        StringBuilder result = new StringBuilder();
        List<Genre> genres = movie.getGenres();
        if (genres != null) {
            for (Genre g : genres) {
                result.append(g.getName());
                if (g.getId() != genres.get(genres.size() - 1).getId()) {
                    result.append(", ");
                }
            }
            return result.toString();
        } else
            return "";
    }

    //возвращает год выпуска фильма
    public static String transformDate(Movie movie) {
        if (movie.getRelease_date() != null && movie.getRelease_date().length() > 3) {
            return String.format(" (%s)", movie.getRelease_date().substring(0, 4));
        }
        return "";
    }
}
