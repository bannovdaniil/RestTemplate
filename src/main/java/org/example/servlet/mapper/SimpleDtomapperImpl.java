package org.example.servlet.mapper;

import org.example.model.SimpleEntity;
import org.example.servlet.dto.IncomingDto;
import org.example.servlet.dto.OutGoingDto;

public class SimpleDtomapperImpl implements SimpleDtomapper {
    @Override
    public SimpleEntity map(IncomingDto incomingDto) {
        return null;
    }

    @Override
    public OutGoingDto map(SimpleEntity simpleEntity) {
        return null;
    }
}
