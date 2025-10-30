package ninja.oxente.cara_suja.models;

import java.time.ZonedDateTime;
import ninja.oxente.cara_suja.enums.payment.FormEnum;
import ninja.oxente.cara_suja.enums.payment.FrequencyEnum;
import ninja.oxente.cara_suja.enums.payment.MethodEnum;

public record TemplateModel(int id, int totalInstallments, int currentInstallment, FrequencyEnum frequency,
                            int period, boolean isRecurrent, int payday, ZonedDateTime lastPaymentAt,
                            FormEnum paymentForm, MethodEnum paymentMethod, int precision, String currency) {

}
