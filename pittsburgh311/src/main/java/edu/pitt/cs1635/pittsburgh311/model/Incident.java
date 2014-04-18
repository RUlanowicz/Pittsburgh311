package edu.pitt.cs1635.pittsburgh311.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by ulanowicz on 4/18/14.
 */
public class Incident extends SugarRecord<Incident> {
    public Incident(Context context) {
        super(context);
    }
}
