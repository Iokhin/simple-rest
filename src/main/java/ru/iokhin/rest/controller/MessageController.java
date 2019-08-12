package ru.iokhin.rest.controller;

import org.springframework.web.bind.annotation.*;
import ru.iokhin.rest.exception.NotFoundException;

import java.util.*;

@RestController
@RequestMapping("message")
public class MessageController {

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", UUID.randomUUID().toString()); put("text", "First message"); }});
        add(new HashMap<String, String>() {{ put("id", UUID.randomUUID().toString()); put("text", "Second message"); }});
        add(new HashMap<String, String>() {{ put("id", UUID.randomUUID().toString()); put("text", "Third message"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream().filter(messages -> messages.get("id").equals(id))
                .findFirst().orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", UUID.randomUUID().toString());
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromMap = getMessage(id);
        messageFromMap.putAll(message);
        messageFromMap.put("id", id);
        return messageFromMap;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }
}
