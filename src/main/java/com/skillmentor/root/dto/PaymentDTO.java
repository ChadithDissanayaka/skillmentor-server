package com.skillmentor.root.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {
    @JsonProperty("mentor_id")
    private Integer mentorId;

    @NotBlank(message = "Mentor name must not be blank")
    @JsonProperty("mentor_name")
    private String mentorName;

    @NotNull(message = "Total fee must not be null")
    @Min(value = 0, message = "Total fee must be zero or positive")
    @JsonProperty("total_fee")
    private Double totalFee;
}
