package com.exsun.commonlibrary.frame.network.exception;

import android.util.Log;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by MrKong on 2017/9/12.
 */

public class ExceptionFunc implements Func1<Throwable, Observable>
{
    @Override
    public Observable call(Throwable throwable) {
        Log.e("Tag","-------->"+throwable.getMessage());
        return Observable.error(FactoryException.analysisExcetpion(throwable));
    }
}
