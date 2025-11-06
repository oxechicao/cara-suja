package ninja.oxente.cara_suja.domains.account;

import java.time.ZonedDateTime;
import ninja.oxente.cara_suja.domains.payment.FormEnum;
import ninja.oxente.cara_suja.domains.payment.FrequencyEnum;
import ninja.oxente.cara_suja.domains.payment.MethodEnum;

public record TemplateModel(int id, int totalInstallments, int currentInstallment,
                            FrequencyEnum frequency,
                            int period, boolean isRecurrent, int payday,
                            ZonedDateTime lastPaymentAt,
                            FormEnum paymentForm, MethodEnum paymentMethod, int precision,
                            String currency) {

}
