package ru.ppxxd.deansoffice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ppxxd.deansoffice.exception.ChairNotFoundException;
import ru.ppxxd.deansoffice.model.Chair;
import ru.ppxxd.deansoffice.storage.ChairStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChairService {
    private final ChairStorage chairStorage;

    public Chair addChair(Chair chair) {
        chair = chairStorage.addChair(chair);
        return chair;
    }

    public Chair updateChair(Chair chair) {
        chairStorage.checkChairExist(chair.getChairID());
        return chairStorage.updateChair(chair);
    }

    public Chair deleteChair(Chair chair) {
        chairStorage.checkChairExist(chair.getChairID());
        return chairStorage.deleteChair(chair);
    }

    public Chair getChairByID(Integer id) {
        chairStorage.checkChairExist(id);
        return chairStorage.getChairByID(id);
    }

    public List<Chair> getAll() {
        return chairStorage.getChairs();
    }
}
