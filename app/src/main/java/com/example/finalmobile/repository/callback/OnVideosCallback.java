package com.example.finalmobile.repository.callback;

import com.example.finalmobile.models.Videos;

import java.util.List;

public interface OnVideosCallback {
    void onSuccess(List<Videos> videos);
    void onFailure(String message);
}
