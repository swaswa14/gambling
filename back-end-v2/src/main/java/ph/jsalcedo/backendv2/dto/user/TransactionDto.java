package ph.jsalcedo.backendv2.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ph.jsalcedo.backendv2.dto.BaseDto;
import ph.jsalcedo.backendv2.model.TransactionType;

import java.math.BigDecimal;
import java.util.Date;


public record  TransactionDto(
        Long id,
        Date createDate,
        TransactionType type,
        BigDecimal amount

) implements BaseDto {


    @Override
    public Long getId() {
        return id;
    }
}
