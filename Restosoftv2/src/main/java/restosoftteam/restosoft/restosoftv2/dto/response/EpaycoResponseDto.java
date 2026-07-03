package restosoftteam.restosoft.restosoftv2.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EpaycoResponseDto {
    private Boolean success;
    private String title;
    private String message;
    private EpaycoDataDto data;

    @Data
    public static class EpaycoDataDto {
        @JsonProperty("x_ref_payco")
        private String xRefPayco;

        @JsonProperty("x_transaction_state")
        private String xTransactionState;

        @JsonProperty("x_amount")
        private Double xAmount;

        @JsonProperty("x_id_invoice")
        private Integer xIdInvoice;

        @JsonProperty("x_extra1")
        private Integer xExtra1;

        @JsonProperty("x_extra2")
        private Integer xExtra2;
    }
}
