package com.myproject.twitch.favorite;

import com.myproject.twitch.db.FavoriteRecordRepository;
import com.myproject.twitch.db.ItemRepository;
import com.myproject.twitch.db.entity.FavoriteRecordEntity;
import com.myproject.twitch.db.entity.ItemEntity;
import com.myproject.twitch.db.entity.UserEntity;
import com.myproject.twitch.model.TypeGroupedItemList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FavoriteService {

    private final ItemRepository itemRepository;
    private final FavoriteRecordRepository favoriteRecordRepository;

    public FavoriteService(ItemRepository itemRepository,
                           FavoriteRecordRepository favoriteRecordRepository) {
        this.itemRepository = itemRepository;
        this.favoriteRecordRepository = favoriteRecordRepository;
    }

    @Transactional
    public void setFavoriteItem(UserEntity user, ItemEntity item) {
        ItemEntity persistedItem = itemRepository.findByTwitchId(item.twitchId());
        if (persistedItem == null) {
            persistedItem = itemRepository.save(item);
        }
        FavoriteRecordEntity favoriteRecord =
                new FavoriteRecordEntity(null, user.id(), persistedItem.id(), Instant.now());
        favoriteRecordRepository.save(favoriteRecord);
    }


    public void unsetFavoriteItem(UserEntity user, String twitchId) {
        ItemEntity item = itemRepository.findByTwitchId(twitchId);
        if (item != null) {
            favoriteRecordRepository.delete(user.id(), item.id());
        }
    }


    public List<ItemEntity> getFavoriteItems(UserEntity user) {
        List<Long> favoriteItemIds = favoriteRecordRepository.findFavoriteItemIdsByUserId(user.id());
        return itemRepository.findAllById(favoriteItemIds);
    }


    public TypeGroupedItemList getGroupedFavoriteItems(UserEntity user) {
        List<ItemEntity> items = getFavoriteItems(user);
        return new TypeGroupedItemList(items);
    }
}
