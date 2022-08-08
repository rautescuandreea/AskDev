package utcn.ps.assignment1demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import utcn.ps.assignment1demo.dto.TagDto;
import utcn.ps.assignment1demo.entity.Tag;
import utcn.ps.assignment1demo.event.BaseEvent;
import utcn.ps.assignment1demo.service.TagService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping()
    public List<TagDto> findTags() {
        return tagService.findAll();
    }

    @GetMapping(value = "/{id}")
    public TagDto findTagById(@PathVariable("id") Integer id){
        return tagService.findById(id);
    }

    @GetMapping(value = "byQuestionId/{id}")
    public List<TagDto> findTagByQuestionId(@PathVariable("id") Integer id){
        return tagService.findByQuestionId(id);
    }

    @PostMapping(value="/{questionId}/{tagId}")
    public void setTagQuestion(@PathVariable("questionId") Integer questionId, @PathVariable("tagId") Integer tagId) {
        tagService.setQuestionTag(questionId, tagId);
    }

    @PostMapping()
    public TagDto insert(@RequestBody TagDto tagDto){
        return tagService.insert(tagDto);
    }

    @PutMapping()
    public TagDto update(@RequestBody TagDto tagDto) {
        return tagService.update(tagDto);
    }

    @DeleteMapping(value="/{id}")
    public void remove(@PathVariable("id") Integer id){
        tagService.remove(id);
    }

    @EventListener(BaseEvent.class)
    public void handleEvent(BaseEvent event) {
        log.info("Got an event: {}.", event);
        messagingTemplate.convertAndSend("/topic/events", event);
    }
}
