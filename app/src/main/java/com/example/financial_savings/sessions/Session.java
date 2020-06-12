package com.example.financial_savings.sessions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences preferences;
    private static final String ID_CATE = "idCate";
    private static final String ID_WALLET = "idWallet";
    private static final String ID_WALLET_RECEIVE = "idWalletReceive";
    private static final String ID_EVENT = "idEvent";
    private static final String ID_SAVINGS = "idSavings";
    private static final String TIME_START = "timeStart";
    private static final String TIME_END = "timeEnd";
    private static final String NAME_TIME = "nameTime";

    public Session(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    //Clear

    public void clearCate() {
        preferences.edit().remove(ID_CATE).apply();
    }

    public void clearWallet() {
        preferences.edit().remove(ID_WALLET).apply();
    }

    public void clearWalletReceive() {
        preferences.edit().remove(ID_WALLET_RECEIVE).apply();
    }

    public void clearEvent() {
        preferences.edit().remove(ID_EVENT).apply();
    }

    public void clearSavings() {
        preferences.edit().remove(ID_SAVINGS).apply();
    }

    public void clearTimeStart() {
        preferences.edit().remove(TIME_START).apply();
    }

    public void clearTimeEnd() {
        preferences.edit().remove(TIME_END).apply();
    }

    public void clearNameTime() {
        preferences.edit().remove(NAME_TIME).apply();
    }

    //Set

    public void setIDCate(String id) {
        preferences.edit().putString(ID_CATE, id).apply();
    }

    public void setIDWallet(String id) {
        preferences.edit().putString(ID_WALLET, id).apply();
    }

    public void setIDWalletReceive(String id) {
        preferences.edit().putString(ID_WALLET_RECEIVE, id).apply();
    }

    public void setIDEvent(String id) {
        preferences.edit().putString(ID_EVENT, id).apply();
    }

    public void setIDSavings(String id) {
        preferences.edit().putString(ID_SAVINGS, id).apply();
    }

    public void setTimeStart(String timeStart) {
        preferences.edit().putString(TIME_START, timeStart).apply();
    }

    public void setTimeEnd(String timeEnd) {
        preferences.edit().putString(TIME_END, timeEnd).apply();
    }

    public void setNameTime(String nameTime) {
        preferences.edit().putString(NAME_TIME, nameTime).apply();
    }

    //Get

    public String getIDCate() {
        return preferences.getString(ID_CATE, "");
    }

    public String getIDWallet() {
        return preferences.getString(ID_WALLET, "");
    }

    public String getIDWalletReceive() {
        return preferences.getString(ID_WALLET_RECEIVE, "");
    }

    public String getIDEvent() {
        return preferences.getString(ID_EVENT, "");
    }

    public String getIDSavings() {
        return preferences.getString(ID_SAVINGS, "");
    }

    public String getTimeStart() {
        return preferences.getString(TIME_START, "");
    }

    public String getTimeEnd() {
        return preferences.getString(TIME_END, "");
    }

    public String getNameTime() {
        return preferences.getString(NAME_TIME, "");
    }
}
