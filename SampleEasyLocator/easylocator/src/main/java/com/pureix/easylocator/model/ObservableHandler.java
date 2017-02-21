package com.pureix.easylocator.model;

import java.util.Observable;

/**
 * Created by M.Hayle on 6/28/2016.
 */
public class ObservableHandler extends Observable
{
    public ObservableHandler()
    {}

    public void setChange(Object object)
    {
        setChanged();
        notifyObservers(object);
    }

}
