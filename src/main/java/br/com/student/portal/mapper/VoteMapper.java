package br.com.student.portal.mapper;

import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.VoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class VoteMapper {

    public static final VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    public abstract VoteEntity voteRequestIntoVoteEntity(VoteRequest voteRequest);
    public abstract VoteResponse voteEntityIntoVoteResponse(VoteEntity voteEntity);
}
