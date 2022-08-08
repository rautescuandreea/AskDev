package utcn.ps.assignment1demo.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TagDto {
    private Integer tagId;
    private String tag;
    private List<QuestionDto> questions = new ArrayList<>();

    public static TagDto tagDtoFromTag(Tag tag) {
        TagDto tagDto = new TagDto();

        tagDto.setTagId(tag.getTagId());
        tagDto.setTag(tag.getTag());

        if (!CollectionUtils.isEmpty(tag.getQuestions())) {
            tagDto.setQuestions(tag.getQuestions().stream().map(QuestionDto::questionDtoFromQuestion).collect(Collectors.toList()));
        } else {
            tagDto.setQuestions(new ArrayList<>());
        }

        return tagDto;
    }
}
