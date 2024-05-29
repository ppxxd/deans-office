package ru.ppxxd.deansoffice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ppxxd.deansoffice.exception.ChairNotFoundException;
import ru.ppxxd.deansoffice.exception.PositionNotFoundException;
import ru.ppxxd.deansoffice.model.Group;
import ru.ppxxd.deansoffice.model.Position;
import ru.ppxxd.deansoffice.storage.GroupStorage;
import ru.ppxxd.deansoffice.storage.PositionStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionStorage positionStorage;

    public Position getPosition(Integer id) {
        if (!positionStorage.checkPositionExists(id)) {
            throw new PositionNotFoundException("Должность с таким id не существует.");
        }
        return positionStorage.getPositionByID(id);
    }

    public List<Position> getAll() {
        return positionStorage.getPositions();
    }
}
