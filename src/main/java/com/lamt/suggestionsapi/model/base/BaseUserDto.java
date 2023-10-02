package com.lamt.suggestionsapi.model.base;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserDto {
    private UUID id;
    private String username;
    private String email;
}
