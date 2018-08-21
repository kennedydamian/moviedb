package com.damian.moviedb.ui.movieList;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.damian.moviedb.data.db.model.Movie;
import com.damian.moviedb.data.repository.DataState;
import com.damian.moviedb.data.repository.MovieRepository;

import javax.inject.Inject;

public class MovieListViewModel extends ViewModel {

    @Inject
    MovieRepository moviesRepo;

    private LiveData<DataState> repoResult;
    private LiveData<PagedList<Movie>> movies;
    private MutableLiveData<Movie> selectedMovie;

    @Inject
    public MovieListViewModel(MovieRepository moviesRepo) {
        this.moviesRepo = moviesRepo;

        repoResult = moviesRepo.getDataState();
        movies = Transformations.switchMap(repoResult, result -> {
            return result.pagedList;
        });

        moviesRepo.initRepo();
    }

    public LiveData<PagedList<Movie>> getMovies() {
        return movies;
    }

    public MutableLiveData<Movie> getSelectedMovie() {
        if (selectedMovie == null) {
            selectedMovie = new MutableLiveData<Movie>();
        }
        return selectedMovie;
    }
}
