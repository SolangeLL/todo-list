package com.example.todolists.domain.user.dto;

import lombok.*;
import java.util.Optional;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    @Builder.Default
    private Optional<String> email = Optional.empty();

    @Builder.Default
    private Optional<String> name = Optional.empty();
}
