package ph.cdo.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.mapper.BaseEntityDTOMapper;
import ph.cdo.backend.dto.mapper.impl.TokenDTOMapper;
import ph.cdo.backend.dto.records.TokenDTO;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.repository.TokenRepository;
import ph.cdo.backend.repository.noBean.BaseEntityRepository;
import ph.cdo.backend.service.impl.base_entity.BaseEntityServiceImpl;

@Service
public class TokenServiceImpl extends BaseEntityServiceImpl<Token, TokenDTO, TokenDTOMapper> {

    @Autowired
    public TokenServiceImpl(
            @Qualifier("TokenRepository") TokenRepository repository,
            @Qualifier("TokenDTOMapper")TokenDTOMapper mapper) {
        super(repository, mapper);
    }
}
