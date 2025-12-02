package br.com.student.portal.service;

import br.com.student.portal.dto.question.AnswerRequest;
import br.com.student.portal.dto.question.AnswerResponse;
import br.com.student.portal.entity.AnswerEntity;
import br.com.student.portal.entity.QuestionEntity;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.AnswerRepository;
import br.com.student.portal.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerResponse getAnswerById(UUID id) {
        AnswerEntity answer = answerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Resposta não encontrada com ID: " + id));
        return mapToResponse(answer);
    }

    @Transactional(readOnly = true)
    public List<AnswerResponse> getAnswersByQuestionId(UUID questionId) {
        return answerRepository.findByQuestionId(questionId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AnswerResponse createAnswer(AnswerRequest request, UUID questionId, UserEntity author) {
        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ObjectNotFoundException("Pergunta não encontrada com ID: " + questionId));

        AnswerEntity answer = AnswerEntity.builder()
                .content(request.getContent())
                .author(author)
                .question(question)
                .build();

        AnswerEntity savedAnswer = answerRepository.save(answer);

        question.setAnswerCount(question.getAnswerCount() + 1);
        questionRepository.save(question);

        log.info("Resposta criada com ID: {} para pergunta: {}", savedAnswer.getId(), questionId);
        return mapToResponse(savedAnswer);
    }

    public AnswerResponse updateAnswer(UUID id, AnswerRequest request, UserEntity author) {
        AnswerEntity existingAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Resposta não encontrada com ID: " + id));

        if (!existingAnswer.getAuthor().getId().equals(author.getId())) {
            throw new RuntimeException("Apenas o autor pode editar a resposta");
        }

        existingAnswer.setContent(request.getContent());
        AnswerEntity updatedAnswer = answerRepository.save(existingAnswer);

        return mapToResponse(updatedAnswer);
    }

    public void deleteAnswer(UUID id, UserEntity requester) {
        AnswerEntity answer = answerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Resposta não encontrada com ID: " + id));

        boolean isAuthor = answer.getAuthor().getId().equals(requester.getId());
        boolean isAdmin = requester.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAuthor && !isAdmin) {
            throw new RuntimeException("Não autorizado a deletar esta resposta");
        }

        QuestionEntity question = answer.getQuestion();
        question.setAnswerCount(Math.max(0, question.getAnswerCount() - 1));
        questionRepository.save(question);

        answerRepository.delete(answer);
    }

    @Transactional(readOnly = true)
    public long countAnswersByQuestionId(UUID questionId) {
        return answerRepository.countByQuestionId(questionId);
    }

    @Transactional(readOnly = true)
    public List<AnswerResponse> getAnswersByAuthor(UUID authorId) {
        return answerRepository.findByAuthorId(authorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AnswerResponse mapToResponse(AnswerEntity entity) {
        return AnswerResponse.builder()
                .id(entity.getId().toString())
                .questionId(entity.getQuestion().getId().toString())
                .authorId(entity.getAuthor().getId().toString())
                .authorName(entity.getAuthor().getName())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : "")
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : "")
                .build();
    }
}