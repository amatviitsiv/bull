package com.alex.ua.provider;

import com.alex.ua.client.collection.CollectionCollectDto;
import com.alex.ua.client.collection.CollectionModel;
import com.alex.ua.client.collection.CollectionRequestDto;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedList;

public class CollectionsProvider {
    @Getter
    private LinkedList<CollectionModel> collectionsModels = new LinkedList<>();

    public CollectionsProvider() {
        collectionsModels.add(new CollectionModel(new CollectionCollectDto("ba"),
                new CollectionRequestDto("ba", Arrays.asList(10), 0)));
        collectionsModels.add(new CollectionModel(new CollectionCollectDto("mn"),
                new CollectionRequestDto("mn", Arrays.asList(6,7,8), 0)));
        collectionsModels.add(new CollectionModel(new CollectionCollectDto("lk"),
                new CollectionRequestDto("lk", Arrays.asList(11,5,4), 0)));
        collectionsModels.add(new CollectionModel(new CollectionCollectDto("fr"),
                new CollectionRequestDto("fr", Arrays.asList(1,2,3), 0)));
    }
}
