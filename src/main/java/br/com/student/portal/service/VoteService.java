package br.com.student.portal.service;

import br.com.student.portal.entity.VoteEntity;
import br.com.student.portal.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class VoteService {
    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public VoteEntity createVote(VoteEntity voteEntity){
        return voteRepository.save(voteEntity);
    }
    public List<VoteEntity> getAllVotes(){
        return voteRepository.findAll();
    }
    public Optional<VoteEntity> getVoteById(UUID id){
        return voteRepository.findById(id);
    }
}
