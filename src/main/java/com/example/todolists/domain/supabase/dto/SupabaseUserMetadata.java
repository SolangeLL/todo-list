package com.example.todolists.domain.supabase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SupabaseUserMetadata {
    @JsonProperty("name")
    private String name;

    @JsonProperty("role")
    private String role;
}
