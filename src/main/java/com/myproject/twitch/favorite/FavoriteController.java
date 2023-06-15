package com.myproject.twitch.favorite;

import com.myproject.twitch.db.entity.UserEntity;
import com.myproject.twitch.model.FavoriteRequestBody;
import com.myproject.twitch.model.TypeGroupedItemList;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    // Hard-coded user for temporary use, will be replaced in future
    private final UserEntity userEntity = new UserEntity(1L, "user0", "Foo", "Bar", "password");

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public TypeGroupedItemList getFavoriteItems() {
        return favoriteService.getGroupedFavoriteItems(userEntity);
    }

    @PostMapping
    public void setFavoriteItem(@RequestBody FavoriteRequestBody body) {
        try {
            favoriteService.setFavoriteItem(userEntity, body.favorite());
        } catch (DbActionExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DuplicateKeyException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate entry for favorite record", e);
            } else {
                throw e;
            }
        }
    }

    @DeleteMapping
    public void unsetFavoriteItem(@RequestBody FavoriteRequestBody body) {
        favoriteService.unsetFavoriteItem(userEntity, body.favorite().twitchId());
    }
}

