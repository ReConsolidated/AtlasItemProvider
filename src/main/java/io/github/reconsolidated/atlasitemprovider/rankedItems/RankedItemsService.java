package io.github.reconsolidated.atlasitemprovider.rankedItems;

import java.util.List;

public class RankedItemsService {
    private final RankedItemsRepository rankedItemsRepository = new RankedItemsRepository();

    public List<RankedItem> getAllByItemName(String itemName) {
        return rankedItemsRepository.getAllByItemName(itemName);
    }
}
