package com.afronet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTResponse {
private String id;
private String object;
private String created;
    private List<Choice> choices;
    private Usage usage;



}