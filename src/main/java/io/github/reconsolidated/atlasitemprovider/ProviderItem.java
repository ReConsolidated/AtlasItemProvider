package io.github.reconsolidated.atlasitemprovider;


import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("provideritem")
public class ProviderItem implements ConfigurationSerializable {
    public String category;
    public String name;

    public ProviderItem(String category, String name) {
        this.category = category;
        this.name = name;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("category", category);
        result.put("name", name);
        return result;
    }

    public static ProviderItem deserialize(Map<String, Object> map) {
        return new ProviderItem((String) map.get("category"), (String) map.get("name"));
    }
}
