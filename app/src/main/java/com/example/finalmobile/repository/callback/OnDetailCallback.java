package com.example.finalmobile.repository.callback;

import com.example.finalmobile.models.DetailModel;

public interface OnDetailCallback {
    void onSuccess(DetailModel model, String message);
    void onFailure(String message);
}
