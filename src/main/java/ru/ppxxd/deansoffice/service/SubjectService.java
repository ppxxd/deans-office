package ru.ppxxd.deansoffice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ppxxd.deansoffice.exception.SubjectNotFoundException;
import ru.ppxxd.deansoffice.model.Subject;
import ru.ppxxd.deansoffice.storage.SubjectStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectStorage subjectStorage;

    public Subject addSubject(Subject subject) {
        subject = subjectStorage.addSubject(subject);
        return subject;
    }

    public Subject updateSubject(Subject subject) {
        subjectStorage.checkSubjectExist(subject.getSubjectID());
        return subjectStorage.updateSubject(subject);
    }

    public Subject deleteSubject(Subject subject) {
        subjectStorage.checkSubjectExist(subject.getSubjectID());
        return subjectStorage.deleteSubject(subject);
    }

    public Subject getSubjectByID(Integer id) {
        subjectStorage.checkSubjectExist(id);
        return subjectStorage.getSubjectByID(id);
    }

    public List<Subject> getAll() {
        return subjectStorage.getSubjects();
    }
}
