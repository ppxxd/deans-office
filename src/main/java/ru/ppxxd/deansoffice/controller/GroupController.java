package ru.ppxxd.deansoffice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ppxxd.deansoffice.model.Group;
import ru.ppxxd.deansoffice.service.GroupService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public Group add(@Valid @RequestBody Group group) {
        log.info("Получен запрос POST /groups. Группа добавлена.");
        return groupService.addGroup(group);
    }

    @PutMapping
    public Group update(@Valid @RequestBody Group group) {
        log.info("Получен запрос PUT /groups. Группа c id {} обновлена.", group.getId());
        return groupService.updateGroup(group);
    }

    @DeleteMapping
    public Group delete(@Valid @RequestBody Group group) {
        log.info("Получен запрос DELETE /groups. Группа c id {} удален.", group.getId());
        return groupService.deleteGroup(group);
    }

    @GetMapping
    public List<Group> findAll() {
        log.info("Получен запрос GET /groups.");
        return groupService.getAll();
    }

    @GetMapping("/find/{id}")
    public Group getGroup(@PathVariable int id) {
        log.info("Получен запрос GET /groups/{}.", id);
        return groupService.getGroupByID(id);
    }
}
