package biz.cosee.mockitoexamples.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
public class Comment {
    private Long id;
    private Long postId;
    private String body;
}
