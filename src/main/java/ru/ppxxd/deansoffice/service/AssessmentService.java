package ru.ppxxd.deansoffice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ppxxd.deansoffice.model.Assessment;
import ru.ppxxd.deansoffice.storage.AssessmentStorage;
import ru.ppxxd.deansoffice.storage.StudentStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {
    private final AssessmentStorage assessmentStorage;

    private final StudentStorage studentStorage;

    public List<Assessment> getAll() {
        return assessmentStorage.getAssessments();
    }

    public List<Assessment> getAssessmentsByStudentID(Integer id) {
        studentStorage.checkStudentExists(id);
        return assessmentStorage.getAssessmentsByStudentID(id);
    }

    public Assessment addAssessment(Integer id, Assessment assessment) {
        studentStorage.checkStudentExists(id);
        return assessmentStorage.addAssessment(id, assessment);
    }

    public Assessment updateAssessment(Integer id, Assessment assessment) {
        assessmentStorage.checkAssessmentExists(assessment.getAssessmentID());
        studentStorage.getStudentByID(id);
        return assessmentStorage.updateAssessment(assessment);
    }

    public Assessment deleteAssessment(Integer studentID, Assessment assessment) {
        assessmentStorage.checkAssessmentExists(assessment.getAssessmentID());
        studentStorage.getStudentByID(studentID);
        return assessmentStorage.deleteAssessment(studentID, assessment);
    }

}