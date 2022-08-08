package utcn.ps.assignment1demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import utcn.ps.assignment1demo.dto.VoteQuestionDto;
import utcn.ps.assignment1demo.event.BaseEvent;
import utcn.ps.assignment1demo.service.VoteQuestionService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/voteQuestions")
@RequiredArgsConstructor
public class VoteQuestionController {
    private final VoteQuestionService voteQuestionService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping()
    public List<VoteQuestionDto> findVoteQuestions() {
        return voteQuestionService.findAll();
    }

    @GetMapping(value = "/{id}")
    public VoteQuestionDto findVoteQuestionById(@PathVariable("id") Integer id){
        return voteQuestionService.findById(id);
    }

    @PostMapping()
    public VoteQuestionDto insert(@RequestBody VoteQuestionDto voteQuestionDto){
        return voteQuestionService.insert(voteQuestionDto);
    }

    @PutMapping()
    public VoteQuestionDto update(@RequestBody VoteQuestionDto voteQuestionDto) {
        return voteQuestionService.update(voteQuestionDto);
    }

    @DeleteMapping(value="/{id}")
    public void remove(@PathVariable("id") Integer id){
        voteQuestionService.remove(id);
    }

    @EventListener(BaseEvent.class)
    public void handleEvent(BaseEvent event) {
        log.info("Got an event: {}.", event);
        messagingTemplate.convertAndSend("/topic/events", event);
    }
}
