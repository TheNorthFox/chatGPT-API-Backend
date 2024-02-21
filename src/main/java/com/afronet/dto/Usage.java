package com.afronet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usage {
    private String prompt_tokens;
    private String completion_tokens;
    private String total_tokens;
}
