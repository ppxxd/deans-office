package ru.ppxxd.deansoffice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ppxxd.deansoffice.model.Group;
import ru.ppxxd.deansoffice.storage.GroupStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupStorage groupStorage;
    public Group addGroup(Group group) {
        return groupStorage.addGroup(group);
    }

    public Group updateGroup(Group group) {
        groupStorage.checkGroupExist(group.getId());
        return groupStorage.updateGroup(group);
    }

    public Group deleteGroup(Group group) {
        groupStorage.checkGroupExist(group.getId());
        return groupStorage.deleteGroup(group);
    }

    public Group getGroupByID(Integer id) {
        groupStorage.checkGroupExist(id);
        return groupStorage.getGroupByID(id);
    }

    public List<Group> getAll() {
        return groupStorage.getGroups();
    }

    public Group getGroupByStudentID(int studentID) {
        return groupStorage.getGroupByStudentID(studentID);
    }

    public Group addStudentToGroup(int studentID, Group group) {
        return groupStorage.addStudentToGroup(studentID, group);
    }

    public Group updateStudentGroup(int studentID, Group group) {
        groupStorage.checkGroupExist(group.getId());
        return groupStorage.updateStudentGroup(studentID, group);
    }

    public Group deleteStudentFromGroup(int studentID, Group group) {
        groupStorage.checkGroupExist(group.getId());
        return groupStorage.deleteStudentFromGroup(studentID, group);
    }
}
