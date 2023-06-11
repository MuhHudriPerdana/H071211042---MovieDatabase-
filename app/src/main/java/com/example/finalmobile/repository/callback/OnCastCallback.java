package com.example.finalmobile.repository.callback;

import com.example.finalmobile.models.CreditModel;

public interface OnCastCallback {
    void onSuccess(CreditModel creditModel, String message);
    void onFailure(String message);
}
