package kz.bitlab.middle02.keycloak.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

    @GetMapping
    public List<String> getItems() {
        return List.of("Item1", "Item2", "Item3");
    }
}
