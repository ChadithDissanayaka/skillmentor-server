package com.skillmentor.root.service.impl;

import com.skillmentor.root.dto.SessionDTO;
import com.skillmentor.root.dto.SessionLiteDTO;
import com.skillmentor.root.entity.LiteSessionEntity;
import com.skillmentor.root.entity.SessionEntity;
import com.skillmentor.root.mapper.LiteSessionEntityDTOMapper;
import com.skillmentor.root.mapper.SessionDTOEntityMapper;
import com.skillmentor.root.repository.LiteSessionRepository;
import com.skillmentor.root.repository.SessionRepository;
import com.skillmentor.root.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SessionServiceImp implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private LiteSessionRepository liteSessionRepository;

    @Override
    public SessionLiteDTO createSession(SessionLiteDTO sessionDTO) {
        final LiteSessionEntity liteSessionEntity = LiteSessionEntityDTOMapper.map(sessionDTO);
        final LiteSessionEntity savedEntity =  liteSessionRepository.save(liteSessionEntity);
        return LiteSessionEntityDTOMapper.map(savedEntity);
    }

    @Override
    public List<SessionDTO> getAllSessions() {
        final List<SessionEntity> sessionEntityList = sessionRepository.findAll();
        return sessionEntityList.stream().map(SessionDTOEntityMapper::map).toList();
    }
}
