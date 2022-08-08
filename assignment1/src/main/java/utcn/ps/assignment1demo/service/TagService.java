package utcn.ps.assignment1demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utcn.ps.assignment1demo.dto.TagDto;
import utcn.ps.assignment1demo.entity.Tag;
import utcn.ps.assignment1demo.event.TagCreatedEvent;
import utcn.ps.assignment1demo.exception.TagNotFoundException;
import utcn.ps.assignment1demo.persistance.api.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// The @Service is a specialized @Component (https://www.baeldung.com/spring-component-repository-service)
@Service
@RequiredArgsConstructor
public class TagService {
    private final RepositoryFactory factory;
    private final ApplicationEventPublisher eventPublisher;

    /*
        Transactional methods ensure that the code contained inside is run in a transaction,
        which is committed when the methods returns and is rolled-back when an exception is thrown
        see http://www.codingpedia.org/jhadesdev/how-does-spring-transactional-really-work/
    */
    @Transactional
    public TagDto findById(Integer id) {
        return TagDto.tagDtoFromTag(factory.createTagRepository().findById(id).orElseThrow(TagNotFoundException::new));
    }

    @Transactional
    public List<TagDto> findByQuestionId(Integer questionId) {
        return factory.createTagRepository().findByQuestionId(questionId).stream().map(TagDto::tagDtoFromTag).collect(Collectors.toList());
    }

    @Transactional
    public List<TagDto> findAll() {
        return factory.createTagRepository().findAll().stream().map(TagDto::tagDtoFromTag).collect(Collectors.toList());
    }

    @Transactional
    public TagDto insert(TagDto tagDto) {
        Tag tag = new Tag(tagDto.getTagId(), tagDto.getTag(), new ArrayList<>());
        TagDto output = TagDto.tagDtoFromTag(factory.createTagRepository().save(tag));
        eventPublisher.publishEvent(new TagCreatedEvent(output));
        return output;
    }

    @Transactional
    public TagDto update(TagDto tagDto) {
        Tag tag = new Tag(tagDto.getTagId(), tagDto.getTag(), new ArrayList<>());
        TagDto output = TagDto.tagDtoFromTag(factory.createTagRepository().save(tag));
        eventPublisher.publishEvent(new TagCreatedEvent(output));
        return output;
    }

    @Transactional
    public void remove(int id) {
        Tag Tag = factory.createTagRepository().findById(id).orElseThrow(TagNotFoundException::new);
        factory.createTagRepository().remove(Tag);
    }

    public void setQuestionTag(Integer questionId, Integer tagId) {
        factory.createTagRepository().setQuestionTag(questionId, tagId);
    }
}