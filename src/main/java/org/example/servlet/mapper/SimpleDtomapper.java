package org.example.servlet.mapper;

import org.example.model.SimpleEntity;
import org.example.servlet.dto.IncomingDto;
import org.example.servlet.dto.OutGoingDto;

public interface SimpleDtomapper {
    SimpleEntity map(IncomingDto incomingDto);

    OutGoingDto map(SimpleEntity simpleEntity);
}
