package br.com.student.portal.service;

import br.com.student.portal.dto.question.QuestionRequest;
import br.com.student.portal.dto.question.QuestionResponse;
import br.com.student.portal.entity.QuestionEntity;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionResponse getQuestionById(UUID id) {
        log.info("Buscando pergunta por ID: {}", id);
        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pergunta não encontrada com ID: " + id));
        return mapToResponse(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getAllQuestions() {
        log.info("Buscando todas as perguntas");
        return questionRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponse> getAllQuestions(Pageable pageable) {
        log.info("Buscando perguntas paginadas: página {}, tamanho {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return questionRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public QuestionResponse createQuestion(QuestionRequest request) {
        log.info("Criando nova pergunta: {}", request.getTitle());

        QuestionEntity question = QuestionEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getUserEntity())
                .build();

        QuestionEntity savedQuestion = questionRepository.save(question);
        return mapToResponse(savedQuestion);
    }

    public QuestionResponse updateQuestion(UUID id, QuestionRequest questionRequest) {
        log.info("Atualizando pergunta ID: {}", id);

        QuestionEntity existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pergunta não encontrada"));

        if (!existingQuestion.getAuthor().getId().equals(questionRequest.getUserEntity().getId())) {
            throw new RuntimeException("Apenas o autor pode editar a pergunta");
        }

        existingQuestion.setTitle(questionRequest.getTitle());
        existingQuestion.setContent(questionRequest.getContent());
        existingQuestion.setAuthor(questionRequest.getUserEntity());

        QuestionEntity updatedQuestion = questionRepository.save(existingQuestion);
        return mapToResponse(updatedQuestion);
    }

    public void deleteQuestion(UUID id) {
        log.info("Deletando pergunta ID: {}", id);

        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pergunta não encontrada"));

        boolean isAuthor = question.getAuthor().getId().equals(question.getId().toString());
        boolean isAdmin = question.getAuthor().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAuthor && !isAdmin) {
            throw new RuntimeException("Não autorizado a deletar esta pergunta");
        }

        questionRepository.delete(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> searchQuestions(String term) {
        log.info("Buscando perguntas com termo: {}", term);

        return questionRepository.searchByTerm(term)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponse> searchQuestions(String term, Pageable pageable) {
        log.info("Buscando perguntas paginadas com termo: {}", term);

        List<QuestionResponse> results = searchQuestions(term);

        return new org.springframework.data.domain.PageImpl<>(
                results.subList(
                        (int) pageable.getOffset(),
                        Math.min((int) (pageable.getOffset() + pageable.getPageSize()), results.size())
                ),
                pageable,
                results.size()
        );
    }

    private QuestionResponse mapToResponse(QuestionEntity entity) {
        return QuestionResponse.builder()
                .id(entity.getId().toString())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .answerCount(entity.getAnswerCount() != null ? entity.getAnswerCount() : 0)
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : "")
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : "")
                .build();
    }
}