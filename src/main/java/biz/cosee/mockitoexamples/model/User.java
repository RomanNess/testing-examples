package biz.cosee.mockitoexamples.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    private Long id;

    private String username;
}
