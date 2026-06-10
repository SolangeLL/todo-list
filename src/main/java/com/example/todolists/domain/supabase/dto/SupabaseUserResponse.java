package com.example.todolists.domain.supabase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class SupabaseUserResponse {
    private String id;
    private String email;

    @JsonProperty("user_metadata")
    private SupabaseUserMetadata metadata;
}
