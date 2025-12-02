package br.com.student.portal.controller;

import br.com.student.portal.dto.question.QuestionRequest;
import br.com.student.portal.dto.question.QuestionResponse;
import br.com.student.portal.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
        log.info("GET /api/questions - Listar todas as perguntas");
        List<QuestionResponse> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable UUID id) {  // ✅ MUDADO: UUID
        log.info("GET /api/questions/{} - Obter pergunta específica", id);
        QuestionResponse question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/search")
    public ResponseEntity<List<QuestionResponse>> searchQuestions(@RequestParam String term) {
        log.info("GET /api/questions/search?term={} - Buscar perguntas", term);
        List<QuestionResponse> questions = questionService.searchQuestions(term);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequest request) {
        log.info("POST /api/questions - Criar nova pergunta");

        QuestionResponse response = questionService.createQuestion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable UUID id,  // ✅ MUDADO: UUID
            @RequestBody QuestionRequest request) {
        log.info("PUT /api/questions/{} - Atualizar pergunta", id);
        QuestionResponse response = questionService.updateQuestion(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID id) {  // ✅ MUDADO: UUID
        log.info("DELETE /api/questions/{} - Deletar pergunta", id);
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}