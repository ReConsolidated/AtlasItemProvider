package io.github.reconsolidated.atlasitemprovider.rankedItems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RankedItem {
    private long id;
    private String name;
    private double score;


}
