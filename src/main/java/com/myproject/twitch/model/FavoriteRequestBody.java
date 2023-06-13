package com.myproject.twitch.model;

import com.myproject.twitch.db.entity.ItemEntity;

public record FavoriteRequestBody(
        ItemEntity favorite
) { }

