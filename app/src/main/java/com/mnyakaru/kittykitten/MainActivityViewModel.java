package com.mnyakaru.kittykitten;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = "MainActivityViewModel";

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<CatImage> catImage = new MutableLiveData<>();

    private final MutableLiveData<CatFact> catFact = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoadingCatImage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingCatFact = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoadingCatFact() {
        return isLoadingCatFact;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    private final MutableLiveData<Boolean> isError = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoadingCatImage() {
        return isLoadingCatImage;
    }

    public LiveData<CatFact> getCatFact() {
        return catFact;
    }

    public LiveData<CatImage> getCatImage() {
        return catImage;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

    }

    public void loadCatImage() {
        Disposable disposable =
                loadCatImageRx()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> {
                            isError.setValue(false);
                            isLoadingCatImage.setValue(true);
                        })
                        .doOnTerminate(() -> isLoadingCatImage.setValue(false))
                        .subscribe(image -> catImage.setValue(image.get(0)),
                                throwable -> isError.setValue(true));
        compositeDisposable.add(disposable);
    }

    public void loadCatFact() {
        Disposable disposable = loadCatFactRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> isLoadingCatFact.setValue(true))
                .doOnTerminate(() -> isLoadingCatFact.setValue(false))
                .subscribe(catFact::setValue, throwable -> Log.d(TAG, "Error: " + throwable));
        compositeDisposable.add(disposable);
    }

    private Single<List<CatImage>> loadCatImageRx() {
        return ApiFactory.getApiServiceCat().loadCatImage();
    }

    private Single<CatFact> loadCatFactRx() {
        return ApiFactory.getApiServiceFact().loadCatFact();
    }
}
